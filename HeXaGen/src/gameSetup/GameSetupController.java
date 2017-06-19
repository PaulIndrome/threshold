package gameSetup;

import java.io.IOException;

import application.EventController;
import gameobjects.HexTile;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import material.StartEndCleanArray;

public class GameSetupController {

	@FXML
	AnchorPane rootPane;
	@FXML
	Pane hexaPane;
	@FXML
	Button startButton;

	boolean[] gridSetup = new boolean[255];
	HexButton[] hexButtonArray = new HexButton[153];

	
	
	public void initialize() {
		drawHexagonals();
		registerReleaseHandler();
	}

	public void drawHexagonals() {
		StartEndCleanArray seca = new StartEndCleanArray();
		int ID = 0;
		int step = 0;
		int radius = 20;
		int Xstart = -20;
		double Ystart = 5 - (4.5f * radius * 2);
		double X = 0;
		double Y = 0;
		for (int row = 0; row < 17; row++) {
			Y = (row * radius * 2);
			for (int col = 0; col < 15; col++) {
				X = (col * radius * 1.75f);
				if (ID == 8 || ID == 144) {
					HexButton hx = new HexButton(Xstart + X, Ystart + Y, radius, gridSetup, ID);
					hexButtonArray[ID] = hx;
					hexaPane.getChildren().add(hx);
					ID++;
					step++;
				} else if (seca.contains(step) || col == 0 || col == 14 || row == 0 || row == 16) {
					step++;
				} else {
					HexButton hx = new HexButton(Xstart + X, Ystart + Y, radius, gridSetup, ID);
					hx.setMouseBehavior();
					hexButtonArray[ID] = hx;
					hexaPane.getChildren().add(hx);
					ID++;
					step++;
				}
				Y += radius;
			}
			X -= 15 * radius * 1.75f;
		}
	}

	public void startGame() {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../application/GameScreen.fxml"));
			AnchorPane root;

			root = loader.load();
			
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

			EventController eC = (EventController)loader.getController();
			eC.initializeHexGrid(gridSetup);
			
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setWidth(root.getPrefWidth());
			primaryStage.setHeight(root.getPrefHeight());
			primaryStage.show();
			primaryStage.centerOnScreen();
			
//			rootPane.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerReleaseHandler(){
		rootPane.addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
			for(HexButton hx : hexButtonArray){
				hx.draggedOverReset();
			}
		});
	}
	
}
