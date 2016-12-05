package rowOfFourGame;

import java.util.ArrayList;

import application.WinControl;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
		circleGroup.toFront();
		columnGroup.toBack();
		parent.getChildren().addAll(circleGroup, columnGroup);
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
				r.toBack();
				columnGroup.getChildren().add(r);
			}
		}
	}

	public void win(int team, double widthSet, double heightSet) {
		WinControl wc = null;
		if(wc == null){
			wc = new WinControl();
			pause = true;
		}
		
		/*
		columnGroup.setVisible(false);
		AnchorPane anchor = new AnchorPane();
		anchor.setPrefSize(200, 279);
		anchor.setTranslateX(100);
		anchor.setTranslateY(100);
		anchor.toFront();
		
		Circle winCircle = new Circle(anchor.getLayoutX()+(anchor.getPrefWidth()/2), anchor.getLayoutY()+50, 100, Color.WHITE);
		switch (team) {
		case 1:
			winCircle.setFill(Color.GOLD);
			break;
		case 2:
			winCircle.setFill(Color.BLUEVIOLET);
			break;
		case 3:
			winCircle.setFill(Color.CYAN);
			break;
		case 4:
			winCircle.setFill(Color.GREEN);
			break;
		default:
			break;
		}
		winCircle.toFront();
		
		Text winner = new Text(anchor.getLayoutX()+2, anchor.getLayoutY()+75, "WINNER");
		winner.setFont(new Font("System Bold", 48));
		winner.toFront();
		
		Button reset = new Button("reset");
		reset.setLayoutX(anchor.getLayoutX()+75);
		reset.setLayoutY(anchor.getLayoutY()+208);
		reset.setOnAction(e->{
			columnGroup.setVisible(true);
			circleGroup.getChildren().clear();
		});
		reset.toFront();
		
		Button close = new Button("close");
		close.setLayoutX(anchor.getLayoutX()+75);
		close.setLayoutY(anchor.getLayoutY()+240);
		close.setOnAction(e->{
			circleGroup.getScene().getWindow().hide();
		});
		close.toFront();
		
		anchor.getChildren().addAll(winCircle, winner, reset, close);
		anchor.toFront();
		circleGroup.getChildren().add(anchor);
		*/
		
		
		
	}

}
