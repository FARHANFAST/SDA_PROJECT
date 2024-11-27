package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminDashboardController {

    @FXML
    private Button AdminDashboardbackbtn;
    @FXML
    private Button Admin_AddHotelBranchbtn;
    @FXML
    private Button Admin_RemoveHotelBranchbtn;
    @FXML
    private Button Admin_HandelRequestbtn;

    @FXML
    private Button Admin_GenerateReportbtn;
    
    @FXML
    void AdminDashboardbackbtn_Clicked(ActionEvent event) {
        try {
            Main.setRoot("AdminLogin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Admin_AddHotelBranchbtn_Clicked(ActionEvent event) {
        try {
            Main.setRoot("AdminAddHotels");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Admin_RemoveHotelBranchbtn_Clicked(ActionEvent event) {
        try {
            Main.setRoot("AdminRemoveHotels");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Admin_HandelRequestbtn_Clicked(ActionEvent event) {
        try {
            Main.setRoot("AdminHandelRequest");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void Admin_GenerateReportbtn_Clicked(ActionEvent event) {

    	try {
			Main.setRoot("AdminReports");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void Admin_ViewPendingBookings_Clicked(ActionEvent event) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String fetchPendingQuery = "SELECT * FROM RoomBookings WHERE BookingStatus = 'Pending'";
            PreparedStatement fetchPendingStmt = connection.prepareStatement(fetchPendingQuery);
            ResultSet resultSet = fetchPendingStmt.executeQuery();

            while (resultSet.next()) {
                int bookingId = resultSet.getInt("BookingID");
                String roomType = resultSet.getString("RoomType");
                int quantity = resultSet.getInt("Quantity");
                String location = resultSet.getString("Location");

                System.out.println("Booking ID: " + bookingId);
                System.out.println("Room Type: " + roomType);
                System.out.println("Quantity: " + quantity);
                System.out.println("Location: " + location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
