import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI2 {

	private Stage mStage;
	
	public void setStage(Stage mStage) {
		this.mStage = mStage;
	}
	
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
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		Parent root = loader.load(getClass().getResource("ImportPanel.fxml").openStream());
    		UI ui = loader.getController();
    		ui.setStage(mStage);
    		
    		mStage.setTitle("Schedule Comparator");
    		mStage.setScene(new Scene(root));
    		mStage.show();
		} catch (IOException e) {
			System.out.println("Other Panel could not be loaded!");
			e.printStackTrace();
		}
    }


}