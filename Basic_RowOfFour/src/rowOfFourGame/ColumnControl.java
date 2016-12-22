package rowOfFourGame;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ColumnControl {

	private Group controlGroup;
	private ColumnModel model;
	private Rectangle idleSpawner;
	private int someonewon = -1;
	private long lastTime = System.currentTimeMillis();

	public ColumnControl(int width, int height, int streak, int players, boolean[] mode) {
		controlGroup = new Group();
		this.model = new ColumnModel(width, height, streak, players, mode);
		model.getRoot().getChildren().add(controlGroup);
		generateButtons(width, height, model.getColWidth(), model.getRowHeight(), model.getMargin());
	}

	public void generateButtons(int width, int height, double colWidth, double rowHeight, double margin) {
		for (int x = 0; x < width; x++) {
			int col = x;
			Rectangle icon = new Rectangle(x * (colWidth + margin), 0, colWidth, (rowHeight * 2) + rowHeight * height);
			icon.setFill(Color.TRANSPARENT);
			icon.setOnMouseClicked(e -> {
				int winner = model.check(col);
				if (winner != -1) {
					//win(winner, model.getWidthSet(), model.getHeightSet());
				}
				model.calcHover(col, true);
			});
			icon.setOnMouseEntered(e -> {
				model.calcHover(col, true);
			});
			icon.setOnMouseExited(e -> {
				model.calcHover(col, false);
			});

			controlGroup.getChildren().add(icon);
		}
		
		idleSpawner = new Rectangle(model.getWidthSet(), model.getHeightSet());
		idleSpawner.setFill(Color.TRANSPARENT);
		idleSpawner.setMouseTransparent(true);
		
		idleSpawner.setOnMouseMoved(e->{
			if(someonewon != -1 && System.currentTimeMillis()-lastTime>200){
				double xPos = Math.random() * model.getWidthSet();
				Circle idleC = new Circle(xPos, 0, model.getRadius(), Color.GREY);
				model.getCircleGroup().getChildren().add(idleC);
				model.check((int) (Math.random()*width));
				fallDepths.add(heightSet - radius);
				pause = false;
				lastTime = System.currentTimeMillis();
			}
		});
	}

	public void disableButtons() {
		for (Node r : controlGroup.getChildren()) {
			if (r.getClass().equals(Rectangle.class)) {
				r.setDisable(true);
			}
		}
	}

	public void enableButtons() {
		for (Node r : controlGroup.getChildren()) {
			if (r.getClass().equals(Rectangle.class)) {
				r.setDisable(false);
			}
		}
	}

	// public void win(int team, double widthSet, double heightSet) {
	// AnchorPane root = null;
	// FXMLLoader load = null;
	// try {
	// load = new
	// FXMLLoader(getClass().getResource("/application/WinIcon.fxml"));
	// root = load.load();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// WinControl wc = load.getController();
	// root.setTranslateX(widthSet / 2 - wc.getCircle().getRadius());
	// wc.createWin(team, model.getCircleGroup());
	// disableButtons();
	// controlGroup.getChildren().add(root);
	// root.setOnMouseClicked(e -> {
	// controlGroup.getChildren().remove(this);
	// enableButtons();
	// });
	// root.toFront();
	//
	// }

	public void win(int team, double widthSet, double heightSet) {
		Rectangle winRectangle = new Rectangle(widthSet, 20);
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
		Button closeButton = new Button("close");
		Button resetButton = new Button("reset");
		
		
		AnchorPane winRoot = new AnchorPane(closeButton, resetButton);
		
		// make a scene!
		Scene columnScene = new Scene(winRoot);
		Stage columnStage = new Stage();
		columnStage.setScene(columnScene);
		columnStage.show();
	}

	public Group getControlGroup() {
		return controlGroup;
	}

}
