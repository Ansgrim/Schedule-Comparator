import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI{
	
	private Stage mStage;
	
	public void setStage(Stage mStage) {
		this.mStage = mStage;
	}
	
	private String scheduleOne = "";
	
	private String scheduleTwo = "";
	
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
    	if(!scheduleOne.equals("") & !scheduleTwo.equals("")) {
    		try {
    			FXMLLoader loader = new FXMLLoader();
    			Parent root = loader.load(getClass().getResource("Display.fxml").openStream());
    			UI2 ui2 = loader.getController();
    			ui2.setStage(mStage);
    			TimeSlot[] shifts = Control.parse(scheduleOne, scheduleTwo);
    			ui2.updateGraph(shifts);

    			mStage.setTitle("Comparing" + s1Label.getText() + " and " + s2Label.getText());
    			mStage.setScene(new Scene(root));
    			mStage.show();
    		} catch (IOException e) {
    			System.out.println("Other Panel could not be loaded!");
    			e.printStackTrace();
    		}
    	}
    }

    @FXML
    void importSchedule1(ActionEvent event) {
    	System.out.println("Import Schedule 1 button pressed!");
    	JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            scheduleOne = file.getPath();
            s1Label.setText(file.getName());
        }
    }

    @FXML
    void importSchedule2(ActionEvent event) {
    	System.out.println("Import Schedule 2 button pressed!");
    	JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            scheduleTwo = file.getPath();
            s2Label.setText(file.getName());
        }
    }
}
