package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteChapterViaBufferedStreamReader {

	public WriteChapterViaBufferedStreamReader(String path) {

	}

	public static void main(String[] args) {
		Pattern twocapitals = Pattern.compile("[A-Z]"); // Großbuchstaben Suchmuster

		BufferedReader chapter1 = null; // bufferedreader anlegen
		try {
			chapter1 = new BufferedReader(
					new FileReader("F:\\Java\\Workspace\\Text_test\\src\\application\\chapter1.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			while (chapter1.ready()) {
				String twochars = ""; // String vorbereiten
				// for (int i = 2; i > 0; i--) {
				twochars += (char) chapter1.read(); // String mit zwei chars
													// füllen
				// }
				Matcher m = twocapitals.matcher(twochars); // matcher für
															// pattern
				// anlegen
				if (m.find()) { // falls der matcher das pattern findet
								// String mit char langsamer ausgeben
					Thread.sleep((long) 100);
					System.out.print(twochars);
					Thread.sleep((long) (250 + Math.random() * 50));
					/*
					 * if (twochars.length() == 2) {
					 * System.out.print(twochars.charAt(1)); Thread.sleep((long)
					 * (360 + Math.random() * 50)); }
					 */

				}

				else {
					System.out.print(twochars);
					Thread.sleep((long) (100 + Math.random() * 30));
					/*
					 * if (twochars.length() == 2) {
					 * System.out.print(twochars.charAt(1)); Thread.sleep((long)
					 * (100 + Math.random() * 30)); }
					 */

				}

			}
			chapter1.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
