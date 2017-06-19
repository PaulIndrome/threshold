package application;

import gameobjects.HexGrid;
import gameobjects.HexTile;
import gameobjects.Piece;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;

public class EventController {

	boolean activeTeam;
	
	boolean[] gridSetup;
	
	HexGrid gameBoard;
	
	Piece activePiece;
	
	@FXML
	public AnchorPane root;

	EventHandler<Event> mainMouseEventHandler = new EventHandler<Event>() {

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
		root.addEventFilter(MouseEvent.MOUSE_CLICKED, mainMouseEventHandler);
	}
	
	public void initializeHexGrid(boolean[] gridSetup){
		// create array of skipped IDs from deactivated tiles
		int indexArraySize = 0;
		for(boolean b : gridSetup){
			if(!b)
				indexArraySize++;
		}
		int[] skipsArray = new int[indexArraySize];
		int index = 0;
		int step = 0;
		for(boolean b : gridSetup){
			if(!b){
				skipsArray[step] = index;
				step++;
				index++;
			} else {
				index++;
			}
		}
		
		gameBoard = new HexGrid(root.getPrefWidth(), root.getPrefHeight(), skipsArray);
		root.getChildren().add(gameBoard);
		
		spawnTestPiece();
		
	}
	
	public void spawnTestPiece(){
		HexTile spawnTile = gameBoard.getHexTileByID(8);
		double spawnY = spawnTile.getPolygon().getPoints().get(1);
		double spawnX = (spawnTile.getPolygon().getPoints().get(0) + spawnTile.getPolygon().getPoints().get(6)) * 0.5;
		Piece testPiece = new Piece(spawnX, spawnY, spawnTile.getRadius()*0.865, null);
		spawnTile.getChildren().add(testPiece);
	}
	

}
