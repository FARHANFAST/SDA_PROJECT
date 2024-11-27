package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WelcomeController {

    @FXML
    private Button welcomAdminbtn;

    @FXML
    private Button welcomCustomerbtn;

    @FXML
    void welcomAdminbtn_Clicked(ActionEvent event) {

    	try {
			Main.setRoot("AdminLogin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void welcomCustomerbtn_Clicked(ActionEvent event) {
    
    	try {
			Main.setRoot("UserLogin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
