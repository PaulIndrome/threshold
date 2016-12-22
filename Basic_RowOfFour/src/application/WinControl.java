package application;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import rowOfFourGame.ColumnControl;

public class WinControl {

	private AnchorPane winRoot;	
	private Button closeButton;
	private Button resetButton;
	private Circle winCircle;

	private ColumnControl cc;
	private Group circleGroup;
	

	public WinControl(int team, ColumnControl cc){
		this.cc = cc;
		
		winCircle = new Circle(100);
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
		closeButton = new Button("close");
		resetButton = new Button("reset");
		
		
		AnchorPane winRoot = new AnchorPane(winCircle, closeButton, resetButton);
		
		
		
	}
	
	
	public void initialize() {

	}

	public void createWin(int team, Group cG) {
		this.circleGroup = cG;
		
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

	public boolean reset() {
		circleGroup.getChildren().clear();
		rootAnchorPane.getChildren().clear();
		rootAnchorPane = null;
		return true;
	}

	public void close() {
		circleGroup.getParent().getScene().getWindow().hide();
	}
	
	public Circle getCircle(){
		return winCircle;
	}

}
