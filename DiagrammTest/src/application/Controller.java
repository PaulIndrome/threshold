package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;

public class Controller {

	ObservableList<Double> Ydata = FXCollections.observableArrayList();
	ObservableList<String> Xdata = FXCollections.observableArrayList();

	private NumberAxis yAxis = new NumberAxis();
	private CategoryAxis xAxis = new CategoryAxis();

	@FXML
	Button RandomDataButton;
	@FXML
	Button actualizeButton;
	@FXML
	public LineChart<String, Number> LineChart = new LineChart<String, Number>(xAxis, yAxis);

	Series<String, Number> series = new XYChart.Series<String, Number>();
	ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
	
	Double randomNumber = new Double(Math.random()*100);

	public void initialize() {
				lineChartData.add(series);
				LineChart.setData(lineChartData);
	}

	public void actualizeChart() {
		

	}

	public void startRandomData() {
		
		if (randomData.isRunning())
			randomData.cancel();
		else
			randomData.restart();
		
	}

	Service<Void> randomData = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				@Override
				protected Void call() {
					while (!isCancelled()) {
						try {
							
							String dateString = new SimpleDateFormat("HH:mm:ss").format(new Date());
							double negpos = (Math.random()*1)-(Math.random()*1);
							randomNumber = Math.abs(new Double(Math.random()*randomNumber)+((100-(randomNumber/10))*negpos));
							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									series.getData().add(new XYChart.Data<String, Number>(dateString,
											new Double(randomNumber)));
								}

							});

							System.out.println("New Data added");
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return null;
				}
			};
		}
	};

}
