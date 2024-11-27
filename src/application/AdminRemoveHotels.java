package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminRemoveHotels {

    @FXML
    private TextField AdminRemoveHotels_Adminconfirmpassword_txtfield;

    @FXML
    private Button AdminRemoveHotels_Backbtn;

    @FXML
    private TextField AdminRemoveHotels_HotelNametxtfield;

    @FXML
    private Button AdminRemoveHotels_Removebtn;
    
    @FXML
    void initialize() {
        String email = SharedSession.getUserEmail();
        String password = SharedSession.getUserPassword();
        System.out.println("Logged-in Admin Email: " + email);
        System.out.println("Logged-in Admin Password: " + password);
    }

    @FXML
    void AdminRemoveHotels_Backbtn_Clicked(ActionEvent event) {

    	try {
			Main.setRoot("AdminDashboard");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

    
    @FXML
    void AdminRemoveHotels_Removebtn_Clicked(ActionEvent event) {
        String hotelName = AdminRemoveHotels_HotelNametxtfield.getText().trim();
        String adminPassword = AdminRemoveHotels_Adminconfirmpassword_txtfield.getText().trim();

        // Check if the entered admin password matches the session password
        if (!adminPassword.equals(SharedSession.getUserPassword())) {
        	DialogBox.show("Notification","Admin Entered Password is incorrect!!!");
            return;
        }

        // Query to retrieve HotelID using HotelName
        String getHotelIdQuery = "SELECT HotelID FROM Hotels WHERE HotelName = ?";
        
        // Query to delete the hotel using the extracted HotelID
        String deleteHotelQuery = "DELETE FROM Hotels WHERE HotelID = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement getIdStmt = conn.prepareStatement(getHotelIdQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteHotelQuery)) {

            // Step 1: Get HotelID based on HotelName
            getIdStmt.setString(1, hotelName);
            ResultSet rs = getIdStmt.executeQuery();

            if (rs.next()) {
                int hotelId = rs.getInt("HotelID");

                // Step 2: Delete the hotel using the extracted HotelID
                deleteStmt.setInt(1, hotelId);
                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected > 0) {
                	DialogBox.show("Notification","Success!! Hotel removed successfully.");
                } else {
                	DialogBox.show("Notification","Failed!!! Hotel could not be deleted.");
                }
            } else {
            	DialogBox.show("Notification","No hotel found with the provided name.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while connecting to the database.");
        }
    }

   }
 
  
