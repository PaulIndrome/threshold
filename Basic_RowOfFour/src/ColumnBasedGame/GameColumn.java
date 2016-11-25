package ColumnBasedGame;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameColumn {

	private Group column;
	private GamePiece[] pieces;
	private int fallDepth;
	private double columnWidth;
	private double rowHeight;
	private int xPos;

	public GameColumn(double columnWidth, double rowHeight, int columNo, int columnDepth, double margin,
			AnchorPane parent, Color stroke) {
		this.columnWidth = columnWidth;
		this.rowHeight = rowHeight;
		column = new Group();
		Rectangle r;
		pieces = new GamePiece[columnDepth + 1];
		fallDepth = columnDepth;
		xPos = (int) (columNo * (columnWidth + margin));

		new PutButton(columnWidth, rowHeight * columnDepth + 2 * rowHeight, xPos, columNo, columnDepth, column);

		for (int c = 0; c < columnDepth; c++) {
			r = new Rectangle(xPos, 2 * rowHeight + (c * rowHeight), columnWidth, rowHeight);
			r.setFill(Color.TRANSPARENT);
			r.setStroke(stroke);
			r.setMouseTransparent(true);
			column.getChildren().add(r);
		}

		parent.getChildren().add(column);

	}

	public int getFallDepth() {
		return fallDepth;
	}

	public void pushPiece(GamePiece piece) {
		pieces[fallDepth] = piece;
		column.getChildren().add(piece.getCircle());
		fallDepth--;
	}

	public int getX() {
		return xPos;
	}

	public double getColumnWidth() {
		return columnWidth;
	}

	public double getRowHeight() {
		return rowHeight;
	}

	public boolean getTeam(int pos) {
		return pieces[pos].getTeam();
	}

	public boolean hasPieceAt(int pos) {
		return pieces[pos] != null;
	}

}
