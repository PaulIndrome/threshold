package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import rowOfFourGame.ColumnModel;

public class GameStartController {

	@FXML
	private TextField widthField;
	@FXML
	private TextField heightField;
	@FXML
	private TextField winstreakField;
	@FXML
	private TextField playersField;
	@FXML
	private Button exitButton;
	@FXML
	private Button ColumnGameButton;
	@FXML
	private CheckBox randomModeCheck;

	private static int width;
	private static int height;
	private static int streak;
	private static int players;
	private boolean[] mode = new boolean[]{false,false,false,false};
	private static boolean currentTeam = true;
	private static GameRectangle[][] rectArray;

	public void initialize() {
		randomModeCheck.setTooltip(new Tooltip("Play with randomized teams."));
		widthField.setTooltip(new Tooltip("Maximum width = 100"));
		heightField.setTooltip(new Tooltip("Maximum height = 100"));
		playersField.setTooltip(new Tooltip("Maximum players = 4"));
		winstreakField.setTooltip(new Tooltip("Winstreak must be smaller than field's dimensions minus one."));
		

		width = Integer.parseInt(widthField.getText());
		height = Integer.parseInt(heightField.getText());
		streak = Integer.parseInt(winstreakField.getText());
		players = Integer.parseInt(playersField.getText());


		randomModeCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					mode[0] = newValue.booleanValue();
					System.out.println("Random mode = " + mode[0]);
			}
		});
		
		widthField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals("") && Integer.parseInt(newValue)<101 && Integer.parseInt(newValue)>-101)
					width = Math.abs(Integer.parseInt(newValue));
			}
		});
		
		heightField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals("") && Integer.parseInt(newValue)<101 && Integer.parseInt(newValue)>-101)
					height = Math.abs(Integer.parseInt(newValue));
			}
		});
		
		winstreakField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals("") && Integer.parseInt(newValue)<width-1  && Integer.parseInt(newValue)>-width+1)
					streak = Math.abs(Integer.parseInt(newValue));
			}
		});
		
		playersField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals("") && Integer.parseInt(newValue)<5  && Integer.parseInt(newValue)>-5)
					players = Math.abs(Integer.parseInt(newValue));
			}
		});

	}
	
	public void startColumnGame(){
		new ColumnModel(width, height, streak, players, mode);
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
