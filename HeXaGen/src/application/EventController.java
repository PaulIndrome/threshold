package application;

import gameobjects.HexTile;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import material.GameBoard;

public class EventController {

	boolean pieceActive = false;

	@FXML
	public AnchorPane root;

	EventHandler<Event> topHandler = new EventHandler<Event>() {

		@Override
		public void handle(Event event) {
			if (event.getTarget() instanceof Polygon) {
				HexTile target = (HexTile) ((Node)event.getTarget()).getParent();
				System.out.println(target.toString());
			} else {
				System.out.println(event.getTarget().toString());
			}
			event.consume();
		}
	};

	public void initialize() {
		root.addEventFilter(MouseEvent.MOUSE_CLICKED, topHandler);
		//drawHexagonals();
		
		GameBoard gba = new GameBoard(root.getPrefWidth(), root.getPrefHeight(), 63,64,75,76,77,88,89);
		root.getChildren().add(gba);
		System.out.println(gba.toString());
		
	}

	

	/*
	 * Math to start next row lower left of first hex of previous row X previous
	 * row first hex - i*radius*2*0.75f Y previous row first hex +
	 * i*radius*2*0.43f
	 */
	/*
	 * minimum hexes per row = 7 maximumx hexes per row = 13
	 */
	/*
	 * Array of booleans 13 columns * 15 rows
	 * iteration over array, true sets hex w id, false sets hex w/o id
	 */
	

}
