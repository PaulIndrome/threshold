package listing09;

public class EdgeScan {

	public static void edgeScan(int xmin, int ymin, int xmax, int ymax) {
		int x = xmin;
		// 1/m = (x1-x0) / (y1-y0) = z/n
		int z = xmax - xmin;
		int n = ymax - ymin;
		int i = n;
		System.out.println("Zähler: " + z + " Nenner: " + n);
		System.out.println("y\tInc\tSchnitt\tx (links)\tx (rechts)");
		for (int y = ymin; y <= ymax; y++) {

			double schnitt_bruch = x + i%n / (double)n; 
			int schnitt = x + i%n / n;
			int xl = schnitt;
			int xr = schnitt - 1;
			System.out.println(y + "\t" + i + "\t" + schnitt_bruch + "\t" + xl
					+ "\t\t" + xr);

			i = i + z;
			if (i > n) {
				x = x + 1;
				i = i - n;
			}
		}
	}

	public static void main(String[] args) {
		edgeScan(2, 1, 4, 6);

	}

}
