package application;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class ChessFigure {

	private boolean team;
	private int type;
	private int col;
	private int row;
	private ImageView icon;
	private Pane glowPane;
	private Pane fieldPane;
	private FieldRectangle[][] rectArray;

	public abstract void firstSet(int col, int row);

	public abstract void canHit();

	public abstract void canMove();

	public abstract boolean getTeam();

	public abstract int getType();

	public abstract void updateColRow(int col, int row);

	public abstract void setCf(int col, int row);

	public abstract void clearIcon();

}
