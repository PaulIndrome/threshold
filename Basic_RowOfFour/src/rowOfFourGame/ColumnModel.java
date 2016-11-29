package rowOfFourGame;

import ColumnBasedGame.Delta;
import ColumnBasedGame.FallService;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ColumnModel {

	private AnchorPane root = new AnchorPane();

	ColumnControl control;
	ColumnView view;

	private int currentTeam;
	private int[][] teams;
	private Delta[] deltas;
	private int[] fallheights;
	private boolean[] mode;
	private Color[] teamColors = new Color[] { Color.GOLD, Color.BLUEVIOLET, Color.CYAN, Color.FORESTGREEN };

	private int width;
	private int height;
	private int streak;
	private int players;

	private double margin;
	private double colWidth;
	private double rowHeight;

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
		this.teams = new int[width][height + 1];
		this.fallheights = new int[width];
		this.currentTeam = 1;
		this.mode = mode;

		// MATHS!! to discern width and height of single rectangle
		this.colWidth = 1280 / width;
		this.rowHeight = 800 / height;

		// set attributes of game window
		margin = ((1280 % colWidth) / width) + 4;
		double widthSet = (colWidth * width) + ((width - 1) * margin);
		double heightSet = (rowHeight) * height;
		root.setPrefSize(widthSet, heightSet + 2 * rowHeight);
		root.setMaxSize(widthSet, heightSet + 2 * rowHeight);
		root.setMinSize(widthSet, heightSet + 2 * rowHeight);

		initialize();

	}

	public void initialize() {
		System.out.println(players);
		// initialize fallheights
		for (int f = 0; f < fallheights.length; f++) {
			fallheights[f] = height;
		}

		// initialize control and view
		control = new ColumnControl(root, this);
		view = new ColumnView();

		// populate the window with rectangles and "buttons"
		control.generateButtons(width, height, colWidth, rowHeight, margin);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Rectangle r = new Rectangle(x * (colWidth + margin), 2 * rowHeight + (y * rowHeight), colWidth,
						rowHeight);
				r.setFill(Color.TRANSPARENT);
				if (x % 2 == 0) {
					r.setStroke(Color.DARKGREY);
				} else {
					r.setStroke(Color.GREY);
				}
				r.setMouseTransparent(true);
				root.getChildren().add(r);
			}
		}

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
	public void check(int col) {
		// first check if a piece can be placed in the column
		if (fallheights[col] > 0) {
			// establish which row the piece will land in
			int row = fallheights[col];

			// update the fallheight at given column
			teams[col][row] = currentTeam;
			fallheights[col]--;

			// generate and provide data necessary to update view with new piece
			double radius = (colWidth < rowHeight) ? colWidth / 2 - 1 : rowHeight / 2 - 1;
			Color color = teamColors[currentTeam - 1];
			view.addCircle(color, radius, col * (colWidth + margin) + (colWidth / 2), (2 * rowHeight) + row * rowHeight,
					root);

			// check each direction (delta) in team array
			for (Delta d : deltas) {
				try {
					for (int c = 1; c < streak; c++) {
						if (teams[col + (d.colD() * c)][row + (d.rowD() * c)] == currentTeam) {
							d.match();
							continue;
						} else {
							break;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
			if (xm.matches() + xp.matches() >= streak - 1 || xmyp.matches() + xpym.matches() >= streak - 1
					|| xmym.matches() + xpyp.matches() >= streak - 1 || ym.matches() >= streak - 1) {
				System.out.println("We have a winner!");
				FallService.pause();
			} else {
			}
			for (Delta d : deltas) {
				d.clear();
			}
			switchTeam();
		}
	}

	public void switchTeam() {
		if (!mode[0]) {
			if (currentTeam == players) {
				currentTeam = 1;
			} else {
				currentTeam += 1;
			}
		} else {
			currentTeam = (int) (Math.random() * players + 1);
			while (currentTeam > 4) {
				currentTeam = (int) (Math.random() * players + 1);
				System.out.println("We even here?");
			}
		}
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

}
