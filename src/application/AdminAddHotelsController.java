package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class AdminAddHotelsController {

    @FXML
    private Button AdminAddHotelRegisterbtn;

    @FXML
    private Button AdminAddHotel_backbtn;

    @FXML
    private CheckBox AdminOperationalselectfield;

    @FXML
    private TextField Admin_HotelContacttxtfield;

    @FXML
    private TextField Admin_HotelManagertxtfield;

    @FXML
    private TextField Admin_HotelNametxtfield;

    @FXML
    private TextField Admin_Hotellocationtxtfield;

    @FXML
    private CheckBox Adminoutorderselectfield;

    @FXML
    void initialize() {
        String email = SharedSession.getUserEmail();
        String password = SharedSession.getUserPassword();
        System.out.println("Logged-in Admin Email: " + email);
        System.out.println("Logged-in Admin Password: " + password);
    }

    @FXML
    void AdminAddHotelRegisterbtn_Clicked(ActionEvent event) {
        // Validate input fields
        String hotelName = Admin_HotelNametxtfield.getText();
        String location = Admin_Hotellocationtxtfield.getText();
        String contactNumber = Admin_HotelContacttxtfield.getText();
        String managerName = Admin_HotelManagertxtfield.getText();
        LocalDate currentDate = LocalDate.now(); // Get current date

        if (hotelName.isEmpty() || location.isEmpty() || contactNumber.isEmpty() || managerName.isEmpty()) {
        	DialogBox.show("Notification","Error !! All Fields are required!");
            return;
        }

        if (contactNumber.length() < 11) {
        	DialogBox.show("Notification","Contact Number must be at least 11 digits.");
            return;
        }

        try (Connection connection = DatabaseManager.getConnection()) {
            // Get Admin ID from session email
            String email = SharedSession.getUserEmail();
            if (email == null) {
                System.out.println("Error: Admin email not found in session.");
                return;
            }

            String getAdminQuery = "SELECT id FROM Admin WHERE email = ?";
            try (PreparedStatement getAdminStmt = connection.prepareStatement(getAdminQuery)) {
                getAdminStmt.setString(1, email);
                ResultSet resultSet = getAdminStmt.executeQuery();

                if (!resultSet.next()) {
                	DialogBox.show("Notification","Error: Admin does not exist.");
                    return;
                }

                int adminId = resultSet.getInt("id");

                // Insert hotel into database
                String insertHotelQuery = "INSERT INTO Hotels (AdminID, HotelName, Location, ContactNumber, ManagerName, EstablishedDate) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertHotelQuery)) {
                    insertStmt.setInt(1, adminId);
                    insertStmt.setString(2, hotelName);
                    insertStmt.setString(3, location);
                    insertStmt.setString(4, contactNumber);
                    insertStmt.setString(5, managerName);
                    insertStmt.setDate(6, java.sql.Date.valueOf(currentDate));

                    int rowsInserted = insertStmt.executeUpdate();
                    if (rowsInserted > 0) {
                    	DialogBox.show("Notification","Success !!! Hotel added successfully!");
                    } else {
                    	DialogBox.show("Notification","Error !! Failed to add the hotel.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database Error !! An error occurred while connecting to the database.");
        }
    }

    @FXML
    void AdminAddHotel_backbtn_Clicked(ActionEvent event) {
        try {
            Main.setRoot("AdminDashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
