package rowOfFourGame;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class ColumnView {

	private Group circleGroup = new Group();
	private Group columnGroup = new Group();
	private Service<Void> fall;
	private ArrayList<Circle> circles = new ArrayList<Circle>();
	private ArrayList<Double> fallDepths = new ArrayList<Double>();
	private boolean isCancelled;
	private boolean pause;
	
	private boolean idleHover = false;
	
	private Rectangle idleSpawner = new Rectangle();
	
	private int someonewon = -1;
	// circleIncrement should be radius plus radius/4
	private double circleIncrement;
	private Circle hoverCircle;
	private Rectangle winRectangle;

	private double radius;
	private double widthSet;
	private double heightSet;

	private long lastTime = System.currentTimeMillis();

	public ColumnView() {
		circleIncrement = -1;
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
									if (c.getCenterY() + circleIncrement > fallDepths.get(i)) {
										c.setCenterY(fallDepths.get(i) - c.getRadius());
										removeCircle(i);
										pause = (circles.isEmpty());
									} else {
										c.setCenterY(c.getCenterY() + circleIncrement);
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
	}

	public void generateHover() {
		hoverCircle = new Circle(0, 0, radius, Color.TRANSPARENT);
		hoverCircle.setStrokeType(StrokeType.INSIDE);
		hoverCircle.setStrokeWidth(2);
		hoverCircle.setMouseTransparent(true);
		circleGroup.getChildren().add(hoverCircle);
	}

	public void addCircle(Color color, double xPos, double fallDepth) {
		if(color.equals(Color.GREY)){
			System.out.println("idleCircle added");
		}
		if (circleIncrement == -1)
			circleIncrement = radius * 1.25;
		Circle circle = new Circle(xPos, 0, radius, color);
		circle.setMouseTransparent(true);
		circleGroup.getChildren().add(circle);
		circles.add(circle);
		fallDepths.add(fallDepth);
		pause = false;
		System.out.println("Circle of color " + color + " added");
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
			}
		}
		winRectangle = new Rectangle(widthSet, 20);
		winRectangle.setFill(Color.WHITE);
		columnGroup.getChildren().add(winRectangle);
	}

	public void untransparentColumns() {
		columnGroup.setMouseTransparent(false);
	}

	public void transparentColumns() {
		columnGroup.setMouseTransparent(true);
	}

	public void hoverCircle(Color color, double xPos, double fallDepth) {
		hoverCircle.setCenterX(xPos);
		hoverCircle.setCenterY(fallDepth - hoverCircle.getRadius());
		hoverCircle.setStroke(color);
	}

	public void clearHover() {
		hoverCircle.setStroke(Color.TRANSPARENT);
	}

	public Group getCircleGroup() {
		return circleGroup;
	}

	public void colorWinRectangle(int team) {
		someonewon = team;
		switch (team) {
		case 1:
			winRectangle.setFill(Color.RED);
			break;
		case 2:
			winRectangle.setFill(Color.BLUE);
			break;
		case 3:
			winRectangle.setFill(Color.CYAN);
			break;
		case 4:
			winRectangle.setFill(Color.GREEN);
			break;
		default:
			break;
		}
		pause = false;
		
		idleSpawner.setMouseTransparent(false);
	}


	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setWidthSet(double widthSet) {
		this.widthSet = widthSet;
	}

	public void setHeightSet(double heightSet) {
		this.heightSet = heightSet;
	}

}
