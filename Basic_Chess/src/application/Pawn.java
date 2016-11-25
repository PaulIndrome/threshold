package application;

import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Pawn extends ChessFigure {

	private boolean team;
	private boolean movedBefore = false;
	private int type;
	private int col;
	private int row;
	private ImageView icon;
	private Pane glowPane;
	private Pane fieldPane;
	private FieldRectangle[][] rectArray;
	private GlowField[][] glowArray;

	public Pawn(boolean team, int col, int row, FieldRectangle[][] rectArray, GlowField[][] glowArray, Pane fieldPane,
			Pane glowPane) throws IllegalArgumentException {
		this.icon = new ImageView(""+team+"pawn.png");
		this.type = 0;
		this.rectArray = rectArray;
		this.glowArray = glowArray;
		this.fieldPane = fieldPane;
		this.glowPane = glowPane;
		this.col = col;
		this.row = row;
		this.team = team;
		this.icon.setOnMouseClicked(e -> {
			if (ChessFieldController.setCurrent(this) == true){
				ChessFieldController.switchAnnounce(false);
				this.glowPane.setMouseTransparent(false);
				canMove();
				canHit();
			} else
				ChessFieldController.switchAnnounce(true);
		});
		this.icon.hoverProperty().addListener(e->{
			if((ChessFieldController.getActiveTeam() == this.team && this.icon.isHover()))
				this.icon.setEffect(new ImageInput(new Image(""+team+"pawnsel.png"),icon.getX(),icon.getY()));
			else 
				this.icon.setEffect(null);
		});
		firstSet(col, row);
	}

	public void canHit() {
		if (this.team) {
			if (row - 1 >= 0) {
				if (col + 1 <= 7 && rectArray[col + 1][row - 1].hasCf()
						&& rectArray[col + 1][row - 1].getCf().getTeam() != this.team) {
					glowArray[col + 1][row - 1].showHit();
				}
				if (col - 1 >= 0 && rectArray[col - 1][row - 1].hasCf()
						&& rectArray[col - 1][row - 1].getCf().getTeam() != this.team) {
					glowArray[col - 1][row - 1].showHit();
				}
			} else {
				System.out.println("This is the end my friend. (CanHit)");
				return;
			}
		} else if (!this.team) {
			if (row + 1 <= 7) {
				if (col + 1 <= 7 && rectArray[col + 1][row + 1].hasCf()
						&& rectArray[col + 1][row + 1].getCf().getTeam() != this.team) {
					glowArray[col + 1][row + 1].showHit();
				}
				if (col - 1 >= 0 && rectArray[col - 1][row + 1].hasCf()
						&& rectArray[col - 1][row + 1].getCf().getTeam() != this.team) {
					glowArray[col - 1][row + 1].showHit();
				}
			} else {
				System.out.println("This is the end my friend. (CanHit)");
				return;
			}
		} 
	}

	public void firstSet(int col, int row) {
		rectArray[col][row].setCf(this);
		icon.setX(rectArray[col][row].getX() + 9);
		icon.setY(rectArray[col][row].getY() + 9);
		fieldPane.getChildren().add(icon);

	}

	public void canMove() {
		if (team) {
			if (row - 1 >= 0 && !rectArray[col][row-1].hasCf()) {
				glowArray[col][row - 1].showMove();
				if(!movedBefore)
					glowArray[col][row-2].showMove();
			}
		} else {
			if (row + 1 <= 7 && !rectArray[col][row+1].hasCf()) {
				glowArray[col][row + 1].showMove();
				if(!movedBefore)
					glowArray[col][row + 2].showMove();
			}
		}
	}

	public void setCf(int col, int row) {
		/*
		 * This method is used both for moving and hitting.
		 */
		if(!movedBefore)
			movedBefore = true;
		rectArray[this.col][this.row].setCf(null);
		rectArray[col][row].clearCf();
		updateColRow(col, row);
		icon.setX(rectArray[col][row].getX() + 9);
		icon.setY(rectArray[col][row].getY() + 9);
		rectArray[col][row].setCf(this);
	}
	
	public void clearIcon(){
		this.icon.setImage(null);
	}

	public boolean getTeam() {
		return team;
	}

	public int getType() {
		return this.type;
	}

	public void updateColRow(int col, int row) {
		this.col = col;
		this.row = row;
	}
	

	/*
	 * Mathematically, the Pawn moves the following way in an 8x8 field numbered
	 * from top left to bottom right: if team false: row - 2 (on first move) row
	 * - 1 (on following moves) if team true: row + 2 (on first move) row + 1
	 * (on following moves)
	 * 
	 * Mathematically, the Pawn can hit the following way in an 8x8 field
	 * numbered from top left to bottom right:
	 */

}
