package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controls {

	// Variablen für Bedienelemente und Anzeige
	@FXML
	private Button matteAnButton;
	@FXML
	private Button matteAusButton;
	@FXML
	private Button okMinWert;
	@FXML
	private Button okMaxWert;
	@FXML
	private Button diagramm;

	@FXML
	private Button speichern;
	@FXML
	private Button schliessen;

	@FXML
	private TextField minTempTF;
	@FXML
	private TextField maxTempTF;

	@FXML
	private Label aktTemp1;
	@FXML
	private Label aktTemp2;
	@FXML
	private Label aktHydr;
	@FXML
	private Label minWertLabel;
	@FXML
	private Label maxWertLabel;

	@FXML
	private Circle matteStatusDot;
	@FXML
	private Circle temp1Dot;
	@FXML
	private Circle temp2Dot;
	@FXML
	private Circle hydrDot;

	private static CategoryAxis xAxis = new CategoryAxis();
	private static NumberAxis yAxis = new NumberAxis();

	public LineChart<String, Number> verlauf = new LineChart<String, Number>(xAxis, yAxis);
	public ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
	public XYChart.Series<String, Number> diagrammdaten = new XYChart.Series<String, Number>();
	public XYChart.Series<String, Number> diagrammdaten2 = new XYChart.Series<String, Number>();

	@FXML
	public void initialize() {
		Main.theFatCuntRoller = this;
		erstelleDiagramm();

		try {
			String zeile;
			String xWert;
			String yWert;
			FileReader fileReader = new FileReader("Temperaturdaten2.txt");
			BufferedReader buffer = new BufferedReader(fileReader);

			while (buffer.ready()) {
				zeile = buffer.readLine();
				xWert = zeile.substring(5, 13);
				yWert = zeile.substring(14, 19);
				System.out.println(xWert + " " + yWert);
				verlauf.getData().get(1).getData()
						.add(new XYChart.Data<String, Number>(xWert, Double.parseDouble(yWert.split(",")[0])));
			}

			buffer.close();
			fileReader.close();

		} catch (FileNotFoundException e) {
			System.err.println("File nicht gefunden");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			String zeile;
			String xWert;
			String yWert;
			FileReader fileReader = new FileReader("Temperaturdaten.txt");
			BufferedReader buffer = new BufferedReader(fileReader);

			while (buffer.ready()) {
				zeile = buffer.readLine();
				xWert = zeile.substring(5, 13);
				yWert = zeile.substring(14, 19);
				System.out.println(xWert + " " + yWert);
				verlauf.getData().get(0).getData()
						.add(new XYChart.Data<String, Number>(xWert, Double.parseDouble(yWert.split(",")[0])));
			}

			buffer.close();
			fileReader.close();

		} catch (FileNotFoundException e) {
			System.err.println("File nicht gefunden");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("I-N-I-T-I-A-L-I-S-I-N-G");

		aktTemp1.setText(Data.aktTemp1 + "");
		aktTemp2.setText(Data.aktTemp2 + "");
		aktHydr.setText(Data.aktHydr + "");
		minWertLabel.setText(Data.minTemp + "");
		maxWertLabel.setText(Data.maxTemp + "");

		System.out.println(verlauf.toString());

		updateWerte();

		System.out.println(verlauf.getData().isEmpty());

	}

	// Eventhandling

	// aktualisiert Diagramm und Statusanzeigen
	public void updateWerte() {

		String datum = new SimpleDateFormat("HH:mm:ss").format(new Date());
		verlauf.getData().get(0).getData().add(new XYChart.Data<String, Number>(datum, Data.aktTemp1));
		verlauf.getData().get(1).getData().add(new XYChart.Data<String, Number>(datum, Data.aktTemp2));

		// System.out.println(verlauf.getData().get(0).getData().get(0));

		// System.out.println("Ich wurde ausgeführt!");

		Data werte = new Data();

		if (werte.hydrAbgleich(Data.aktHydr) == 0) {
			hydrDot.setFill(Color.GREEN);
		} else if (werte.hydrAbgleich(Data.aktHydr) == 1) {
			hydrDot.setFill(Color.RED);
		} else if (werte.hydrAbgleich(Data.aktHydr) == 2) {
			hydrDot.setFill(Color.BLUE);
		}

		if (werte.tempAbgleich(Data.aktTemp1) == 0) {
			temp1Dot.setFill(Color.GREEN);
		} else if (werte.tempAbgleich(Data.aktTemp1) == 1) {
			temp1Dot.setFill(Color.BLUE);
		} else if (werte.tempAbgleich(Data.aktTemp1) == 2) {
			temp1Dot.setFill(Color.RED);
		}

		if (werte.tempAbgleich(Data.aktTemp2) == 0) {
			temp2Dot.setFill(Color.GREEN);
		} else if (werte.tempAbgleich(Data.aktTemp2) == 1) {
			temp2Dot.setFill(Color.BLUE);
		} else if (werte.tempAbgleich(Data.aktTemp2) == 2) {
			temp2Dot.setFill(Color.RED);
		}

		if (Data.statusMatte) {
			matteStatusDot.setFill(Color.GREEN);
		} else {
			matteStatusDot.setFill(Color.RED);
		}
	}

	public void matteAn(ActionEvent e) {
		Data.statusMatte = true;
		matteStatusDot.setFill(Color.GREEN);
	}

	public void matteAus() {
		Data.statusMatte = false;
		matteStatusDot.setFill(Color.RED);
	}

	public void setMinTemp() {
		double temp = 0;

		if (minTempTF.getText().trim().isEmpty()) {
			System.err.println("invalid character");

		} else if (!minTempTF.getText().matches("[0-9][0-9].[0-9]")) {
			System.err.println("invalid character");

		} else if (minTempTF.getText().contains(",")) {
			System.err.println("please use '.' instead of ',' ");

		} else {
			minWertLabel.setText(minTempTF.getText());
			temp = Data.minTemp;
			Data.minTemp = Double.parseDouble(minTempTF.getText());
			System.out.println(Data.minTemp);
		}

		if (Double.parseDouble(minWertLabel.getText()) > Data.maxTemp) {
			minWertLabel.setText(temp + "");
			Data.minTemp = temp;
			System.out.println("Wert muss unter Maximalwert liegen");
		}
	}

	public void setMaxTemp() {
		double temp = 0;

		if (maxTempTF.getText().trim().isEmpty()) {
			System.err.println("invalid character");

		} else if (!maxTempTF.getText().matches("[0-9][0-9].[0-9]")) {
			System.err.println("invalid character");

		} else if (maxTempTF.getText().contains(",")) {
			System.err.println("please use '.' instead of ',' ");

		} else {
			maxWertLabel.setText(maxTempTF.getText());
			Data.maxTemp = Double.parseDouble(maxTempTF.getText());
			System.out.println(Data.maxTemp);
		}

		if (Double.parseDouble(maxWertLabel.getText()) <= Data.minTemp) {
			maxWertLabel.setText(temp + "");
			Data.maxTemp = temp;
			System.out.println("Wert muss über Minimalwert liegen");
		}
	}

	/*
	 * füllt das LineChart 'verlauf' mit Daten aus der ObservableList
	 * 'lineChartData', welche mit der XYChart.Series 'diagrammdaten' gefüllt
	 * wurde
	 */
	public void erstelleDiagramm() {


		lineChartData.add(diagrammdaten);// XYChart.Series an ObservableList
											// übergeben
		lineChartData.add(diagrammdaten2);

		verlauf.setData(lineChartData); // observableList an LineChart übergeben

	}

	// speichert Diagrammdaten in eine Datei
	public void speichern() {

		try {
			FileWriter writer = new FileWriter("Temperaturdaten.txt", true);
			FileWriter writer2 = new FileWriter("Temperaturdaten2.txt", true);
			
			for (int i = 0; i < verlauf.getData().get(0).getData().size(); i++) {
				writer.write(verlauf.getData().get(0).getData().get(i).toString() + "\n");
				writer2.write(verlauf.getData().get(1).getData().get(i).toString() + "\n");
			}
			writer.close();
			writer2.close();

		} catch (IOException e) {
			System.err.println("Speichern fehlgeschlagen");
			e.printStackTrace();
		}

		for (int i = 0; i < verlauf.getData().get(0).getData().size(); i++) {
			System.out.println(verlauf.getData().get(0).getData().get(i));
		}
		System.out.println("Ich speichere!");
	}

	// beendet das Programm und speichert
	public void schliessen(ActionEvent e) {
		speichern();
		System.exit(0);
	}
}
