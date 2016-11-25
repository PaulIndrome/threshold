package listing13;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class PatternFiller {

	public static class Edge implements Comparable<Edge> {
		int y_min;
		int y_max;
		int d_x;
		int d_y;
		int inc;
		int x;

		public Edge(Point v1, Point v2) {
			super();
			y_min = (v1.y < v2.y) ? v1.y : v2.y;
			y_max = (v1.y > v2.y) ? v1.y : v2.y;
			d_x = v1.x - v2.x;
			d_y = v1.y - v2.y;
			if (d_y < 0) {
				d_x = -d_x;
				d_y = -d_y;
			}
			inc = d_y;
			x = (v1.y < v2.y) ? v1.x : v2.x;
		}

		public int getSign() {
			return (this.d_x < 0) ? -1 : 1;
		}

		@Override
		public int compareTo(Edge e) {
			if (this.x < e.x) {
				return -1;
			} else if (this.x == e.x) {
				return 0;
			} else {
				return 1;
			}
		}

		@Override
		public String toString() {
			return this.getClass().getName() + ",y_max=" + this.y_max + ",d_x="
					+ this.d_x + ",d_y=" + this.d_y + ",inc=" + this.inc
					+ ",x=" + this.x + "]";
		}
	}
	
	public static final BufferedImage PATTERN = PatternFiller.loadImage("brick.png");
	
	public static final BufferedImage loadImage(String name) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(PatternFiller.class.getResourceAsStream(name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	public static void drawPatternPixel(BufferedImage image, int x, int y) {
		image.setRGB(x, y, PATTERN.getRGB(x % PATTERN.getWidth(), y % PATTERN.getHeight()));
	}

	public static void fillPattern(BufferedImage image,
			List<Point> vertices) {
		Map<Integer, List<Edge>> edgeTable = new HashMap<Integer, List<Edge>>();
		for (int i = 0; i < vertices.size(); i++) {
			Point vertex = vertices.get(i);
			Point nextVertex = (i < vertices.size() - 1) ? vertices.get(i + 1)
					: vertices.get(0);
			if (vertex.y != nextVertex.y) {

				Edge e = new Edge(vertex, nextVertex);
				System.out.println(e.toString());
				if (!edgeTable.containsKey(e.y_min)) {
					List<Edge> aList = new ArrayList<Edge>();
					edgeTable.put(e.y_min, aList);
				}
				edgeTable.get(e.y_min).add(e);
				Collections.sort(edgeTable.get(e.y_min));
			}
		}

		List<Edge> activeEdgeTable = new ArrayList<Edge>();
		int y = 0;
		// Wiederhole bis AET und ET leer sind
		while ((edgeTable.keySet().size() > 0 || activeEdgeTable.size() > 0)
				&& y < image.getHeight()) {
			// Übernehme die Kanten der aktuellen Scan Line von der ET in die
			// AET
			if (edgeTable.containsKey(y)) {
				for (Edge e : edgeTable.get(y)) {
					activeEdgeTable.add(e);
				}
				edgeTable.remove(y);
			}
			// Einhalten der Ordnung
			Collections.sort(activeEdgeTable);
			// Entferne aus der AET die fertigen Kanten
			ArrayList<Edge> newActiveEdgeTable = new ArrayList<Edge>();
			for (Edge e : activeEdgeTable) {
				if (e.y_max > y) {
					newActiveEdgeTable.add(e);
				}
			}
			activeEdgeTable = newActiveEdgeTable;
			// Füllen der Pixel zwischen Schnittpunkten mit ungerader und
			// gerader Parität
			// von links x bis rechts x-1
			for (int i = 0; i < activeEdgeTable.size(); i += 2) {
				Edge e1 = activeEdgeTable.get(i);
				Edge e2 = activeEdgeTable.get(i + 1);
				for (int x = e1.x; x < e2.x; x++) {
					PatternFiller.drawPatternPixel(image, x, y);
				}
			}
			// Erhöhe y um 1
			y++;
			// für alle nicht vertikalen Kanten in AET, aktualisiere x für die
			// nächste Scan-Linie
			for (Edge e : activeEdgeTable) {
				if (e.d_x != 0) {
					e.inc += e.getSign() * e.d_x;
					while (e.inc > e.d_y) {
						e.x += e.getSign();
						e.inc -= e.d_y;
					}
				}
			}
			// falls nötig (sich selbst schneidende Polygone) muss AET neu
			// sortiert werden
			Collections.sort(activeEdgeTable);
		}
	}

}
