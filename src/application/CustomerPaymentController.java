package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CustomerPaymentController {

	  @FXML
	    private Button CustomerPayment_backbtn;
	  
    @FXML
    private TextField CustomerPaymentAccNumbertxtfield;

    @FXML
    private PasswordField CustomerPaymentAccPasstxtfield;

    @FXML
    private Button CustomerPayment_Paybtn;

    private double totalBill = SharedSession.getSum(); // Assuming you get the total bill from a shared session or another source

    @FXML
    void CustomerPayment_Paybtn_Clciked(ActionEvent event) throws SQLException {
        String accountNumber = CustomerPaymentAccNumbertxtfield.getText();
        String accountPassword = CustomerPaymentAccPasstxtfield.getText();

        // Validate account number and password
        if (validatePayment(accountNumber, accountPassword)) {
            // If valid, process the payment
            if (processPayment(accountNumber, totalBill)) {
                DialogBox.show("Success", "Payment processed successfully!");
                
            } else {
                DialogBox.show("Error", "Payment failed.");
            }
        } else {
            DialogBox.show("Error", "Invalid account details.");
        }
    }

    // Method to validate the account number and password from PaymentAccounts table
    private boolean validatePayment(String accountNumber, String accountPassword) {
        String query = "SELECT * FROM PaymentAccounts WHERE AccountNumber = ? AND Password = ?";

        try (Connection connection = DatabaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, accountNumber);
            statement.setString(2, accountPassword);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If a matching row is found, return true
        } catch (SQLException e) {
            e.printStackTrace();
            DialogBox.show("Error", "Error validating payment credentials.");
            return false;
        }
    }

    // Method to process payment by deducting from the account balance
    private boolean processPayment(String accountNumber, double amount) {
        try (Connection connection = DatabaseManager.getConnection()) {
            // Check the balance of the account
            String checkBalanceQuery = "SELECT Balance FROM PaymentAccounts WHERE AccountNumber = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkBalanceQuery);
            checkStmt.setString(1, accountNumber);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                double balance = resultSet.getDouble("Balance");

                // Check if the balance is sufficient for the payment
                if (balance >= amount) {
                    // Deduct the payment amount
                    String deductBalanceQuery = "UPDATE PaymentAccounts SET Balance = Balance - ? WHERE AccountNumber = ?";
                    PreparedStatement deductStmt = connection.prepareStatement(deductBalanceQuery);
                    deductStmt.setDouble(1, amount);
                    deductStmt.setString(2, accountNumber);
                    deductStmt.executeUpdate();

                    // Optionally, you could update the payment history here if needed
                    return true;  // Payment processed successfully
                } else {
                    DialogBox.show("Error", "Insufficient balance.");
                    return false;  // Not enough balance for payment
                }
            } else {
                DialogBox.show("Error", "Account not found.");
                return false;  // Account not found in the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DialogBox.show("Error", "Payment processing error.");
            return false;
        }
    }
    
    @FXML
    void CustomerPayment_backbtn_Clicked(ActionEvent event) {

    	try {
			Main.setRoot("CustomerDashboard");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
