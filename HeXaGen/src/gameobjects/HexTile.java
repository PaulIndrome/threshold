package gameobjects;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HexTile extends Pane {

	boolean terrain = true;
	int ID;
	double radius;
	int[] neighbourIDs = new int[6];
	HexTile[] neighbours = new HexTile[6];

	Polygon hex;

	public HexTile(double leftX, double leftY, double r, Color c, int ID) {
		super();
		super.setTranslateX(leftX);
		super.setTranslateY(leftY);

		double ratioY = 0.865;
		double ratioX = 0.5;
		leftX = 0;
		leftY = 2 * r * 0.43f;
		this.ID = ID;
		radius = r;

		hex = new Polygon(leftX, leftY, // left
				leftX + r * ratioX, leftY - r * ratioY, // upper left
				leftX + r * (1 + ratioX), leftY - r * ratioY, // upper right
				leftX + r * 2, leftY, // right
				leftX + r * (1 + ratioX), leftY + r * ratioY, // lower right
				leftX + r * ratioX, leftY + r * ratioY); // lower left
		
		hex.setStrokeType(StrokeType.INSIDE);
		hex.setStrokeWidth(r / 18);
		hex.setFill(c);

		if (ID == -1)
			terrain = false;
		else {
			setMouseBehavior();
			Text idText = new Text(r * 0.5f, r*1.05f, "" + ID);
			idText.setFill(Color.GRAY);
			idText.setMouseTransparent(true);
			idText.setFont(new Font(r / 2));
			super.getChildren().addAll(hex, idText);
		}

	}

	private void setMouseBehavior() {
		hex.setOnMouseEntered(e -> {
			hex.setStroke(Color.BLACK);
//			super.setBorder(new Border(
//					new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), BorderStroke.THIN)));
		});

		hex.setOnMouseExited(e -> {
			hex.setStroke(Color.TRANSPARENT);
//			super.setBorder(null);
		});
	}

	public void setNeighbour(int dir, HexTile nHx) {
		neighbours[dir] = nHx;
		/*Text tx = new Text("" + nHx.getID());
		tx.setFont(new Font(radius / 2.75f));
		switch (dir) {
		case 0:
//			tx.setFill(Color.RED);
			tx.setTranslateX(radius * 0.75f);
			tx.setTranslateY(radius * 0.25f);
			break;
		case 1:
//			tx.setFill(Color.BLUE);
			tx.setTranslateX(radius * 1.25f);
			tx.setTranslateY(radius * 0.65f);
			break;
		case 2:
//			tx.setFill(Color.GREEN);
			tx.setTranslateX(radius * 1.25f);
			tx.setTranslateY(radius * 1.4f);
			break;
		case 3:
//			tx.setFill(Color.VIOLET);
			tx.setTranslateX(radius * 0.75f);
			tx.setTranslateY(radius * 1.75f);
			break;
		case 4:
//			tx.setFill(Color.DEEPPINK);
			tx.setTranslateX(radius * 0.25);
			tx.setTranslateY(radius * 1.4f);
			break;
		case 5:
//			tx.setFill(Color.CYAN);
			tx.setTranslateX(radius * 0.25f);
			tx.setTranslateY(radius * 0.65f);
			break;
		default:
			System.out.println("Copulation between Hexagonals went in the wrong direction.");
		}
		super.getChildren().add(tx);*/
	}

	public void setTerrain(boolean terrain) {
		this.terrain = terrain;
	}

	public void swapTerrain() {
		terrain = !terrain;
	}

	public void disable() {
		super.getChildren().clear();
	}

	@Override
	public String toString() {
		return "" + super.getTranslateX() + " " + ID;
	}

	public int getID() {
		return ID;
	}
}
