package gameobjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends Circle{
	
	boolean team;
	

	public Piece(double mpx, double mpy, double r){
		super(mpx, mpy, r);
		super.setFill(Color.RED);
		setMouseBehavior();
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
