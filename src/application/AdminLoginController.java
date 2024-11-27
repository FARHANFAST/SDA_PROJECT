
package application;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminLoginController {

    @FXML
    private Button AdminBackbtn;

    @FXML
    private Button AdminSignUpbtn;

    @FXML
    private TextField Adminlogin_emailTextField;

    @FXML
    private TextField Adminlogin_passTextField;

    @FXML
    private Button Adminloginbtn;

    @FXML
    void AdminBackbtn_Clicked(ActionEvent event) {
      
    	try {
			Main.setRoot("Welcome");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void AdminSignUpbtn_Clicked(ActionEvent event) {

    	try {
			Main.setRoot("AdminSignUp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }

    @FXML
    void Adminloginbtn_Clicked(ActionEvent event) {

    	String email = Adminlogin_emailTextField.getText();
        String password = Adminlogin_passTextField.getText();

        
        if (email.isEmpty() || password.isEmpty()) {
            DialogBox.show("Notification","Empty Fields: Please enter both email and password.");
            return;
        }

        try (Connection connection = DatabaseManager.getConnection()) 
        {
            String query = "SELECT * FROM Admin WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // User exists, log them in
                System.out.println("Login Successful: Welcome, " + email + "!");
                
                SharedSession.setUserEmail(email);
                SharedSession.setUserPassword(password);
                
                Main.setRoot("AdminDashboard");
            } 
            else 
            {
                // User not found
            	DialogBox.show("Notification","Login Failed: Invalid email or password. Please sign up if you are new.");
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error: An error occurred while connecting to the database.");
        }
    }

}
