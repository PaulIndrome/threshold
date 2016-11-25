package application;

import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Bishop extends ChessFigure {

	private boolean team;
	private int type;
	private int col;
	private int row;
	private ImageView icon;
	private Pane glowPane;
	private Pane fieldPane;
	private FieldRectangle[][] rectArray;
	private GlowField[][] glowArray;

	public Bishop(boolean team, int col, int row, FieldRectangle[][] rectArray, GlowField[][] glowArray, Pane fieldPane,
			Pane glowPane) throws IllegalArgumentException {
		// if (col >= 8 || row > 6 || col < 0 || row < 1 || row == 2 || row == 3
		// || row == 4 || row == 5) {
		// throw new IllegalArgumentException("Default position of Pawn is
		// invalid.");
		// }
		this.icon = new ImageView("" + team + "bishop.png");
		this.type = 3;
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
			} else
				ChessFieldController.switchAnnounce(true);
		});
		this.icon.hoverProperty().addListener(e->{
			if((ChessFieldController.getActiveTeam() == this.team && this.icon.isHover()))
				this.icon.setEffect(new ImageInput(new Image(""+team+"bishopsel.png"),icon.getX(),icon.getY()));
			else 
				this.icon.setEffect(null);
		});
		firstSet(col, row);
	}

	public void canHit() {
	}

	public void firstSet(int col, int row) {
		rectArray[col][row].setCf(this);
		icon.setX(rectArray[col][row].getX() + 9);
		icon.setY(rectArray[col][row].getY() + 9);
		fieldPane.getChildren().add(icon);
	}

	public void canMove() {
		int count = 1;
		/*
		 * TODO Define escape from while-loops by hitting a ChessFigure or going
		 * off the board's boundaries. There seems to be no alternative to - in
		 * the end - run along each of the eight possible directions with a
		 * dedicated while-loop. What a hassle for the poor runtime. Hint: Reset
		 * count after each loop has finished.
		 */
		
		while(col+count <= 7 && row+count <= 7){
			int breaker = glowArray[col+count][row+count].determineShow(team, col+count, row+count, rectArray);
			if(breaker != 0){
				break;
			}
			count++;
		}
		count=1;
		while(col+count <= 7 && row-count >= 0){
			int breaker = glowArray[col+count][row-count].determineShow(team, col+count, row-count, rectArray);
			if(breaker != 0){
				break;
			}
			count++;
		}
		count=1;
		while(col-count >=0 && row+count <= 7){
			int breaker = glowArray[col-count][row+count].determineShow(team, col-count, row+count, rectArray);
			if(breaker != 0){
				break;
			}
			count++;
		}
		count=1;
		while(col-count >= 0 && row-count >= 0){
			int breaker = glowArray[col-count][row-count].determineShow(team, col-count, row-count, rectArray);
			if(breaker != 0){
				break;
			}
			count++;
		}
	}

	public void setCf(int col, int row) {
		/*
		 * This method is used both for moving and hitting.
		 */
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
