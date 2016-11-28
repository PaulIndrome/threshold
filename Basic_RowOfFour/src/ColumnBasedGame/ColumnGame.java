package ColumnBasedGame;

import application.GameStartController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColumnGame {

	private AnchorPane root = new AnchorPane();
	private static GameColumn[] columns;
	private static boolean team = true;
	private static double heightSet;
	private static int width;
	private static int height;
	private static int streak;
	private static double columnWidth;
	private static double rowHeight;

	public ColumnGame(int width, int height, Stage secondary) {
		streak = GameStartController.getStreak();

		columns = new GameColumn[width];
		ColumnGame.height = height;
		ColumnGame.width = width;
		ColumnGame.columnWidth = 1280 / width;
		ColumnGame.rowHeight = 800 / height;
		double margin = ((1280 % columnWidth) / width) + 4;
		double widthSet = (columnWidth * width) + ((width - 1) * margin);
		ColumnGame.heightSet = (rowHeight) * height;
		root.setPrefSize(widthSet, heightSet + 2 * rowHeight);
		root.setMaxSize(widthSet, heightSet + 2 * rowHeight);
		root.setMinSize(widthSet, heightSet + 2 * rowHeight);

		for (int a = 0; a < width; a++) {
			if (a % 2 != 0) {
				columns[a] = new GameColumn(columnWidth, rowHeight, a, height, margin, root, Color.RED);
			} else {
				columns[a] = new GameColumn(columnWidth, rowHeight, a, height, margin, root, Color.BLUE);
			}
		}

		new FallService();
		new CheckService();
		Scene scene = new Scene(root);
		secondary.setScene(scene);
		secondary.show();
	}

	public static GameColumn getColumn(int columnNo) {
		return columns[columnNo];
	}

	public static GameColumn[] getColumns(){
		return columns;
	}
	
	public static boolean getTeam() {
		return team;
	}

	public static void switchTeam() {
		team = !team;
	}
	
	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
	}
	
	public static double getColumnWidth(){
		return columnWidth;
	}
	
	public static double getRowHeight(){
		return rowHeight;
	}
	
	public static int getStreak(){
		return streak;
	}

	public void checkPiece(int columNo, int fallDepth) {
		boolean checker = columns[columNo].getTeam(fallDepth);
		//System.out.println("team: " + checker);
		boolean xm = true, xmyp = true, xmym = true, xp = true, xpym = true, xpyp = true, ym = true;
		int c = 1;
		//System.out.println(columNo - (streak - 1));
		xm = (columNo - (streak - 1) < 0) ? false : true;
		xmyp = xm;
		xmym = xm;
		if (xm) {
			xm = (columns[columNo - 1].hasPieceAt(fallDepth)) ? true : false;
			xmyp = xm;
		}
		/*xp = (columNo + (streak - 1) > columns.length) ? false : true;
		xpym = xp;
		xpyp = xp;
		if (xp) {
			xp = (columns[columNo + 1].hasPieceAt(fallDepth)) ? true : false;
			xpyp = xp;
		}
		ym = (fallDepth + (streak - 1) > height) ? false : true;
		xpym = ym;
		xmym = ym;*/
		//while ((xm || xmyp || xmym || xp || xpym || xpyp || ym) && c < streak) {
		while ((xm) && c < streak) {
			try {
			if (xm)
				xm = (checker == columns[columNo - c].getTeam(fallDepth)) ? true : false;
			
			} catch (NullPointerException e) {
				xm = false;
				//System.out.println("Checked a place without a GamePiece.");
				break;
			}
			c++;
		}

		//if (xm || xmyp || xmym || xp || xpym || xpyp || ym) {
		if (xm){
			System.out.println("xm has produced a winner.");
			//System.out.println("We have a winner!" + xm + xmyp + xmym + xp + xpym + xpyp + ym);
		} else {
			System.out.println("No winner!");
		}

	}

}


//System.out.println("xm checked " + xm);
			/*if (xmyp)
				xmyp = (checker == columns[columNo - c].getTeam(fallDepth - c)) ? true : false;
			System.out.println("xmyp checked " + xmyp);
			if (xmym)
				xmym = (checker == columns[columNo - c].getTeam(fallDepth + c)) ? true : false;
			// System.out.println("xmym checked " + xmym);
			if (xp)
				xp = (checker == columns[columNo + c].getTeam(fallDepth)) ? true : false;
			// System.out.println("xp checked " + xp);
			if (xpyp)
				xpyp = (checker == columns[columNo + c].getTeam(fallDepth - c)) ? true : false;
			// System.out.println("xpyp checked " + xpyp);
			if (xpym)
				xpym = (checker == columns[columNo + c].getTeam(fallDepth + c)) ? true : false;
			// System.out.println("xpym checked " + xpym);
			if (ym)
				ym = (checker == columns[columNo].getTeam(fallDepth + c)) ? true : false;
			// System.out.println("ym checked " + ym);*/
