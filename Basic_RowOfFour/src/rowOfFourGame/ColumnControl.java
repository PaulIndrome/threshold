package rowOfFourGame;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColumnControl {
	
	private Group controlGroup;
	private ColumnModel model;

	public ColumnControl(AnchorPane parent, ColumnModel model){
		controlGroup = new Group();
		this.model = model;
		parent.getChildren().add(controlGroup);
	}
	
	public Group getControlGroup(){
		return controlGroup;
	}
	
	public void generateButtons(int width, int height, double colWidth, double rowHeight, double margin){
		for (int x = 0; x < width; x++) {
			int col = x;
			Rectangle icon = new Rectangle(x*(colWidth+margin), 0, colWidth, (rowHeight*2)+rowHeight*height);
			icon.setFill(Color.TRANSPARENT);
			icon.setOnMouseClicked(e -> {
					model.check(col);
			});
			controlGroup.getChildren().add(icon);
		}
	}
	
}