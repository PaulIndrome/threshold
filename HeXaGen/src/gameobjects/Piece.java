package gameobjects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import material.MovementPattern;

public class Piece extends Circle{
	
	boolean team;
	MovementPattern movePat;

	public Piece(double mpx, double mpy, double r, MovementPattern mPattern){
		super(mpx, mpy, r);
		super.setFill(Color.RED);
		setMouseBehavior();
		movePat = mPattern;
	}

	private void setMouseBehavior() {
		super.setStrokeWidth(0);
		super.setStroke(Color.BLUE);
		
		super.setOnMouseEntered(e->{
			super.setStrokeWidth(2);
		});
		super.setOnMouseExited(e->{
			super.setStrokeWidth(0);
		});
	}
	
}
