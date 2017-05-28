package gameboard;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Field extends Rectangle {

	/*
	 * hit property changes when rectangle is clicked
	 * 0 means not clicked
	 * 1 means clicked for a water hit
	 * 2 means clicked for a ship hit
	 */
	SimpleIntegerProperty hit;
	
	int posX;
	int posY;
	
	//Circle
	Circle status = new Circle(10, Color.TRANSPARENT);
	
	public Field(int x, int y, int size){
		this.posX = x;
		this.posY = y;
		this.setFill(Color.GREY);
		this.resize(size, size);
		/*
		 * position of rectangle to be determined dynamically by position x and y
		 */
		this.setX(0);
		this.setY(0);
		
		status.setMouseTransparent(true);
		status.translateXProperty().bind(this.xProperty().add(this.getWidth()/2));
		status.translateYProperty().bind(this.yProperty().add(this.getHeight()/2));
		
	}
	
}
