package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserLoginController {

    @FXML
    private Button gotosignup;

    @FXML
    private TextField login_emailTextField;

    @FXML
    private TextField login_passTextField;

    @FXML
    private Button loginbtn;

    @FXML
    private Label myscene2;
    

    @FXML
    private Button Backbtn;

    @FXML
    void gotosignup_Clicked(ActionEvent event)
    {
    	try 
    	{
			Main.setRoot("UserSignup");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("From Login Page Moved to Sign Up Page!");
    }

    @FXML
    void loginbtn_Clicked(ActionEvent event) {

    	String email = login_emailTextField.getText();
        String password = login_passTextField.getText();

        if (email.isEmpty() || password.isEmpty()) {
        	DialogBox.show("Notification","Empty Fields: Please enter both email and password.");
            return;
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) 
        {
        	DialogBox.show("Notification","Invalid email format.");
            return;
        }

        try (Connection connection = DatabaseManager.getConnection()) 
        {
            String query = "SELECT * FROM Customer WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // User exists, log them in
            	DialogBox.show("Notification","Login Successful: Welcome, " + email + "!");
                
                // SharedSession Injection
                SharedSession.setUserEmail(email);
                SharedSession.setUserPassword(password);
                
                Main.setRoot("RoomRateList");
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
    
    
    @FXML
    void Backbtn_Clicked(ActionEvent event) {

    	try 
    	{
			Main.setRoot("Welcome");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
