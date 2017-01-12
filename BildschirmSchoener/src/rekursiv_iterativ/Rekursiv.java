package rekursiv_iterativ;

public class Rekursiv {

	public static void down1(int n) {

		if (n <= 0)
			return;

		System.out.print(n + ", "); // erste Version stapelt den SysOut sofort
									// mit dem n des Methodenaufrufs, erst
									// danach wird n dekrementiert, die Ausgabe
									// erfolgt nach dem FIFO Prinzip

		down1(n - 1);

	}

	public static void down2(int n) {

		if (n <= 0)
			return;

		down2(n - 1);

		System.out.print(n + ", "); // zweite Version stapelt die n solange, bis
									// n<=0, und gibt dann alle "aufgestauten"
									// Ergebnisse nach dem LIFO Prinzip per
									// SysOut aus

	}

	public static void main(String[] args) {
		int n = 10;

		down1(n);

		System.out.println("");

		down2(n);

	}

}
