package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DeckTesterController {

	@FXML
	private Button testBtn;
	@FXML
	private Button saveBtn;
	@FXML
	private Button loadBtn;
	@FXML
	private Button exitBtn;

	@FXML
	private TextField anzTests;
	@FXML
	private TextField zielWert;
	@FXML
	private TextField kartenMengeH;
	@FXML
	private TextField kartenMengeM;
	@FXML
	private TextField kartenMengeL;
	@FXML
	private TextField kartenTeilerH;
	@FXML
	private TextField kartenTeilerM;
	@FXML
	private TextField kartenTeilerL;
	@FXML
	private TextArea logTextArea;

	@FXML
	private Label winsLabel;
	@FXML
	private Label standsLabel;
	@FXML
	private Label lossesLabel;
	@FXML
	private Label gamesPlayedLabel;
	@FXML
	private Label cardsGivenLabel;
	@FXML
	private Label avgCardsPerGameLabel;
	@FXML
	private Label avgScorePerGameLabel;
	@FXML
	private Label kartenWertHLabel;
	@FXML
	private Label kartenWertMLabel;
	@FXML
	private Label kartenWertLLabel;

	private Model values = new Model();

	public void initialize() {
		initializeTextFields();
		addListenersToTextFields();
		
		kartenWertHLabel.textProperty().bind(values.kartenWertHProperty.asString());
		kartenWertMLabel.textProperty().bind(values.kartenWertMProperty.asString());
		kartenWertLLabel.textProperty().bind(values.kartenWertLProperty.asString());
	
	}

	

	public void test() {

	}

	public void load() {

	}

	public void save() {

	}

	public void exit() {

	}

	// hitting ENTER on one of the divider fields
	public void dividersChanged(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			TextField source = (TextField) e.getSource();
			values.updateAnyTextField(source.getText(), source.getId());
			logTextArea.appendText("Neue Kartenwerte:\tH = " + values.kartenWertH + "\tM = " + values.kartenWertM
					+ "\tL = " + values.kartenWertL + "\n");
			e.consume();
		}
	}

	private void addListenersToTextFields() {
		anzTests.textProperty().addListener(new TextFieldListener(anzTests, values, false));
		zielWert.textProperty().addListener(new TextFieldListener(zielWert, values, true));

		kartenMengeH.textProperty().addListener(new TextFieldListener(kartenMengeH, values, false));
		kartenMengeM.textProperty().addListener(new TextFieldListener(kartenMengeM, values, false));
		kartenMengeL.textProperty().addListener(new TextFieldListener(kartenMengeL, values, false));

		kartenTeilerH.textProperty().addListener(new TextFieldListener(kartenTeilerH, values, true));
		kartenTeilerM.textProperty().addListener(new TextFieldListener(kartenTeilerM, values, true));
		kartenTeilerL.textProperty().addListener(new TextFieldListener(kartenTeilerL, values, true));
		
		kartenTeilerH.setOnKeyPressed(e -> {
			dividersChanged(e);
		});
		kartenTeilerM.setOnKeyPressed(e -> {
			dividersChanged(e);
		});
		kartenTeilerL.setOnKeyPressed(e -> {
			dividersChanged(e);
		});
	}

	private void initializeTextFields() {
		anzTests.setText("0");
		zielWert.setText("0");
		kartenMengeH.setText("0");
		kartenMengeM.setText("0");
		kartenMengeL.setText("0");
		kartenTeilerH.setText("0");
		kartenTeilerM.setText("0");
		kartenTeilerL.setText("0");
	}
}
