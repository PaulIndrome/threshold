package gameSetup;

import javafx.event.EventType;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class HexButton extends Pane {

	boolean draggedOver = false;
	int index;
	double radius;
	boolean[] gridSetup;
	Polygon buttonHex;

	public HexButton(double leftX, double leftY, double radius, boolean[] gridSetup, int index) {
		super();
		super.setTranslateX(leftX);
		super.setTranslateY(leftY);

		this.radius = radius;
		this.gridSetup = gridSetup;
		this.index = index;
		this.gridSetup[index] = true;

		double ratioY = 0.865;
		double ratioX = 0.5;
		leftX = 0;
		leftY = 2 * radius * 0.43f;

		buttonHex = new Polygon(leftX, leftY, // left
				leftX + radius * ratioX, leftY - radius * ratioY, // upper left
				leftX + radius * (1 + ratioX), leftY - radius * ratioY, // upper
																		// right
				leftX + radius * 2, leftY, // right
				leftX + radius * (1 + ratioX), leftY + radius * ratioY, // lower
																		// right
				leftX + radius * ratioX, leftY + radius * ratioY); // lower left

		super.getChildren().add(buttonHex);
	}

	public void setMouseBehavior() {

		buttonHex.setStrokeType(StrokeType.INSIDE);
		buttonHex.setStrokeWidth(radius / 18);
		buttonHex.setFill(Color.LIGHTGREY);

		buttonHex.setOnMouseEntered(e -> {
			buttonHex.setStroke(Color.BLACK);
		});

		buttonHex.setOnMouseExited(e -> {
			buttonHex.setStroke(Color.TRANSPARENT);
		});

		buttonHex.setOnMouseClicked(e -> {
			deactivate();
		});

		buttonHex.addEventHandler(MouseEvent.DRAG_DETECTED, e -> {
			buttonHex.startFullDrag();
		});

		buttonHex.addEventHandler(MouseDragEvent.MOUSE_DRAG_ENTERED, e -> {
			deactivate();
			draggedOver = true;
		});

	}

	public void deactivate() {
		if (!draggedOver) {
			gridSetup[index] = !gridSetup[index];
			if (gridSetup[index]) {
				buttonHex.setFill(Color.LIGHTGREY);
			} else {
				buttonHex.setFill(Color.TRANSPARENT);
			}
		}
	}
	
	public void draggedOverReset(){
		draggedOver = false;
	}

}
