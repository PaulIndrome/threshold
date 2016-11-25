package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Chapter {
	private String KapitelTextPfad;
	// private String KapitelBildPfad;
	private int choice;

	public Chapter(String kapTextPfad) {
		KapitelTextPfad = kapTextPfad;

	}

	public void setChoice(int c) {
		choice = c;
	}

	public int getChoice() {
		return choice;
	}

	public String getKapitelTextPfad() {
		return KapitelTextPfad;
	}

	public void writeChapter(TextArea textarea) throws InterruptedException {
		Pattern twocapitals = Pattern.compile("[A-Z]"); // Großbuchstaben
														// Suchmuster
		BufferedReader chapter = null; // bufferedreader anlegen
		try {
			chapter = new BufferedReader(new FileReader(this.getKapitelTextPfad()));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			while (chapter.ready()) {
				String twochars = ""; // String vorbereiten
				for (int i = 2; i > 0; i--) {
					char nextChar = (char) chapter.read(); // String mit
					twochars += nextChar;					// zwei chars
				} 											// füllen
				Matcher m = twocapitals.matcher(twochars); // matcher für
															// pattern
															// anlegen
				if (m.find()) { // falls der matcher das pattern findet
								// String mit char langsamer ausgeben
					Thread.sleep((long) 100);
					textarea.appendText(twochars);
					Thread.sleep((long) (250 + Math.random() * 50));
				} else {
					textarea.appendText(twochars);
					Thread.sleep((long) (120 + Math.random() * 40));

				}
			}
			chapter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} /*
			 * catch (InterruptedException e) { e.printStackTrace(); }
			 */
	}
	
	public void writeChapterInLabel(Label mainlabel) throws InterruptedException {
		Pattern twocapitals = Pattern.compile("[A-Z]"); // Großbuchstaben
														// Suchmuster
		BufferedReader chapter = null; // bufferedreader anlegen
		try {
			chapter = new BufferedReader(new FileReader(this.getKapitelTextPfad()));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			while (chapter.ready()) {
				String twochars = ""; // String vorbereiten
				String fullstring = "";
				for (int i = 2; i > 0; i--) {
					char nextChar = (char) chapter.read(); // String mit
					twochars += nextChar;					// zwei chars
				} 											// füllen
				Matcher m = twocapitals.matcher(twochars); // matcher für
															// pattern
															// anlegen
				if (m.find()) { // falls der matcher das pattern findet
								// String mit char langsamer ausgeben
					fullstring += twochars;
					Thread.sleep((long) 100);
					mainlabel.setText(fullstring);
					Thread.sleep((long) (250 + Math.random() * 50));
				} else {
					fullstring += twochars;
					mainlabel.setText(fullstring);
					Thread.sleep((long) (120 + Math.random() * 40));

				}
			}
			chapter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} /*
			 * catch (InterruptedException e) { e.printStackTrace(); }
			 */
	}
}
