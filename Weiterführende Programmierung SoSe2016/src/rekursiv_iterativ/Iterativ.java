package rekursiv_iterativ;

public class Iterativ {

	public static void iter1(int n) {

		while (n >= 0) {

			System.out.print(n + ", "); // die erste Schleife dekrementiert n
										// NACH dem SysOut, wodurch n=0 der
										// letzte erfolgreiche Durchlauf ist.

			n--;

		}

	}

	public static void iter2(int n) {

		while (n >= 0) {

			n--;

			System.out.print(n + ", "); // die zweite Schleife dekrementiert n
										// VOR dem SysOut, was zur Folge hat,
										// dass der Durchlauf mit n=10 bei "9"
										// anfängt und n=0 eine "-1" ausgibt

		}

	}

	public static void main(String[] args) {
		int n = 10;

		iter1(n);

		System.out.println("");

		iter2(n);

	}

}
