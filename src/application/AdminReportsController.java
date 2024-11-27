package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminReportsController {

    @FXML
    private Button AdminReports_Backbtn;

    @FXML
    private Button GuestFeedbackReportGenbtn;

    @FXML
    private Button OccupancyReportGenbtn;

    @FXML
    private Button RevenueReportGenbtn;

    @FXML
    void AdminReports_Backbtn_Clicked(ActionEvent event) {

    	try {
			Main.setRoot("AdminDashboard");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void GuestFeedbackReportGenbtn_Clicked(ActionEvent event) {

    }

    @FXML
    void OccupancyReportGenbtn_Clicked(ActionEvent event) {

    }

    @FXML
    void RevenueReportGenbtn_Clicked(ActionEvent event) {

    }

}
