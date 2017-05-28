package application;

import javafx.scene.control.TextArea;

public class Decktester {

	TextArea text;

	int zielWert;
	double spielWert = 0;
	int handKarten = 0;

	double[] kWerte = new double[3];
	int[] kAnzahl = { 3, 5, 8 };

	boolean gameEndedOnce = false;

	public Decktester(int zielW, TextArea tA) {
		text = tA;

		if (zielW <= 0) {
			zielWert = 5000;
		} else {
			zielWert = zielW;
		}

		berechneKarten(zielWert);
	}

	// Haupt-KI
	public int spiele() {
		while (!(spielWert > zielWert)) {
			if (spielWert < (zielWert / 2)) {
				hit();
			}
			// else if (spielWert > (zielWert - (zielWert / 10))) {
			// stand();
			// return 1;
			// }
			else if (spielWert == zielWert) {
				win();
				gameEndedOnce = true;
				return 2;
			} else {
				double drawNoDraw = Math.random() + Math.random();
//				System.out.println("drawNoDraw = " + drawNoDraw);
				if (drawNoDraw >= 1) {
					hit();
				} else {
					stand();
					gameEndedOnce = true;
					return 1;
				}
			}
		}
		lose();
		gameEndedOnce = true;
		return 0;
	}

	public void berechneKarten(int zW) {
		kWerte[0] = zW / 5;
		kWerte[1] = kWerte[0] / 2;
		kWerte[2] = kWerte[1] / 2;
//		 System.out.println();
//		 for (double x : kWerte) {
//		 System.out.print("\t" + x);
//		 }
	}

	public void hit() {
		handKarten++;
		int gezogeneK;
		boolean abgezogen = false;
		while (!abgezogen) {
			gezogeneK =  (int)(Math.random() * 3);
			if (!(kAnzahl[gezogeneK] <= 0)) {
				kAnzahl[gezogeneK]--;
				spielWert += kWerte[gezogeneK];
				abgezogen = true;
				return;
			}
		}
	}

	public void win() {
		if (!gameEndedOnce)
			text.appendText("\nWIN!\tWert: " + spielWert + "\t\tHandkarten: " + handKarten + " :\t" + (3 - kAnzahl[0])
					+ " h, " + (5 - kAnzahl[1]) + " m, " + (8 - kAnzahl[2]) + " l");
		// System.out.println("WIN!\tWert: " + spielWert + "\t\tHandkarten: " +
		// handKarten);
	}

	public void stand() {
		if (!gameEndedOnce)
			text.appendText("\nSTAND!\tWert: " + spielWert + "\t\tHandkarten: " + handKarten + " :\t" + (3 - kAnzahl[0])
					+ " h, " + (5 - kAnzahl[1]) + " m, " + (8 - kAnzahl[2]) + " l");
		// System.out.println("STAND!\tWert: " + spielWert + "\t\tHandkarten: "
		// + handKarten);
	}

	public void lose() {
		if (!gameEndedOnce)
			text.appendText("\nLOSE!\tWert: " + spielWert + "\t\tHandkarten: " + handKarten + " :\t" + (3 - kAnzahl[0])
					+ " h, " + (5 - kAnzahl[1]) + " m, " + (8 - kAnzahl[2]) + " l");
		// System.out.println("LOSE!\tWert: " + spielWert + "\t\tHandkarten: " +
		// handKarten);
	}

}

/*
 * Zielwert 1
 * 
 * Menge Karten H M L 3
 * 
 * Kartenwerte Teiler H M L 3
 * 
 */
