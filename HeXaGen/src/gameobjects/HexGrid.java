package gameobjects;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import material.StartEndCleanArray;

public class HexGrid extends Pane {


	HexTile[] tilesArray = new HexTile[255];

	ArrayList<Integer> skipsArray = new ArrayList<Integer>();

	public HexGrid(double prefWidth, double prefHeight, int[] skips) {
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
		int step = 0;
		
		for (int row = 0; row < 17; row++) {
			s += "(" + tilesArray[step].getID() + ") " + tilesArray[step].getTerrain();
			step++;
			for (int col = 1; col < 15; col++) {
				s += ", (" + tilesArray[step].getID() + ") " + tilesArray[step].getTerrain();
				step++;
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
					tilesArray[step] = hx;
//					tiles.add(hx);
					super.getChildren().add(hx);
					step++;
				} else if (skipsArray.contains(ID)) {
					HexTile hx = new HexTile(Xstart + X, Ystart + Y, radius, Color.LIGHTGREY, -1);
					tilesArray[step] = hx;
//					tiles.add(hx);
					super.getChildren().add(hx);
					step++;
					ID++;
				} else {
					HexTile hx = new HexTile(Xstart + X, Ystart + Y, radius, Color.LIGHTGREY, ID);
					tilesArray[step] = hx;
//					tiles.add(hx);
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
		for (int hx = 0; hx < tilesArray.length; hx++) {
			if (tilesArray[hx].getID() == -1)
				continue;

			HexTile current = tilesArray[hx];
			current.setNeighbour(0, tilesArray[hx-15]);
			current.setNeighbour(1, tilesArray[hx-14]);
			current.setNeighbour(2, tilesArray[hx+1]);
			current.setNeighbour(3, tilesArray[hx+15]);
			current.setNeighbour(4, tilesArray[hx+14]);
			current.setNeighbour(5, tilesArray[hx-1]);
		}
	}

	public void drawHexagonalsFullIDs() {
//		StartEndCleanArray seca = new StartEndCleanArray();
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
				tilesArray[step] = hx;
//				tiles.add(hx);
				super.getChildren().add(hx);
				ID++;
				step++;
				Y += radius;
			}
			X -= 15 * radius * 1.75f;
		}
	}
	
	public HexTile getHexTileByID(int ID){
		for(int i = 22;i<tilesArray.length;i++)
			if(tilesArray[i].getID()==ID)
				return tilesArray[i];
		return null;
	}

}
