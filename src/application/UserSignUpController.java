package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserSignUpController {

    @FXML
    private PasswordField ConfPassTExtField;

    @FXML
    private TextField ContactTExtField;

    @FXML
    private PasswordField SetPassTExtField;

    @FXML
    private Button SignUpbtn;

    @FXML
    private TextField addressTExtField;

    @FXML
    private Button backbtn;

    @FXML
    private TextField emailTExtField;

    @FXML
    private Label myscene1;

    @FXML
    private TextField nameTExtField;
    
    @FXML
    private TextField AccTextField;
    
    @FXML
    private PasswordField AccountPasswordtxtfield;
    
    @FXML
    void Signupbtn_clicked(ActionEvent event) {
        String name = nameTExtField.getText();
        String email = emailTExtField.getText();
        String contact = ContactTExtField.getText();
        String address = addressTExtField.getText();
        String password = SetPassTExtField.getText();
        String confirmPassword = ConfPassTExtField.getText();
        String accNo = AccTextField.getText();
        String accpass = AccountPasswordtxtfield.getText();

        // Validate inputs
        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || accNo.isEmpty() || accpass.isEmpty()) {
            DialogBox.show("Notification", "All fields are required.");
            return;
        }

        if (accNo.length() < 10 || accNo.length() > 16) {
            DialogBox.show("Notification", "Incorrect Account Number format");
            return;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            DialogBox.show("Notification", "Invalid email format.");
            return;
        }

        if (!contact.matches("\\d{10}")) {
            DialogBox.show("Notification", "Contact number must be 10 digits.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            DialogBox.show("Notification", "Passwords do not match.");
            return;
        }

        if (!password.matches("\\d{3}")) {
            DialogBox.show("Notification", "Password must be at least 3 characters.");
            return;
        }

        try (Connection connection = DatabaseManager.getConnection()) {
            if (connection == null) {
                DialogBox.show("Notification", "Failed to connect to the database.");
                return;
            }

            // Insert customer data and get the generated ID
            String insertCustomerQuery = "INSERT INTO Customer (name, email, contact, address, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement customerStatement = connection.prepareStatement(insertCustomerQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            customerStatement.setString(1, name);
            customerStatement.setString(2, email);
            customerStatement.setString(3, contact);
            customerStatement.setString(4, address);
            customerStatement.setString(5, password);

            int rowsInserted = customerStatement.executeUpdate();
            if (rowsInserted == 0) {
                System.out.println("Failed to insert customer data.");
                return;
            }

            // Insert Payment Account data (AccountNumber and Password)
            String insertPaymentAccountQuery = "INSERT INTO PaymentAccounts (AccountNumber, Password, Balance,CustomerEmail) VALUES (?, ?, ?,?)";
            PreparedStatement paymentStatement = connection.prepareStatement(insertPaymentAccountQuery);
            paymentStatement.setString(1, accNo);
            paymentStatement.setString(2, accpass);
            paymentStatement.setDouble(3, 100000);  // Assuming initial balance 
            paymentStatement.setString(4, email);

            int paymentRowsInserted = paymentStatement.executeUpdate();
            if (paymentRowsInserted == 0) {
                System.out.println("Failed to insert payment account data.");
                return;
            }

            System.out.println("Success !! Customer and Payment Account Added Successfully");

        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    @FXML
    void backbtn_Clicked(ActionEvent event)
    {
       try {
		Main.setRoot("UserLogin");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
    }

}
