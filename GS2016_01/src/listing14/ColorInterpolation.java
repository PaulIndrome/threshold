package listing14;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ColorInterpolation {
	
	public static int INDEX_RED = 0;
	public static int INDEX_GREEN = 1;
	public static int INDEX_BLUE = 2;

	public static class ColoredPoint {
		public int x;
		public int y;
		public int rgb;
		
		@Override
		public String toString() {
			return this.getClass().getName() + "[x=" + x + ",y=" + y + ",rgb=0x" + Integer.toHexString(rgb) + "]";
		}
	}

	public static class Edge implements Comparable<Edge> {
		int y_min;
		int y_max;
		int d_x;
		int d_y;
		int inc;
		int x;
		int currentRgb;
		int x1;
		int y1;
		int rgb1;
		int rgb2;
		int x2;
		int y2;

		public Edge(ColoredPoint v1, ColoredPoint v2) {
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
			if (v1.y < v2.y) {
				x = v1.x;
				rgb1 = v1.rgb;
				x1 = v1.x;
				y1 = v1.y;
				rgb2 = v2.rgb;
				x2 = v2.x;
				y2 = v2.y;
			} else {
				x = v2.x;
				rgb1 = v2.rgb;
				x1 = v2.x;
				y1 = v2.y;
				rgb2 = v1.rgb;
				x2 = v1.x;
				y2 = v1.y;
			}
			currentRgb = rgb1;
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
			return this.getClass().getName() + "[y_max=" + this.y_max + ",d_x="
					+ this.d_x + ",d_y=" + this.d_y + ",inc=" + this.inc
					+ ",x=" + this.x + "]";
		}
	}
	
	public static int linearInterpolation(int rgb1, int rgb2, double t) {
		int[] colors1 = splitIntegerPixel(rgb1);
		int[] colors2 = splitIntegerPixel(rgb2);
		int[] interpolated = new int[3];
		for (int i = 0; i < interpolated.length; i++) {
			int newRgb = (int)((1 - t) * colors1[i] + t * colors2[i]);
			interpolated[i] = (newRgb < 256) ? newRgb : 255; 
		}
		return combineColors(interpolated);
	}

	public static void interpolateColor(BufferedImage image, List<ColoredPoint> vertices) {
		HashMap<Integer, ArrayList<Edge>> edgeTable = new HashMap<Integer, ArrayList<Edge>>();
		for (int i = 0; i < vertices.size(); i++) {
			ColoredPoint vertex = vertices.get(i);
			ColoredPoint nextVertex = (i < vertices.size() - 1) ? vertices
					.get(i + 1) : vertices.get(0);
			if (vertex.y != nextVertex.y) {

				Edge e = new Edge(vertex, nextVertex);
				System.out.println(e.toString());
				if (!edgeTable.containsKey(e.y_min)) {
					ArrayList<Edge> aList = new ArrayList<Edge>();
					edgeTable.put(e.y_min, aList);
				}
				edgeTable.get(e.y_min).add(e);
				Collections.sort(edgeTable.get(e.y_min));
			}
		}

		ArrayList<Edge> activeEdgeTable = new ArrayList<Edge>();
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
				double lengthOfScanLine = e2.x - e1.x;
				for (int x = e1.x; x < e2.x; x++) {
					int rgb = linearInterpolation(e1.currentRgb, e2.currentRgb, (x-e1.x)/lengthOfScanLine);
					image.setRGB(x, y, rgb);
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
				// aktualisiere die Farbe für alle Kanten in der AET
				double d1 = Math.sqrt(Math.pow(e.x1 - e.x,2.0) + Math.pow(e.y1 - y, 2.0));
				double d2 = Math.sqrt(Math.pow(e.x - e.x2,2.0) + Math.pow(y - e.y2, 2.0));
				double t = d1 / (d1 + d2);
				e.currentRgb = linearInterpolation(e.rgb1, e.rgb2, t);
			}
			// falls nötig (sich selbst schneidende Polygone) muss AET neu
			// sortiert werden
			Collections.sort(activeEdgeTable);
		}
	}
	
	public static int[] splitIntegerPixel(int rgb) {
		int[] colors = new int[3];
		int red = (rgb >> 16) & 0x000000FF;
		int green = (rgb >> 8) & 0x000000FF;
		int blue = (rgb) & 0x000000FF;
		colors[INDEX_RED] = red;
		colors[INDEX_GREEN] = green;
		colors[INDEX_BLUE] = blue;
		return colors;
	}

	public static int combineColors(int red, int green, int blue) {
		return ((red << 16) & 0x00FF0000) + ((green << 8) & 0x0000FF00)
				+ (blue & 0x000000FF);
	}

	public static int combineColors(int[] colors) {
		return combineColors(
				colors[INDEX_RED],
				colors[INDEX_GREEN],
				colors[INDEX_BLUE]);
	}

}
