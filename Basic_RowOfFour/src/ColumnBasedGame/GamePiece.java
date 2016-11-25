package ColumnBasedGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GamePiece {

	private Circle circle;
	private double radius;
	private boolean team;
	
	public GamePiece(int col, int row) {
		double columnWidth = ColumnGame.getColumnWidth();
		double rowHeight = ColumnGame.getRowHeight();
		
		this.team = ColumnGame.getTeam();
		if (columnWidth < rowHeight) {
			radius = columnWidth/2 - 1;
		} else {
			radius = rowHeight/2 -1;
		}
		if(this.team){
			this.circle = new Circle(radius, Color.GOLD);
		} else {
			this.circle = new Circle(radius, Color.BLUEVIOLET);
		}
		circle.setMouseTransparent(true);
		circle.setCenterX(ColumnGame.getColumn(col).getX()+(columnWidth/2));
		circle.setCenterY(0);
		
		
		
		FallService.addCircle(circle, row);
		
		
	}
	
	public Circle getCircle(){
		return circle;
	}

	public boolean getTeam() {
		return team;
	}
	
}
