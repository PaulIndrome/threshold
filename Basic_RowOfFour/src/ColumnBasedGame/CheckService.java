package ColumnBasedGame;

public class CheckService {

	private static int streak = ColumnGame.getStreak();
	private static int[][] teams = new int[ColumnGame.getWidth()][ColumnGame.getHeight() + 1];
	private static int currentCol;
	private static int currentRow;
	private static int currentChecker;
	private static Delta xm = new Delta(-1, 0);
	private static Delta xmyp = new Delta(-1, -1);
	private static Delta xmym = new Delta(-1, 1);
	private static Delta xp = new Delta(1, 0);
	private static Delta xpyp = new Delta(1, -1);
	private static Delta xpym = new Delta(1, 1);
	private static Delta ym = new Delta(0, 1);
	private static Delta[] deltas = new Delta[] { xm, xmyp, xmym, xp, xpyp, xpym, ym };

	public CheckService() {
		for (int x = 0; x < ColumnGame.getWidth(); x++) {
			for (int y = 0; y < ColumnGame.getHeight() + 1; y++) {
				teams[x][y] = 0;
			}
		}
	}

	public static void check() {
		for (Delta d : deltas) {
			try {
				for (int c = 1; c < streak; c++) {
					if (teams[currentCol + (d.colD() * c)][currentRow + (d.rowD() * c)] == currentChecker) {
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
	}

	public static void put(int col, int row) {
		CheckService.currentCol = col;
		CheckService.currentRow = row;
		CheckService.currentChecker = (ColumnGame.getTeam() == true) ? 1 : -1;
		teams[col][row] = (ColumnGame.getTeam() == true) ? 1 : -1;
		check();
	}

}
