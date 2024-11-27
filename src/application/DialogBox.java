package application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DialogBox {
    // method to display the dialog
    public static void show(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title); 
        alert.setHeaderText(null); 
        alert.setContentText(message); 
        alert.showAndWait(); 
    }
}
