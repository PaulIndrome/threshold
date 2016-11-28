package ColumnBasedGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.shape.Circle;

// DOES THIS SHOW UP?
public class CheckService {

	private static GameColumn[] columns = ColumnGame.getColumns();
	private static int streak = ColumnGame.getStreak();
	private static Task<Void> check;
	private static boolean pause;
	private static boolean found = false;
	private static ArrayList<Integer> cols = new ArrayList<Integer>();
	private static ArrayList<Integer> rows = new ArrayList<Integer>();
	private boolean[] directions = new boolean[] { false, false, false, false, false, false, false };
	// xm = true, xmyp = true, xmym = true, xp = true, xpym = true, xpyp = true,
	// ym = true;
	private static int width = ColumnGame.getWidth();
	private static int height = ColumnGame.getHeight();

	private static int[][] teams = new int[ColumnGame.getWidth()][ColumnGame.getHeight() + 1];
	private static int currentCol;
	private static int currentRow;
	private static int currentChecker;
	private static Delta[] deltas = new Delta[] { new Delta(-1, 0), new Delta(-1, -1), new Delta(-1, 1),
			new Delta(1, 0), new Delta(1, -1), new Delta(1, 1), new Delta(0, 1) };
	private static Delta xm = new Delta(-1, 0);
	private static Delta xmyp = new Delta(-1, -1);
	private static Delta xmym = new Delta(-1, 1);
	private static Delta xp = new Delta(1, 0);
	private static Delta xpyp = new Delta(1, -1);
	private static Delta xpym = new Delta(1, 1);
	private static Delta ym = new Delta(0, 1);
	private static boolean[] streaksteps;

	public CheckService() {
		for (int x = 0; x < ColumnGame.getWidth(); x++) {
			for (int y = 0; y < ColumnGame.getHeight() + 1; y++) {
				teams[x][y] = 0;
			}
		}
		pause = false;
		// check = new Task<Void>() {
		// protected Void call() throws Exception {
		// try {
		// for (Delta d : deltas) {
		// streaksteps = new boolean[streak];
		// streaksteps[0] = true;
		// for (int c = 1; c < streaksteps.length; c++) {
		// if (teams[currentCol + (d.colD() * c)][currentRow
		// + (d.rowD() * c)] == currentChecker) {
		// streaksteps[c] = true;
		// System.out.println("Found one!");
		// continue;
		// } else {
		// break;
		// }
		// }
		// System.out.println(d.toString());
		// }
		// } catch (IndexOutOfBoundsException e) {
		//
		// }
		// return null;
		// }
		// };
	}

	public static void check() {
		for (Delta d : deltas) {
			streaksteps = new boolean[streak];
			streaksteps[0] = true;
			try {
				for (int c = 1; c < streaksteps.length; c++) {
					if (teams[currentCol + (d.colD() * c)][currentRow + (d.rowD() * c)] == currentChecker) {
						streaksteps[c] = true;
						//System.out.println("Found one!");
						if(c==streaksteps.length-1 && streaksteps[c] == true){
							System.out.println("We have a winner!");
							FallService.pause();
						}
						continue;
					} else {
						streaksteps[c] = false;
						break;
					}
				}
			} catch (IndexOutOfBoundsException e) {
				//System.out.println("Index out of bounds\n");
			}
			/*String steps = "" + d.colD() + "," + d.rowD() + " : ";
			for (int s = 0; s < streaksteps.length; s++)
				steps += streaksteps[s] + " ";
			System.out.println(steps);*/

		}

	}

	public static void put(int col, int row) {
		CheckService.currentCol = col;
		CheckService.currentRow = row;
		CheckService.currentChecker = (ColumnGame.getTeam() == true) ? 1 : -1;
		//System.out.println(teams.length + " " + col + " " + row);
		teams[col][row] = (ColumnGame.getTeam() == true) ? 1 : -1;
		check();
	}

}
