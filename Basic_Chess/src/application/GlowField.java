package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GlowField {

	private Rectangle re;
	private Circle ci;
	private int col;
	private int row;
	private Pane glowPane;

	public GlowField(int col, int row, Pane glowPane) {
		this.ci = new Circle(12, Color.RED);
		this.ci.setOpacity(0.5);
		this.ci.setCenterX(col * 50 + 25);
		this.ci.setCenterY(row * 50 + 25);
		this.ci.setVisible(false);
		this.re = new Rectangle(32, 32, Color.CYAN);
		this.re.setOpacity(0.5);
		this.re.setX(col * 50 + 9);
		this.re.setY(row * 50 + 9);
		this.re.setVisible(false);
		this.col = col;
		this.row = row;
		this.re.setOnMouseClicked(e -> {
			View.putCfAtGlow(this);
		});
		this.ci.setOnMouseClicked(e -> {
			View.putCfAtGlow(this);
		});
		this.glowPane = glowPane;
		this.glowPane.getChildren().addAll(this.re, this.ci);
	}

	public int determineShow(boolean thisTeam, int col, int row, FieldRectangle[][] rectArray) {
		if (!(col < 0 || col > 7 || row < 0 || row > 7)) {
			if (rectArray[col][row].hasCf()) {
				if (thisTeam != rectArray[col][row].getCf().getTeam()) {
					showHit();
					return 1;
				} else {
					return -1;
				}
			} else {
				showMove();
				return 0;
			}
		} else {
			return 2;
		}
	}

	public void showHit() {
		this.ci.setVisible(true);
		this.ci.toFront();
	}

	public void showMove() {
		this.re.setVisible(true);
		this.re.toFront();
	}

	public void hide() {
		this.ci.setVisible(false);
		this.re.setVisible(false);
	}

	public Rectangle getRe() {
		return re;
	}

	public void setRe(Rectangle re) {
		this.re = re;
	}

	public double getX() {
		return re.getX();
	}

	public double getY() {
		return re.getY();
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public Pane getGlowPane() {
		return this.glowPane;
	}

	public boolean getCorrespondingFieldsTeam() {
		// if
		// (!ChessFieldController.getRectArray()[this.col][this.row].getCf().equals(null))
		// return
		// ChessFieldController.getRectArray()[this.col][this.row].getCf().getTeam();
		// else
		// System.out.println("Something went wrong in the Correspondence");
		return false;
	}

}
