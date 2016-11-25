package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ChessFieldController {

	@FXML
	private Pane fieldPane;
	@FXML
	private Pane fieldViewPane;
	@FXML
	private Pane glowPane;

	private static ChessFigure current;
	private static boolean activeTeam = true;
	private static Label wrongTeamAnnounce = new Label("Other team's turn", new Rectangle(30, 30, Color.RED));

	private static FieldRectangle[][] rectArray = new FieldRectangle[8][8];
	private static GlowField[][] glowArray = new GlowField[8][8];

	public void initialize() {
		initRectArray();
		initGlow();
		//View.instantiateFields(rectArray, glowArray, fieldPane, glowPane);
		fieldViewPane.setMouseTransparent(true);
		wrongTeamAnnounce.setOpacity(0.5);
		wrongTeamAnnounce.setTextFill(Color.RED);
		wrongTeamAnnounce.setTranslateX(110);
		wrongTeamAnnounce.setTranslateY(160);
		wrongTeamAnnounce.setVisible(false);
		fieldViewPane.getChildren().add(wrongTeamAnnounce);
		for (int c = 0; c <= 7; c++) {
			new Pawn(true, c, 6, rectArray, glowArray, fieldPane, glowPane);
			new Pawn(false, c, 1, rectArray, glowArray, fieldPane, glowPane);
		}

		new Queen(false, 3, 0, rectArray, glowArray, fieldPane, glowPane);
		new Queen(true, 3, 7, rectArray, glowArray, fieldPane, glowPane);
		new Bishop(false, 2, 0, rectArray, glowArray, fieldPane, glowPane);
		new Bishop(false, 5, 0, rectArray, glowArray, fieldPane, glowPane);
		new Bishop(true, 2, 7, rectArray, glowArray, fieldPane, glowPane);
		new Bishop(true, 5, 7, rectArray, glowArray, fieldPane, glowPane);
		new Rook(true, 0, 7, rectArray, glowArray, fieldPane, glowPane);
		new Rook(true, 7, 7, rectArray, glowArray, fieldPane, glowPane);
		new Rook(false, 0, 0, rectArray, glowArray, fieldPane, glowPane);
		new Rook(false, 7, 0, rectArray, glowArray, fieldPane, glowPane);

	}

	private void initRectArray() {
		short Xcount = 0;
		short Ycount = 0;
		boolean alternate = true;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				FieldRectangle re;
				if (alternate) {
					re = new FieldRectangle(Xcount, Ycount, 50, 50, Color.DARKGRAY, fieldViewPane);
					alternate = false;
				} else {
					re = new FieldRectangle(Xcount, Ycount, 50, 50, Color.LIGHTGRAY, fieldViewPane);
					alternate = true;
				}
				if (r < 2)
					re.getRe().setStroke(Color.RED);
				if (r > 5)
					re.getRe().setStroke(Color.BLUE);
				rectArray[c][r] = re;
				Xcount += 50;
			}
			alternate = !alternate;
			Ycount += 50;
			Xcount = 0;
		}
	}

	public void initGlow() {
		/*
		 * The glowPane is a transparent second Pane on top of the fieldPane
		 * that is only clickable when the ImageView of a ChessFigure has been
		 * clicked. The canMove() and canHit() methods of a ChessFigure add
		 * visible Nodes to the glowPane that can be clicked to move the
		 * ChessFigure to an empty and accessible or enemy-owned and accessible
		 * Rectangle of the fieldPane.
		 */
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				glowArray[c][r] = new GlowField(c, r, glowPane);
			}
		}
		glowPane.toFront();
		fieldPane.toBack();
		fieldViewPane.toBack();
		glowPane.setOnMouseClicked(e -> {
			if (!(e.getTarget().getClass().equals(Rectangle.class))
					|| !(e.getTarget().getClass().equals(Circle.class))) {
				for (GlowField[] gfA : glowArray) {
					for (GlowField gf : gfA) {
						gf.hide();
					}
				}
			}
			glowPane.setMouseTransparent(true);
		});
		glowPane.setMouseTransparent(true);
	}

	public static boolean setCurrent(ChessFigure cf) {
		if (cf.getTeam() == activeTeam) {
			ChessFieldController.current = cf;
			return true;
		} else {
			return false;
		}
	}

	public static ChessFigure getCurrent() {
		return current;
	}

	public static FieldRectangle[][] getRectArray() {
		return rectArray;
	}

	public static void switchTeam() {
		activeTeam = !activeTeam;
	}

	public static boolean getActiveTeam() {
		return activeTeam;
	}

	public static void switchAnnounce(boolean to) {
		if (to)
			wrongTeamAnnounce.setVisible(true);
		else
			wrongTeamAnnounce.setVisible(false);
	}

}
