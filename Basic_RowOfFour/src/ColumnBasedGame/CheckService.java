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
	private static Service<Void> check;
	private static boolean pause;
	private static boolean found = false;
	private static ArrayList<Integer> cols = new ArrayList<Integer>();
	private static ArrayList<Integer> rows = new ArrayList<Integer>();
	private boolean xm = true, xmyp = true, xmym = true, xp = true, xpym = true, xpyp = true, ym = true;
	

	public CheckService() {
		pause = false;
		check = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					protected Void call() throws Exception {
						while (!found) {
							int c = 1;
							if (!pause && !cols.isEmpty() && !rows.isEmpty() && !(xm || xmyp || xmym || xp || xpym || xpyp || ym)) {
								checker = columns[cols.get(index)]
								xm = (checker == columns[columNo - c].getTeam(fallDepth)) ? true : false;
							}
							Thread.sleep(16);
						}
						return null;
					}
				};
			}
		};
		check.start();
	}

	public static void check(int col, int row){
		cols.add(col);
		rows.add(row);
	}

}
