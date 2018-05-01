import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class UI2 {

	private Stage mStage;
	
	public void setStage(Stage mStage) {
		this.mStage = mStage;
	}
	
	public ArrayList<String> getProblemSlots(TimeSlot[] slots, int differenceThreshold)
	{
		ArrayList<TimeSlot> problems = new ArrayList<TimeSlot>();
		ArrayList<Integer> problemIndices = new ArrayList<Integer>();
		for(int i = 0; i < slots.length; i++)
		{
			if(Math.abs(slots[i].getDifference()) >= differenceThreshold)
			{
				problems.add(slots[i]);
				problemIndices.add(new Integer(i));
			}
		}
		ArrayList<String> problemStrings = new ArrayList<String>();
		for(int j = 0; j < problems.size(); j++)
		{
			String message = TimeSlot.getTime(problemIndices.get(j));
			problemStrings.add(message);
		}
		return problemStrings;
		//Test
		
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
    private TableView<String> table;
    
    @FXML
    private TableColumn<String, String> column;
    
    @FXML
    private LineChart<String, Number> graph;

    @FXML
    void displayInfo(ActionEvent event) {
    	//System.out.println("Display Information");
    }

    @FXML
    void exportReport(ActionEvent event) {
    	//System.out.println("Export this report to a PDF");
    }

    @FXML
    void newComparison(ActionEvent event) {
    	//System.out.println("Go back to previous screen to change schedules.");
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		Parent root = loader.load(getClass().getResource("ImportPanel.fxml").openStream());
    		UI ui = loader.getController();
    		ui.setStage(mStage);
    		
    		mStage.setTitle("Schedule Comparator");
    		mStage.setResizable(false);
    		mStage.setScene(new Scene(root));
    		mStage.show();
		} catch (IOException e) {
			//System.out.println("Other Panel could not be loaded!");
			e.printStackTrace();
		}
    }
    
    void updateGraph(TimeSlot[] dataList) {
    	graph.getXAxis().setLabel("Day of the Week");
    	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
    	for(int i=0; i<dataList.length; i++) {
    		TimeSlot t = dataList[i];
    		series.getData().add(new XYChart.Data<String, Number>(TimeSlot.getTime(i), t.getDifference()));
    	}
    	graph.getData().add(series);
    	updateTable(getProblemSlots(dataList, 3));
    }
    
    void updateTable(ArrayList<String> problems){
    	table = new TableView<String>();
    	table.setEditable(true);
    	final ObservableList<String> data = FXCollections.observableArrayList(problems);
    	column = new TableColumn<String, String>("Problems");
    	table.setItems(data);
    	table.getColumns().add(column);
    }


}