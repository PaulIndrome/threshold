package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;

public class Chapter {
	private String KapitelTextPfad;
	// private String KapitelBildPfad;
	private byte choice = 0;

	public Chapter(String kapTextPfad) {
		KapitelTextPfad = kapTextPfad;

	}

	public void setChoice(byte c) {
		choice = c;
	}

	public byte getChoice() {
		return choice;
	}

	public String getKapitelTextPfad() {
		return KapitelTextPfad;
	}
	/*
	 * public String getBildPfad(){ return KapitelBildPfad; }
	 */

	public void writeChapter(TextField textfield) {
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
				// for (int i = 2; i > 0; i--) {
				twochars += (char) chapter.read(); // String mit zwei chars
													// füllen
				Matcher m = twocapitals.matcher(twochars); // matcher für
															// pattern
															// anlegen
				if (m.find()) { // falls der matcher das pattern findet
								// String mit char langsamer ausgeben
					Thread.sleep((long) 100);
					textfield.setText(twochars);
					Thread.sleep((long) (250 + Math.random() * 50));
				} else {
					textfield.setText(twochars);
					Thread.sleep((long) (100 + Math.random() * 30));
				}
			}
			chapter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
