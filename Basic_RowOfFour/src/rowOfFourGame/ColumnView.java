package rowOfFourGame;

import java.io.IOException;
import java.util.ArrayList;

import application.WinControl;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ColumnView {

	private Group circleGroup = new Group();
	private Group columnGroup = new Group();
	private Service<Void> fall;
	private ArrayList<Circle> circles = new ArrayList<Circle>();
	private ArrayList<Double> fallDepths = new ArrayList<Double>();
	private boolean isCancelled;
	private boolean pause;

	public ColumnView() {
		isCancelled = false;
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
									if (c.getCenterY() + c.getRadius() + 1 < fallDepths.get(i)) {
										c.setCenterY(c.getCenterY() + c.getRadius() + 1);
										// setCenterX and Thread sleep of 100ms
										// makes for retro look
										// c.setCenterX(-1 * c.getCenterX()+10);
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

	public void removeCircle(int i) {
		circles.remove(i);
		fallDepths.remove(i);
	}

	public void pause() {
		pause = !pause;
	}

	public void addGroups(AnchorPane parent) {
		parent.getChildren().addAll(circleGroup, columnGroup);
		circleGroup.toFront();
		columnGroup.toBack();
	}

	public void addCircle(Color color, double radius, double xPos, double fallDepth, AnchorPane parent) {
		Circle circle = new Circle(xPos, 0, radius, color);
		circle.setMouseTransparent(true);
		circleGroup.getChildren().add(circle);
		circles.add(circle);
		fallDepths.add(fallDepth);
		pause = false;
	}

	public void generateColumns(int width, int height, double colWidth, double rowHeight, double margin) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Rectangle r = new Rectangle(x * (colWidth + margin), 20 + (y * rowHeight), colWidth, rowHeight);
				r.setFill(Color.TRANSPARENT);
				if (x % 2 == 0) {
					r.setStroke(Color.DARKGREY);
				} else {
					r.setStroke(Color.GREY);
				}
				r.setMouseTransparent(true);
				columnGroup.getChildren().add(r);
				r.toBack();
			}
		}
	}

	public void win(int team, double widthSet, double heightSet) {
		AnchorPane root = null;
		FXMLLoader load = null;
		try {
			load = new FXMLLoader(getClass().getResource("/application/WinIcon.fxml"));
			root = load.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		WinControl wc = load.getController();
		wc.createWin(team, circleGroup);
		circleGroup.getChildren().add(root);
		root.toFront();
	}

}
