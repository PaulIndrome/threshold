package application;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	SimpleIntegerProperty[] scoresProp = { 	new SimpleIntegerProperty(0), 
											new SimpleIntegerProperty(0),
											new SimpleIntegerProperty(0) };

	SimpleIntegerProperty gamesPlayed = new SimpleIntegerProperty(0);
	SimpleIntegerProperty gameCardsTotal = new SimpleIntegerProperty(0);
	SimpleDoubleProperty averageCards = new SimpleDoubleProperty(0);
	SimpleDoubleProperty averageScore = new SimpleDoubleProperty(0);

	int gamesPlayedInt = 0;
	int gameCardsTotalInt = 0;
	int gameScoreTotalInt = 0;
	double averageCardsDouble = 0;
	double averageScoreDouble = 0;
	
	int[] scoresInt = {0,0,0};
	
	TextArea text = new TextArea();
	Label wins = new Label();
	Label stands = new Label();
	Label losses = new Label();
	Label gamesPlayedLabel = new Label();
	Label gameCardsTotalLabel = new Label();
	Label averageCardsLabel = new Label();
	Label averageScoreLabel = new Label();
	
	Text tW = new Text("wins");
	Text tS = new Text("stands");
	Text tL = new Text("loss");
	Text tG = new Text("games");
	Text tC = new Text("cards");
	Text taC = new Text("average cards");
	Text taS = new Text("average score"); 
	

	Button testButton = new Button("test it");

	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new Pane();

			setSceneMaterial();

			root.getChildren().addAll(text, testButton, wins, stands, losses, gamesPlayedLabel, gameCardsTotalLabel, averageCardsLabel, averageScoreLabel, tW, tS, tL, tG, tC, taC, taS);
			Scene scene = new Scene(root, 1000, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSceneMaterial() {
		// large textArea
		text.setTranslateY(100);
		text.setPrefWidth(1000);
		text.setPrefHeight(400);

		// set Button method
		testButton.setOnAction(e -> deckTest());

		// bind everything
		wins.textProperty().bind(scoresProp[2].asString());
		stands.textProperty().bind(scoresProp[1].asString());
		losses.textProperty().bind(scoresProp[0].asString());
		
		gamesPlayedLabel.textProperty().bind(gamesPlayed.asString());
		gameCardsTotalLabel.textProperty().bind(gameCardsTotal.asString());
		averageCardsLabel.textProperty().bind(averageCards.asString());
		averageScoreLabel.textProperty().bind(averageScore.asString());
		
		// resize and locate those areas
		wins.setPrefSize(50, 20);
		stands.setPrefSize(50, 20);
		losses.setPrefSize(50, 20);
		wins.setTranslateX(200);
		stands.setTranslateX(260);
		losses.setTranslateX(310);
		
		//even more Labels
		gamesPlayedLabel.setPrefSize(50, 20);
		gamesPlayedLabel.setTranslateX(200);
		gamesPlayedLabel.setTranslateY(40);
		
		gameCardsTotalLabel.setPrefSize(50, 20);
		gameCardsTotalLabel.setTranslateX(260);
		gameCardsTotalLabel.setTranslateY(40);
		
		averageCardsLabel.setPrefSize(50, 20);
		averageCardsLabel.setTranslateX(310);
		averageCardsLabel.setTranslateY(40);
		
		averageScoreLabel.setPrefSize(50, 20);
		averageScoreLabel.setTranslateX(360);
		averageScoreLabel.setTranslateY(0);
		
		//descriptions
		tW.setX(200);
		tS.setX(260);
		tL.setX(310);
		tW.setY(27);
		tS.setY(27);
		tL.setY(27);
		tG.setX(200);
		tC.setX(260);
		taC.setX(310);
		taS.setX(360);
		tG.setY(67);
		tC.setY(67);
		taC.setY(67);
		taS.setY(27);
		
		
		

	}

	public static void main(String[] args) {
		launch(args);
	}

	
	public void deckTest() {
		for(int i = 0; i < 10; i++){
			deckTestSingle();
		}
	}
	
	public void deckTestSingle(){
		Decktester dT = new Decktester(7, text);
		int result = dT.spiele();
		scoresInt[result]++;
		scoresProp[result].set(scoresInt[result]);
		
		gamesPlayedInt++;
		gamesPlayed.set(gamesPlayedInt);
		
		gameCardsTotalInt += dT.handKarten;
		gameCardsTotal.set(gameCardsTotalInt);
		
		averageCardsDouble = (double)gameCardsTotalInt / (double)gamesPlayedInt;
		averageCards.set(averageCardsDouble);
		
		gameScoreTotalInt += dT.spielWert;
		averageScoreDouble = (double)gameScoreTotalInt / (double)gamesPlayedInt;
		averageScore.set(averageScoreDouble);
		
		
	}
}
