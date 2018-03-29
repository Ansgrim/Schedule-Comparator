import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application{
	private Control c = new Control();
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("ImportPanel.fxml").openStream());
		UI ui = loader.getController();
		ui.setStage(stage);
		
		Scene scene = new Scene(root, 380, 130);
		
		stage.setTitle("Schedule Comparator");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main (String[] args) {
			launch(args);
	}
}
