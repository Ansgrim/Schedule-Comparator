import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class UI2 {

	@FXML
    private VBox display;
	
    @FXML
    private MenuItem newComparison;

    @FXML
    private MenuItem exportReport;

    @FXML
    private MenuItem info;

    @FXML
    private ScrollPane graphPane;

    @FXML
    private TitledPane issuesTextBox;

    @FXML
    void displayInfo(ActionEvent event) {
    	System.out.println("Display Information");
    }

    @FXML
    void exportReport(ActionEvent event) {
    	System.out.println("Export this report to a PDF");
    }

    @FXML
    void newComparison(ActionEvent event) {
    	System.out.println("Go back to previous screen to change schedules.");
    }

}