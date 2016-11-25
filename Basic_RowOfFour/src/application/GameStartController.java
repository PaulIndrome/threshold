package application;

import ColumnBasedGame.ColumnGame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GameStartController {

	@FXML
	private Button startButton;
	@FXML
	private TextField widthField;
	@FXML
	private TextField heightField;
	@FXML
	private Button exitButton;
	@FXML
	private Button ColumnGameButton;
	@FXML
	private TextField winstreakField;
	@FXML
	private CheckBox randomModeCheck;
	@FXML
	private CheckBox dynamicModeCheck;

	private static int width;
	private static int height;
	private static int streak;
	private boolean randomMode;
	private static boolean currentTeam = true;
	private static GameRectangle[][] rectArray;

	public void initialize() {

		width = Integer.parseInt(widthField.getText());
		height = Integer.parseInt(heightField.getText());
		streak = Integer.parseInt(winstreakField.getText());

		randomMode = randomModeCheck.isSelected();

		randomModeCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				randomMode = newValue.booleanValue();
			}
		});
		widthField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals(""))
					width = Integer.parseInt(newValue);
			}
		});
		heightField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals(""))
					height = Integer.parseInt(newValue);
			}
		});
		winstreakField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals(""))
					streak = Integer.parseInt(newValue);
			}
		});

	}

	public void start() {
		System.out.println(width + " " + height + " " + streak + " " + randomMode);
		Stage secondary = new Stage();
		if (!dynamicModeCheck.isSelected())
			new StaticGame(width, height, secondary);
		else
			new DynamicGame(secondary);
	}
	
	public void startColumnGame(){
		System.out.println(width + " " + height + " " + streak + " " + randomMode);
		Stage secondary = new Stage();
		new ColumnGame(width, height, secondary);
	}

	public void exit() {
		System.exit(0);
	}
	
	public static boolean getCurrentTeam(){
		return GameStartController.currentTeam;
	}
	
	public static void switchTeam(){
		currentTeam = !currentTeam;
	}
	
	public static int getStreak(){
		return streak;
	}
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	
	public static void setRectArray(GameRectangle[][] rectArray){
		GameStartController.rectArray = rectArray;
	}
	
	public static GameRectangle[][] getRectArray(){
		return GameStartController.rectArray;
	}
}
