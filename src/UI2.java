import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
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
    private LineChart<String, Integer> graph;

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
    
    void updateGraph(Control c) {
    	ObservableList<Series<String, Double>> value = null;
    	Series<String, Integer> data = new Series<String, Integer>();
    	TimeSlot[] dataList = c.shifts;
    	
    	ObservableList<Data<String, Integer>> dList = new FilteredList<Data<String, Integer>>(FXCollections.observableArrayList());
    	for(int i=0; i<dataList.length; i++) {
    		TimeSlot t = dataList[i];
    		Data<String, Integer> data2 = new Data<String, Integer>();
    		data2.setXValue(t.getTime(i));
    		data2.setYValue(t.getDifference());
    		dList.add(data2);
    	}
    	data.setData(dList);
    	ObservableList<Series<String, Integer>> dX = new FilteredList<Series<String, Integer>>(FXCollections.observableArrayList());
    	dX.add(data);
    	graph.setData(dX);
    	
    	
    }


}