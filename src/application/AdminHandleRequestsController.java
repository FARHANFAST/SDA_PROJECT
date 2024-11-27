package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.Button;  // Correct import for JavaFX Button
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminHandleRequestsController {

    @FXML
    private TableView<BookingRequest> pendingRequestsTable;

    @FXML
    private TableColumn<BookingRequest, Integer> bookingIDCol;

    @FXML
    private TableColumn<BookingRequest, String> roomTypeCol;

    @FXML
    private TableColumn<BookingRequest, Integer> quantityCol;

    @FXML
    private TableColumn<BookingRequest, String> locationCol;

    private ObservableList<BookingRequest> pendingRequests = FXCollections.observableArrayList();

    @FXML
    private Button AdminHandelBackbtn;  // Correct Button type

    @FXML
    private Button approveButton;  // Correct Button type
    @FXML
    private Button rejectButton;  // Correct Button type

    @FXML
    public void initialize() {
        bookingIDCol.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        fetchPendingRequests();
    }

    private void fetchPendingRequests() {
        try (Connection connection = DatabaseManager.getConnection()) {
            String fetchPendingQuery = "SELECT BookingID, RoomType, Quantity, Location FROM RoomBookings WHERE BookingStatus = 'Pending'";
            PreparedStatement statement = connection.prepareStatement(fetchPendingQuery);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int bookingID = resultSet.getInt("BookingID");
                String roomType = resultSet.getString("RoomType");
                int quantity = resultSet.getInt("Quantity");
                String location = resultSet.getString("Location");

                pendingRequests.add(new BookingRequest(bookingID, roomType, quantity, location));
            }

            pendingRequestsTable.setItems(pendingRequests);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void approveRequest(ActionEvent event) {
        BookingRequest selectedRequest = pendingRequestsTable.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            try (Connection connection = DatabaseManager.getConnection()) {
                String updateStatusQuery = "UPDATE RoomBookings SET BookingStatus = 'Approved' WHERE BookingID = ?";
                PreparedStatement statement = connection.prepareStatement(updateStatusQuery);
                statement.setInt(1, selectedRequest.getBookingID());
                statement.executeUpdate();

                pendingRequests.remove(selectedRequest);
                pendingRequestsTable.refresh();
                
                System.out.println("Booking approved!");
               
               
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void rejectRequest(ActionEvent event) {
        BookingRequest selectedRequest = pendingRequestsTable.getSelectionModel().getSelectedItem();

        if (selectedRequest != null) {
            try (Connection connection = DatabaseManager.getConnection()) {
                String updateStatusQuery = "UPDATE RoomBookings SET BookingStatus = 'Rejected' WHERE BookingID = ?";
                PreparedStatement statement = connection.prepareStatement(updateStatusQuery);
                statement.setInt(1, selectedRequest.getBookingID());
                statement.executeUpdate();

                pendingRequests.remove(selectedRequest);
                pendingRequestsTable.refresh();
                System.out.println("Booking rejected!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void AdminHandelBackbtn_Clicked(ActionEvent event) {
        try {
            Main.setRoot("AdminDashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
