package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AdminSignUpController {

    @FXML
    private PasswordField AdminConfPassTExtField;

    @FXML
    private TextField AdminContactTExtField;

    @FXML
    private PasswordField AdminSetPassTExtField;

    @FXML
    private Button AdminSignUpbackbtn;

    @FXML
    private Button AdminSignUpbtn;

    @FXML
    private TextField AdminaddressTExtField;

    @FXML
    private TextField AdminemailTExtField;

    @FXML
    private TextField AdminnameTExtField;

    @FXML
    void AdminSignUpbackbtn_Clicked(ActionEvent event) {

    	try {
			Main.setRoot("AdminLogin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void AdminSignUpbtn_Clicked(ActionEvent event) {

    	String name = AdminnameTExtField.getText();
        String email = AdminemailTExtField.getText();
        String contact = AdminContactTExtField.getText();
        String address = AdminaddressTExtField.getText();
        String password = AdminSetPassTExtField.getText();
        String confirmPassword = AdminConfPassTExtField.getText();

        // Validate inputs
        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
        {
        	DialogBox.show("Notification","All fields are required.");
            return;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) 
        {
        	DialogBox.show("Notification","Invalid email format.");
            return;
        }

        if (!contact.matches("\\d{10}"))
        {
        	DialogBox.show("Notification","Contact number must be 10 digits.");
            return;
        }

        if (!password.equals(confirmPassword)) 
        {
        	DialogBox.show("Notification","Passwords do not match.");
            return;
        }
        if (!password.matches("\\d{4}"))
        {
        	DialogBox.show("Notification","Password must be atleast 4 digits.");
            return;
        }

        // If all validations pass, save user data to the database
        try (Connection connection = DatabaseManager.getConnection()) 
        {
            if (connection == null) 
            {
                System.out.println("Failed to connect to the database.");
                return;
            }

            String insertQuery = "INSERT INTO Admin (name, email, contact, address, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, contact);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) 
            {
            	DialogBox.show("Notification","Admin registered successfully and data saved to the database.");
            } 
            else
            {
                System.out.println("Failed to insert data into the database.");
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("Error inserting user data: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
