package gameobjects;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class HexTile extends Pane {

	int ID;
	int[] neighbourIDs = new int[6];

	Color tileColor = Color.WHITE;

	public HexTile(double mpx, double mpy, double r, Color c, int ID) {
		super.getChildren().add(new Polygon(mpx - r / 1.15f, mpy + r / 1.9f, // lower left
				mpx - r / 1.15f, mpy - r / 1.9f, // upper left
				mpx, mpy - r / 0.99f, // top
				mpx + r / 1.15f, mpy - r / 1.9f, // upper right
				mpx + r / 1.15f, mpy + r / 1.9f, // lower right
				mpx, mpy + r / 0.99f); // bottom))
		
		super(
		
		super.setRotate(90);

		this.ID = ID;
		
		this.setStrokeType(StrokeType.INSIDE);
		this.setStrokeWidth(2);
		this.setFill(c);
		tileColor = c;
		
		setMouseBehavior();

	}

	private void setMouseBehavior() {
		this.setOnMouseEntered(e -> {
			this.setStroke(Color.BLACK);
		});

		this.setOnMouseExited(e -> {
			this.setStroke(tileColor);
		});
	}

}
