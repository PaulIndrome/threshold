package application;

import gameobjects.HexTile;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EventController {

	boolean pieceActive = false;

	@FXML
	public AnchorPane root;

	EventHandler<Event> topHandler = new EventHandler<Event>() {

		@Override
		public void handle(Event event) {
			if (event.getTarget() instanceof Node) {
				Node target = (Node) event.getTarget();
				while (target.getParent() != root) {
					target = target.getParent();
				}
				target.toFront();
			}
			event.consume();
		}
	};

	public void initialize() {
		root.addEventFilter(MouseEvent.MOUSE_CLICKED, topHandler);
		drawHexagonals();
	}

	public void drawHexagonals() {
		int radius = 24;
		int row = 0;
		int 
		root.getChildren().add(new HexTile(50, 50, 24, Color.BURLYWOOD, 0));
		for(int column = 1; i <8; i++){
			root.getChildren().add(new HexTile(50+(i*radius*1.55f), 50+(i*radius*0.85f), radius, Color.BURLYWOOD, i));
		}
	}

}
