package ColumnBasedGame;

import java.util.ArrayList;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.shape.Circle;

public class FallService {

	private static ArrayList<Circle> circles = new ArrayList<Circle>();
	private static ArrayList<Double> fallDepths = new ArrayList<Double>();
	private static double rowHeight = ColumnGame.getRowHeight();
	private static Service<Void> fall;
	private static boolean pause;
	private static boolean isCancelled = false;

	public FallService() {
		pause = false;
		fall = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					protected Void call() throws Exception {
						Circle c;
						while (!isCancelled) {
							if (!pause) {
								for (int i = 0; i < circles.size(); i++) {
									c = circles.get(i);
									if (c.getCenterY()+c.getRadius()+1 < fallDepths.get(i)) {
										c.setCenterY(c.getCenterY() + c.getRadius()+1);
									} else {
										removeCircle(i);
										pause = (circles.isEmpty());
									}
								}
							}
							Thread.sleep(16);
						}
						return null;
					}
				};
			}
		};
		fall.start();
	}

	public static void addCircle(Circle c, int row) {
		circles.add(c);
		fallDepths.add((2*rowHeight) + row*rowHeight);
		pause = false;
	}

	public static void removeCircle(int i) {
		circles.remove(i);
		fallDepths.remove(i);
	}

	public static void pause() {
		pause = !pause;
	}

}
