package application;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StaticGame {

	
	private AnchorPane root = new AnchorPane();
	private static GameRectangle[][] rectArray;
	

	public StaticGame(int width, int height, Stage secondary) {

		rectArray = new GameRectangle[width][height];

		int columnWidth = 800 / width;
		int rowHeight = 600 / height;
		int widthSet = (columnWidth + 3) * width;
		int heightSet = (rowHeight + 1) * height;
		root.setPrefSize(widthSet, heightSet + 100);
		root.setMaxSize(widthSet, heightSet + 100);
		root.setMinSize(widthSet, heightSet + 100);

		double xCoord = 0;
		double yCoord = 100;

		for (int a = 0; a < width; a++) {
			for (int b = 0; b < height; b++) {
				new PutButton(columnWidth, rowHeight, xCoord, 0, a, root, rectArray);
				if (a % 2 != 0) {
					rectArray[a][b] = new GameRectangle(columnWidth, rowHeight, xCoord, yCoord, a, root, Color.RED);
				} else {
					rectArray[a][b] = new GameRectangle(columnWidth, rowHeight, xCoord, yCoord, a, root, Color.BLUE);
				}
				yCoord += (rowHeight + 1);
			}
			xCoord += (columnWidth + 3);
			yCoord = 100;
		}

		GameStartController.setRectArray(rectArray);
		
		Scene scene = new Scene(root);
		secondary.setScene(scene);
		secondary.show();
	}
	
	

}
