package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class HitCircle{

	private Circle hC;
	
	public HitCircle(double radius, Color fill){
		this.hC = new Circle(radius, fill) ;
	}
	
	public void setOnMouseClicked(EventHandler<? super MouseEvent> value){
		this.hC.setOnMouseClicked(value);
	}
	
	public void setStroke(Color color){
		this.hC.setStroke(color);
	}
	
	public void setCenterX(double x,double y){
		this.hC.setCenterX(x);
		this.hC.setCenterY(y);
	}
	
	public Circle getCircle(){
		return this.hC;
	}
	
}
