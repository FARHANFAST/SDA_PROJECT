package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RoomRateListController {

    @FXML
    private Button RoomPricingListBackbtn;

    @FXML
    private Button RoomPricingListNextbtn;

    @FXML
    void RoomPricingListBackbtn_Clicked(ActionEvent event) {

    	try {
			Main.setRoot("UserLogin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void RoomPricingListNextbtn_Clicked(ActionEvent event) {

    	try {
			Main.setRoot("CustomerDashboard");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
