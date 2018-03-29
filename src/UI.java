import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@SuppressWarnings("serial")
public class UI{
	
	private Stage mStage;
	
	public void setStage(Stage mStage) {
		this.mStage = mStage;
	}
	
	@FXML
    private VBox importPane;
	
	@FXML
    private Button sc1Button;

    @FXML
    private Button sc2Button;

    @FXML
    private Button compareButton;

    @FXML
    private Label s1Label;

    @FXML
    private Label s2Label;

    @FXML
    void compareSchedules(ActionEvent event) {
    	System.out.println("Compare Button Pressed!");
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		Parent root = loader.load(getClass().getResource("Display.fxml").openStream());
    		UI2 ui2 = loader.getController();
    		ui2.setStage(mStage);
    		
    		mStage.setTitle("Comparing" + s1Label.getText() + " and " + s2Label.getText());
    		mStage.setScene(new Scene(root));
    		mStage.show();
		} catch (IOException e) {
			System.out.println("Other Panel could not be loaded!");
			e.printStackTrace();
		}
    }

    @FXML
    void importSchedule1(ActionEvent event) {
    	System.out.println("Schedule 1 Button Pressed!");
    }

    @FXML
    void importSchedule2(ActionEvent event) {
    	System.out.println("Schedule 2 Button Pressed!");
    }
}
