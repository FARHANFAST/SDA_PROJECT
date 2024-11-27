package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CustomerDashboardController {

    @FXML
    private Button CustomerDashboard_CancelBookingbtn;
    @FXML
    private Button CustomerRoomBookingBackbtn;
    @FXML
    private Button Customer_RoomBookbtn;
    @FXML
    private CheckBox DeluxRoomCheckbx;
    @FXML
    private TextField DeluxRoom_Quantity;
    @FXML
    private CheckBox DoubleRoomCheckbx;
    @FXML
    private TextField DoubleRoom_Quantity;
    @FXML
    private CheckBox SingleRoomCheckbx;
    @FXML
    private TextField SingleRoom_Quantity;
    @FXML
    private CheckBox SuitRoomCheckbx;
    @FXML
    private TextField SuitRoom_Quantity;
    @FXML
    private TextField DeluxRoom_Days;
    @FXML
    private TextField DoubleRoom_Days;
    @FXML
    private TextField SingleRoom_Days;
    @FXML
    private TextField SuitRoom_Days;
    @FXML
    private ComboBox<String> DeluxRoom_LocationCombo;
    @FXML
    private ComboBox<String> DoubleRoom_LocationCombo;
    @FXML
    private ComboBox<String> SingleRoom_LocationCombo;
    @FXML
    private ComboBox<String> SuitRoom_LocationCombo;

    double Sum=0,sum1=0,sum2=0,sum3=0,sum4=0.0;
    
    @FXML
    void initialize() {
        String email = SharedSession.getUserEmail();
        System.out.println("Logged-in User Email: " + email);

        DeluxRoom_LocationCombo.getItems().addAll("Islamabad", "Lahore", "Karachi");
        DoubleRoom_LocationCombo.getItems().addAll("Islamabad", "Lahore", "Karachi");
        SingleRoom_LocationCombo.getItems().addAll("Islamabad", "Lahore", "Karachi");
        SuitRoom_LocationCombo.getItems().addAll("Islamabad", "Lahore", "Karachi");

        DeluxRoom_LocationCombo.setValue("Islamabad");
        DoubleRoom_LocationCombo.setValue("Islamabad");
        SingleRoom_LocationCombo.setValue("Islamabad");
        SuitRoom_LocationCombo.setValue("Islamabad");
    }

    @FXML
    void CustomerRoomBookingBackbtn_Clicked(ActionEvent event) {
        try {
            Main.setRoot("UserLogin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Customer_RoomBookbtn_Clicked(ActionEvent event) {
    	
    	
        try (Connection connection = DatabaseManager.getConnection()) {
            String email = SharedSession.getUserEmail();
            if (email == null) {
                System.out.println("Error: User email not found in session.");
                return;
            }

            String getCustomerQuery = "SELECT id FROM Customer WHERE email = ?";
            PreparedStatement getCustomerStmt = connection.prepareStatement(getCustomerQuery);
            getCustomerStmt.setString(1, email);
            ResultSet resultSet = getCustomerStmt.executeQuery();

            if (!resultSet.next()) {
            	DialogBox.show("Notification","Error: Customer does not exist.");
                return;
            }

            int customerId = resultSet.getInt("id");
            String checkAvailabilityQuery = "SELECT AvailableRooms FROM RoomAvailability WHERE Location = ? AND RoomType = ?";
            String insertBookingQuery = "INSERT INTO RoomBookings (CustomerID, RoomType, Quantity, BookingDays, Location) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement checkAvailabilityStmt = connection.prepareStatement(checkAvailabilityQuery);
            PreparedStatement insertBookingStmt = connection.prepareStatement(insertBookingQuery);

            boolean bookingRequested = false;

            if (SingleRoomCheckbx.isSelected()) {
                processRoomBooking("Single Room", SingleRoom_LocationCombo.getValue(), SingleRoom_Quantity.getText(), SingleRoom_Days.getText(),
                                   checkAvailabilityStmt, insertBookingStmt, customerId);
                bookingRequested = true;
                
                sum1=BookedRoomPriceCalculation.calculatePaymentAmount("Single Room", SingleRoom_Quantity.getText(), SingleRoom_Days.getText());
            }
            if (DoubleRoomCheckbx.isSelected()) {
                processRoomBooking("Double Room", DoubleRoom_LocationCombo.getValue(), DoubleRoom_Quantity.getText(), DoubleRoom_Days.getText(),
                                   checkAvailabilityStmt, insertBookingStmt, customerId);
                bookingRequested = true;
                sum2=BookedRoomPriceCalculation.calculatePaymentAmount("Double Room", DoubleRoom_Quantity.getText(), DoubleRoom_Days.getText());
            }
            if (DeluxRoomCheckbx.isSelected()) {
                processRoomBooking("Delux Room", DeluxRoom_LocationCombo.getValue(), DeluxRoom_Quantity.getText(), DeluxRoom_Days.getText(),
                                   checkAvailabilityStmt, insertBookingStmt, customerId);
                bookingRequested = true;
                sum3=BookedRoomPriceCalculation.calculatePaymentAmount("Delux Room", DeluxRoom_Quantity.getText(), DeluxRoom_Days.getText());
            }
            if (SuitRoomCheckbx.isSelected()) {
                processRoomBooking("Suit Room", SuitRoom_LocationCombo.getValue(), SuitRoom_Quantity.getText(), SuitRoom_Days.getText(),
                                   checkAvailabilityStmt, insertBookingStmt, customerId);
                bookingRequested = true;
                sum4=BookedRoomPriceCalculation.calculatePaymentAmount("Suit Room", SuitRoom_Quantity.getText(), SuitRoom_Days.getText());
            }

            if (!bookingRequested) {
            	DialogBox.show("Notification","Error: No rooms selected for booking.");
                return;
            }
 
            Sum=sum1+sum2+sum3+sum4;
           
            insertBookingStmt.executeBatch();
           
            SharedSession.setSum(Sum);
            Main.setRoot("CustomerPayment");  
            
            System.out.println("Price Calculated : "+Sum);
            DialogBox.show("Notification","Booking request sent. Awaiting admin approval.");
        } catch (SQLException e) {
            e.printStackTrace();
            DialogBox.show("Notification","Error: Unable to process room booking request.");
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void processRoomBooking(String roomType, String location, String quantityText, String daysText,
                                    PreparedStatement checkAvailabilityStmt, PreparedStatement insertBookingStmt, int customerId) throws SQLException {
        if (quantityText.isEmpty() || daysText.isEmpty() || location == null) {
        	DialogBox.show("Notification","Error: All fields are required for " + roomType + ".");
            return;
        }

        int quantity = Integer.parseInt(quantityText);
        int days = Integer.parseInt(daysText);

        checkAvailabilityStmt.setString(1, location);
        checkAvailabilityStmt.setString(2, roomType);
        ResultSet availabilityResult = checkAvailabilityStmt.executeQuery();

        if (availabilityResult.next() && availabilityResult.getInt("AvailableRooms") >= quantity) {
            insertBookingStmt.setInt(1, customerId);
            insertBookingStmt.setString(2, roomType);
            insertBookingStmt.setInt(3, quantity);
            insertBookingStmt.setInt(4, days);
            insertBookingStmt.setString(5, location);
            insertBookingStmt.addBatch();
        } else {
        	DialogBox.show("Notification","Error: Insufficient rooms available for " + roomType + " in " + location + ".");
        }
    }
    
    
    @FXML
    void CustomerDashboard_CancelBookingbtn_Clicked(ActionEvent event) {

    }
}
