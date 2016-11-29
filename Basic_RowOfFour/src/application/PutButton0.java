package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PutButton0 {

	private Rectangle icon;
	private GameRectangle[][] rectArray;
	private int column;

	public PutButton0(int columnWidth, int rowHeight, double x, double y, int column, AnchorPane parent,
			GameRectangle[][] rectArray) {
		this.column = column;
		this.rectArray = rectArray;
		icon = new Rectangle(x, y, columnWidth, rowHeight);
		icon.setFill(Color.BLACK);
		icon.setOnMouseClicked(e -> {
			put(GameStartController.getCurrentTeam());
		});
		parent.getChildren().add(icon);
	}

	public void put(boolean team) {
		if (rectArray[column][0].getTeam() == 0) {
			GameStartController.switchTeam();
			new Thread(new Runnable() {
				@Override
				public void run() {
					int heightStep = 0;

					while (heightStep < GameStartController.getHeight()
							&& rectArray[column][heightStep].getTeam() == 0) {
						if (team)
							rectArray[column][heightStep].showTrue();
						else
							rectArray[column][heightStep].showFalse();

						if (heightStep > 0)
							rectArray[column][heightStep - 1].erase();

						try {
							Thread.sleep(33);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						heightStep++;
					}
					/*if (team)
						FieldCheck.check(column, heightStep - 1, 1);
					else
						FieldCheck.check(column, heightStep - 1, -1);*/
				}
			}).start();
		}

	}

}
