package rowOfFourGame;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColumnModel {

	private AnchorPane root = new AnchorPane();

	ColumnView view;

	private int currentTeam;
	private int[][] teams;
	private Delta[] deltas;
	private int[] fallheights;
	private boolean[] mode;
	private Color[] teamColors = new Color[] { Color.RED, Color.BLUE, Color.CYAN, Color.FORESTGREEN };

	private int width;
	private int height;
	private int streak;
	private int players;

	private double margin;
	private double colWidth;
	private double rowHeight;
	private double widthSet;
	private double heightSet;
	private double radius;

	private Delta xm;
	private Delta xmyp;
	private Delta xmym;
	private Delta xp;
	private Delta xpyp;
	private Delta xpym;
	private Delta ym;

	public ColumnModel(int width, int height, int streak, int players, boolean[] mode) {
		// set basic parameters
		this.width = width;
		this.height = height;
		this.streak = streak;
		this.players = players;
		this.mode = mode;

		// MATHS!! to discern width and height of single rectangle
		if(960/width < 960/height){
			this.colWidth = 960/width;
			this.rowHeight = colWidth;
		} else {
			this.rowHeight = 960/height;
			this.colWidth = rowHeight;
		}

		// set attributes of game window
		margin = ((1280 % colWidth) / width) + 4;
		widthSet = (colWidth * width) + ((width - 1) * margin);
		heightSet = (rowHeight) * height;
		root.setPrefSize(widthSet, 20 + heightSet);
		root.setMaxSize(widthSet, 20 + heightSet);
		root.setMinSize(widthSet, 20 + heightSet);

		initialize();

	}

	public void initialize() {
		// basic parameters
		this.currentTeam = 1;
		this.teams = new int[width][height + 1];
		this.fallheights = new int[width];

		// initialize fallheights
		for (int f = 0; f < fallheights.length; f++) {
			fallheights[f] = height;
		}

		// initialize view
		view = new ColumnView();
		view.addGroups(root);
		radius = (colWidth < rowHeight) ? colWidth / 2 - 1 : rowHeight / 2 - 1;
		view.setWidthSet(widthSet);
		view.setHeightSet(heightSet);
		view.setRadius(radius);
		view.generateHover();

		// populate the window with rectangles and "buttons"
		view.generateColumns(width, height, colWidth, rowHeight, margin);

		// generate the deltas
		xm = new Delta(-1, 0);
		xmyp = new Delta(-1, -1);
		xmym = new Delta(-1, 1);
		xp = new Delta(1, 0);
		xpyp = new Delta(1, -1);
		xpym = new Delta(1, 1);
		ym = new Delta(0, 1);
		deltas = new Delta[] { xm, xmyp, xmym, xp, xpyp, xpym, ym };

		// make a scene!
		Scene columnScene = new Scene(root);
		Stage columnStage = new Stage();
		columnStage.setScene(columnScene);
		columnStage.show();
	}

	// the check() method effectively puts pieces in the game and handles all
	// changes in data following the addition
	public int check(int col) {
		int actualCol = col;
		// if mode randomColumn active, calculate random row in vicinity
		if (mode[1]) {
			double range = Math.random() * (width / 10);
			double negPos = (Math.random() >= 0.49) ? 1 : -1;
			actualCol = (int) (col + ((Math.random() * range) * negPos));
			while (actualCol < 0 || actualCol > width || !(fallheights[actualCol] > 0)) {
				actualCol = (int) (col + ((Math.random() * range) * negPos));
			}
		}
		// first check if a piece can be placed in the column
		if (fallheights[actualCol] > 0) {
			// establish which row the piece will land in
			int row = fallheights[actualCol];

			// update the fallheight at given column
			teams[actualCol][row] = currentTeam;
			fallheights[actualCol]--;

			// generate and provide data necessary to update view with new piece
			double fallHeight = 20 + row * rowHeight;
			Color color = teamColors[currentTeam - 1];
			view.addCircle(color, actualCol * (colWidth + margin) + (colWidth / 2), fallHeight);

			// check each direction (delta) in team array
			for (Delta d : deltas) {
				try {
					for (int c = 1; c < streak; c++) {
						if (teams[actualCol + (d.colD() * c)][row + (d.rowD() * c)] == currentTeam) {
							d.match();
							continue;
						} else {
							break;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}

			// check mirroring deltas if the sum of their matches concludes to a
			// win streak
			// checked for streak length - 1 as the checked piece belongs to
			// matched team
			if (xm.matches() + xp.matches() >= streak - 1 || xmyp.matches() + xpym.matches() >= streak - 1
					|| xmym.matches() + xpyp.matches() >= streak - 1 || ym.matches() >= streak - 1) {
				System.out.println("We have a winner!");
				int winningTeam = currentTeam;
				teams = new int[width][height + 1];
				currentTeam = 1;
				clearDeltas();
				for (int f = 0; f < fallheights.length; f++) {
					fallheights[f] = height;
				}
				view.colorWinRectangle(winningTeam);
				return winningTeam;
			} else {
				clearDeltas();
				switchTeam();
				return -1;
			}
		}
		return -1;
	}

	public void switchTeam() {
		// should work for any number of players
		if (!mode[0]) {
			if (currentTeam == players) {
				currentTeam = 1;
			} else {
				currentTeam += 1;
			}
		}
		// if randomTeam mode active calculate a random team
		else if (mode[0]) {
			currentTeam = (int) (Math.random() * players + 1);
			while (currentTeam > 4) {
				currentTeam = (int) (Math.random() * players + 1);
			}
		}
	}

	public AnchorPane getRoot() {
		return root;
	}

	public int getCurrentTeam() {
		return currentTeam;
	}

	public void setCurrentTeam(int currentTeam) {
		this.currentTeam = currentTeam;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getStreak() {
		return streak;
	}

	public double getMargin() {
		return margin;
	}

	public double getColWidth() {
		return colWidth;
	}

	public double getRowHeight() {
		return rowHeight;
	}

	public void clearDeltas() {
		for (Delta d : deltas) {
			d.clear();
		}
	}

	public double getWidthSet() {
		return widthSet;
	}

	public double getHeightSet() {
		return heightSet;
	}
	
	public Group getCircleGroup(){
		return view.getCircleGroup();
	}

	public void calcHover(int col, boolean b) {
		//calculate information necessary to color and position the hoverCircle
		if (fallheights[col] > 0) {
			if(b){
			int row = fallheights[col];
			double fallHeight = 20 + row * rowHeight;
			Color color = mode[0] ? Color.GREY : teamColors[currentTeam - 1];
			view.hoverCircle(color, col * (colWidth + margin) + (colWidth / 2), fallHeight);
			} else {
				view.clearHover();
			}
		}
	}

	public double getRadius() {
		return radius;
	}

}
