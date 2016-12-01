package rowOfFourGame;

import java.util.ArrayList;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ColumnView {

	
	private Service<Void> fall;
	private ArrayList<Circle> circles = new ArrayList<Circle>();
	private ArrayList<Double> fallDepths = new ArrayList<Double>();
	private boolean isCancelled;
	private boolean pause;
	
	public ColumnView(){
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

	public void removeCircle(int i) {
		circles.remove(i);
		fallDepths.remove(i);
	}

	public void pause() {
		pause = !pause;
	}

	public void addCircle(Color color, double radius, double xPos, double fallDepth, AnchorPane parent) {
		Circle circle = new Circle(xPos, 0, radius, color);
		parent.getChildren().add(circle);
		circles.add(circle);
		fallDepths.add(fallDepth);
		pause = false;
	}
	
	
}