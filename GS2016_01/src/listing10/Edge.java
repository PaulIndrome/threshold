package listing10;

import java.awt.Point;

public class Edge implements Comparable<Edge> {
	int y_min; // unteres Ende der Liste
	int y_max; // oberes  Ende der Liste 
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
		return getClass().getName() + "[y_max=" + y_max + ",d_x="
				+ d_x + ",d_y=" + d_y + ",inc=" + inc
				+ ",x=" + x + "]";
	}
}
