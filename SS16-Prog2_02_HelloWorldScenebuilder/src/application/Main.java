package application;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	@FXML
	private Pane schriftfeld;		//Pane, in dem der Text eingefügt
	@FXML
	private ColorPicker worldcolor;
	@FXML
	private TextField usertext;		//der Nutzer kann den einzufügenden Text selbst festlegen
	@FXML
	private MenuButton stepCount;	//wie oft soll der eingegebene Text in das Pane eingefügt werden
	@FXML
	private MenuItem steps1;
	@FXML
	private MenuItem steps10;
	@FXML
	private MenuItem steps25;
	@FXML
	private MenuItem steps50;
	@FXML
	private MenuItem close;
	@FXML
	private MenuItem help;
	

	@Override
	public void start(Stage primaryStage) {
		try {

			new FXMLLoader();
			AnchorPane test = FXMLLoader.load(this.getClass().getResource("helloworld.fxml"));

			primaryStage.setScene(new Scene(test));

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

	@FXML
	public void onButtonPress(Event mouseEvent) {
		Rectangle clip = new Rectangle(schriftfeld.getWidth(), schriftfeld.getHeight());
		schriftfeld.setClip(clip);														//das Pane wird geclipt, sodass Text nicht mehr außerhalb angezeigt werden kann
		//

		for (int i = Integer.parseInt(stepCount.getText()); i > 0; i--) { 	//int i ist der Text des Menüs "stepCount" als Integer geparsed ... crude, but working
			Text t = new Text();
			t.setFill(worldcolor.getValue()); 								//Text wird eingefärbt mit der Farbe des Colorpickers
			t.setTranslateX(Math.random() * (schriftfeld.getWidth() - (usertext.getLength()-1))); //verringert die maximale zufällige Rechtsausrichtung des eingefügten Textes um seine Länge geteilt durch 1.5
			t.setTranslateY(Math.random() * schriftfeld.getHeight());
			t.setText(usertext.getText()); 									//setzt den vom User eingegebenen Text als einzufügenden Text
			schriftfeld.getChildren().add(t);
		}
	}

	@FXML
	public void onWipeOut(Event mouseEvent) { 	//Methode, um alle Texte in der Pane zu löschen
		schriftfeld.getChildren().clear();
	}

	public void steps1Set(Event mouseEvent) {	//die Methoden, mit denen die Schrittzahl entsprechend dem angeklickten MenuItem festgelegt wird... das geht bestimmt noch weniger umständlich
		stepCount.setText(steps1.getText());
	}
	public void steps10Set(Event mouseEvent) {
		stepCount.setText(steps10.getText());
	}
	public void steps25Set(Event mouseEvent) {
		stepCount.setText(steps25.getText());
	}
	public void steps50Set(Event mouseEvent) {
		stepCount.setText(steps50.getText());
	}
	
	public void closeApp(Event mouseEvent){
		System.exit(0);
	}
	
	public void thereIsNoHelp(Event mouseEvent){
		for (int i = 16; i > 0; i--) {
			Text t = new Text();
			t.setFill(Color.RED);
			t.setFont(Font.font(i+24));
			t.setTranslateX(Math.random() * (schriftfeld.getWidth() - (schriftfeld.getWidth()/2))); 
			t.setTranslateY(Math.random() * schriftfeld.getHeight());
			t.setStroke(Color.BLACK);
			t.setText("THERE IS NO HELP!"); 									
			schriftfeld.getChildren().add(t);	//THERE IS NO HELP! in groß und bedrohlich über alles kleistern
		}
	}
}
