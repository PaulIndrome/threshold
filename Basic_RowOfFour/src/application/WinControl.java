package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class WinControl {

	@FXML
	private Button closeButton;
	@FXML
	private Button resetButton;
	@FXML
	private Circle winCircle;

	private Group circleGroup;
	private Parent root;

	private boolean rootNull = true;

	public WinControl() {
		System.out.println("WinControl says hello");
		root = null;

		initiateFXML();
		/*
		 * Stage stage = new Stage(); stage.setScene(new Scene(root));
		 * System.out.println("and goodbye"); stage.show();
		 */
	}

	public void initialize() {

	}

	public void initiateFXML(){
		if(rootNull){
			try {
					if(rootNull){
					root = FXMLLoader.load(getClass().getResource("/application/WinIcon.fxml"));
					System.out.println("Inside");
					rootNull = false;
					}
			} catch (IOException e) {
				System.out.println("Bla");
				e.printStackTrace();
			}
			rootNull = false;
		}

	}

	public void createWin(int team, Group circleGroup) {

		this.circleGroup = circleGroup;

		switch (team) {
		case 1:
			winCircle.setFill(Color.GOLD);
			break;
		case 2:
			winCircle.setFill(Color.BLUEVIOLET);
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

		circleGroup.getChildren().addAll(closeButton, resetButton, winCircle);
	}

	public void reset() {
		circleGroup.getChildren().clear();
	}

	public void close() {
	}

}
