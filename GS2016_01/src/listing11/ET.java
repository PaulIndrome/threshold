package listing11;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ET {

	public static class Edge implements Comparable<Edge> {
		int y_min; // unteres Ende der Liste
		int y_max; // oberes Ende der Liste
		int d_x; // Anstieg, Zaehler
		int d_y; // Anstieg, Nenner
		int inc; // Inkrement
		int x; // x-Wert des Schnittpunktes

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
			if (x < e.x) {
				return -1;
			} else if (x == e.x) {
				return 0;
			} else {
				return 1;
			}
		}

		@Override
		public String toString() {
			return getClass().getName() + "[y_max=" + y_max + ",d_x=" + d_x
					+ ",d_y=" + d_y + ",inc=" + inc + ",x=" + x + "]";
		}
	}

	public static void createET(List<Point> vertices) {
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
	}
}
