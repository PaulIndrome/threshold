package rowOfFourGame;

import java.io.IOException;

import application.WinControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColumnControl {

	private Group controlGroup;
	private ColumnModel model;

	public ColumnControl(int width, int height, int streak, int players, boolean[] mode) {
		controlGroup = new Group();
		this.model = new ColumnModel(width, height, streak, players, mode);
		model.getRoot().getChildren().add(controlGroup);
		generateButtons(width, height, model.getColWidth(), model.getRowHeight(), model.getMargin());
	}

	public Group getControlGroup() {
		return controlGroup;
	}

	public void generateButtons(int width, int height, double colWidth, double rowHeight, double margin) {
		for (int x = 0; x < width; x++) {
			int col = x;
			Rectangle icon = new Rectangle(x * (colWidth + margin), 0, colWidth, (rowHeight * 2) + rowHeight * height);
			icon.setFill(Color.TRANSPARENT);
			icon.setOnMouseClicked(e -> {
				int winner = model.check(col);
				if (winner != -1) {
					win(winner, model.getWidthSet(),model.getHeightSet());
				}
			});
			controlGroup.getChildren().add(icon);
		}
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
		root.setTranslateX(widthSet / 2 - wc.getCircle().getRadius());
		wc.createWin(team);
		disableButtons();
		controlGroup.getChildren().add(root);
		root.toFront();
	}

}
