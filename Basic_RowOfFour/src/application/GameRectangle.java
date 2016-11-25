package application;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameRectangle {

	private Group holder;
	private Circle trueC;
	private Circle falseC;
	private double radius;
	private int team;
	
	public GameRectangle(int columnWidth, int rowHeight, double x, double y, int column, AnchorPane parent, Color stroke) {
		team = 0;
		
		holder = new Group();
		Rectangle r = new Rectangle(x, y, columnWidth, rowHeight);
		r.setFill(Color.TRANSPARENT);
		r.setStroke(stroke);
		r.setMouseTransparent(true);
		
		if (columnWidth < rowHeight) {
			radius = columnWidth/2-2;
		} else {
			radius = rowHeight/2-2;
		}

		trueC = new Circle(radius, Color.GOLD);
		trueC.setCenterX(x + columnWidth / 2);
		trueC.setCenterY(y + rowHeight / 2);

		falseC = new Circle(radius, Color.BLUEVIOLET);
		falseC.setCenterX(x + columnWidth / 2);
		falseC.setCenterY(y + rowHeight / 2);

		trueC.setVisible(false);
		falseC.setVisible(false);
		
		holder.getChildren().addAll(r,trueC,falseC);
		parent.getChildren().add(holder);
		
	}

	public void showTrue() {
		trueC.setVisible(true);
		team = 1;
	}

	public void showFalse() {
		falseC.setVisible(true);
		team = -1;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int color) {
		this.team = color;
	}
	
	public void erase(){
		falseC.setVisible(false);
		trueC.setVisible(false);
		team = 0;
	}
	
}
