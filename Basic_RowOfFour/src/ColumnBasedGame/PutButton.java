package ColumnBasedGame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PutButton {

	private Rectangle icon;
	private int column;
	private int row;

	public PutButton(double columnWidth2, double rowHeight2, int xPos, int columNo, int height, Group column2) {
		this.column = columNo;
		this.row = height;
		icon = new Rectangle(xPos, 0, columnWidth2, rowHeight2);
		icon.setFill(Color.TRANSPARENT);
		icon.setOnMouseClicked(e -> {
			if (row > 0) {
				ColumnGame.getColumn(column).pushPiece(new GamePiece(column, row));
				row--;
				ColumnGame.switchTeam();
			}
		});
		column2.getChildren().add(icon);
	}

}
