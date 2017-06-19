package material;

import java.util.ArrayList;

import gameobjects.HexTile;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameBoard extends Pane {

	boolean[][] setup = new boolean[13][15];
	int[][] IDs = new int[13][15];

	// HexTile[] tiles = new HexTile[195];

	ArrayList<HexTile> tiles = new ArrayList<HexTile>();
	ArrayList<Integer> skipsArray = new ArrayList<Integer>();

	public GameBoard(double prefWidth, double prefHeight, int... skips) {
		super();
		super.setPrefHeight(prefHeight);
		super.setPrefWidth(prefWidth);

		for (int i : skips)
			skipsArray.add(i);

		drawHexagonals();
		copulateHexagonals();

	}

	public String allTilesToString() {
		String s = "";

		for (int row = 0; row < 15; row++) {
			s += "(" + IDs[0][row] + ") " + setup[0][row];
			for (int col = 1; col < 13; col++) {
				s += ",\t(" + IDs[col][row] + ") " + setup[col][row];
			}
			s += "\n";
		}

		return s;
	}

	public void drawHexagonals() {
		StartEndCleanArray seca = new StartEndCleanArray();
		int ID = 0;
		int step = 0;
		int radius = 28;
		int Xstart = 50;
		double Ystart = 15 - (4.5f * radius * 2);
		double X = 0;
		double Y = 0;
		for (int row = 0; row < 17; row++) {
			Y = (row * radius * 2);
			for (int col = 0; col < 15; col++) {
				X = (col * radius * 1.75f);
				if (seca.contains(step) || col == 0 || col == 14 || row == 0 || row == 16) {
					HexTile hx = new HexTile(Xstart + X, Ystart + Y, radius, Color.LIGHTGREY, -1);
					tiles.add(hx);
					super.getChildren().add(hx);
					step++;
				} else if (skipsArray.contains(ID)) {
					HexTile hx = new HexTile(Xstart + X, Ystart + Y, radius, Color.LIGHTGREY, -1);
					tiles.add(hx);
					super.getChildren().add(hx);
					step++;
					ID++;
				} else {
					HexTile hx = new HexTile(Xstart + X, Ystart + Y, radius, Color.LIGHTGREY, ID);
					tiles.add(hx);
					super.getChildren().add(hx);
					ID++;
					step++;
				}
				Y += radius;
			}
			X -= 15 * radius * 1.75f;
		}
	}

	public void copulateHexagonals() {
		for (int hx = 0; hx < tiles.size(); hx++) {
			if (tiles.get(hx).getID() == -1)
				continue;

			HexTile current = tiles.get(hx);
			current.setNeighbour(0, tiles.get(hx-15));
			current.setNeighbour(1, tiles.get(hx-14));
			current.setNeighbour(2, tiles.get(hx+1));
			current.setNeighbour(3, tiles.get(hx+15));
			current.setNeighbour(4, tiles.get(hx+14));
			current.setNeighbour(5, tiles.get(hx-1));
		}
	}

	public void drawHexagonalsFullIDs() {
		StartEndCleanArray seca = new StartEndCleanArray();
		int ID = 0;
		int step = 0;
		int radius = 18;
		int Xstart = 100;
		int Ystart = 0; // 15 - (3 * radius * 2);
		double X = 0;
		double Y = 0;
		for (int row = 0; row < 17; row++) {
			Y = (row * radius * 2);
			for (int col = 0; col < 15; col++) {
				X = (col * radius * 1.75f);
				HexTile hx = new HexTile(Xstart + X, Ystart + Y, radius, Color.LIGHTGREY, ID);
				tiles.add(hx);
				super.getChildren().add(hx);
				ID++;
				step++;
				Y += radius;
			}
			X -= 15 * radius * 1.75f;
		}
	}

}
