package ColumnBasedGame;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PutButton {

	private Rectangle icon;
	private int columNo;
	private int row;

	public PutButton(double columnWidth2, double rowHeight2, int xPos, int columNo, int height, Group column2) {
		this.columNo = columNo;
		this.row = height;
		icon = new Rectangle(xPos, 0, columnWidth2, rowHeight2);
		icon.setFill(Color.TRANSPARENT);
		icon.setOnMouseClicked(e -> {
			if (row > 0) {
				CheckService.put(columNo, row);
				ColumnGame.getColumn(columNo).pushPiece(new GamePiece(columNo, row));
				row--;
				ColumnGame.switchTeam();
			}
		});
		column2.getChildren().add(icon);
	}

}
