package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FieldRectangle {

	/*
	 * FieldRectangle is a wrapper class, combining an instance of ChessFigure
	 * and javaFX.Rectangle in itself
	 */

	private Rectangle re;
	private ChessFigure cf;
	private Pane fieldViewPane;

	public FieldRectangle(double X, double Y, double width, double height, Color fill, Pane fieldViewPane) {
		this.re = new Rectangle(X, Y, width, height);
		re.setFill(fill);
		this.fieldViewPane = fieldViewPane;
		this.fieldViewPane.getChildren().add(this.re);
		cf = null;
	}

	public Rectangle getRe() {
		return re;
	}

	public void setRe(Rectangle re) {
		this.re = re;
	}

	public ChessFigure getCf() {
		return cf;
	}

	public void setCf(ChessFigure cf) {
		this.cf = cf;
	}

	public double getX() {
		return re.getX();
	}

	public double getY() {
		return re.getY();
	}
	
	public boolean hasCf(){
		return cf != null;
	}
	
	public boolean clearCf(){
		if(this.hasCf()){
			this.cf.clearIcon();
			this.cf = null;
			return true;
		} else
			return false;
	}

}
