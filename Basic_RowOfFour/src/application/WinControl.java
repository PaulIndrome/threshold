package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class WinControl {

	@FXML
	private Button closeButton;
	@FXML
	private Button resetButton;
	@FXML
	private Circle winCircle;

	private Group circleGroup;

	public void initialize() {

	}

	public void createWin(int team) {
		switch (team) {
		case 1:
			winCircle.setFill(Color.RED);
			break;
		case 2:
			winCircle.setFill(Color.BLUE);
			break;
		case 3:
			winCircle.setFill(Color.CYAN);
			break;
		case 4:
			winCircle.setFill(Color.GREEN);
			break;
		default:
			break;
		}

		closeButton.toFront();
		resetButton.toFront();
	}

	public void reset() {
		circleGroup.getChildren().clear();
	}

	public void close() {
		circleGroup.getScene().getWindow().hide();
	}
	
	public Circle getCircle(){
		return winCircle;
	}

}
