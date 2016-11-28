package praktikum02;


import java.io.*;

/**
 * Einfache Klasse zur Tastatur-Eingabe von Zeichenketten und Zahlenwerten. Beim
 * Einlesen von Zahlenwerten werden Format-Ausnahmen ausgeloest, wenn keine
 * gueltige Zahl eingegeben wird.
 * 
 */

public class Keyboard {
	/**
	 * Zum Einlesen der Tastatur-Eingabe wird ein BufferedReader vom
	 * Standard-Eingabe-Kanal verwendet.
	 */
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	/**
	 * Lesen einer Zeichenkette von der Tastatur. Eine hier nur theoretisch
	 * moegliche IOException wird ohne Reaktion abgefangen.
	 * 
	 * @return eingegebene Zeichenkette
	 */
	public static String readString() {
		String s = "";
		try {
			s = in.readLine();
		} catch (IOException e) {
			/*
			 * keine Reaktion
			 */
		}
		;
		return s;
	}

	/**
	 * Lesen eines int-Werts von der Tastatur.
	 * 
	 * @return eingegebener int -Wert
	 * @exception NumberFormatException
	 *                falls kein gueltiger int-Wert eingegeben wird
	 */
	public static int readint() throws NumberFormatException {
		String s = readString();
		int n = Integer.parseInt(s);
		return n;
	}

	/**
	 * Lesen eines long-Werts von der Tastatur.
	 * 
	 * @return eingegebener long -Wert
	 * @exception NumberFormatException
	 *                falls kein gueltiger long-Wert eingegeben wird
	 */
	public static long readlong() throws NumberFormatException {
		String s = readString();
		long n = Long.parseLong(s);
		return n;
	}

	/**
	 * Lesen eines float-Werts von der Tastatur.
	 * 
	 * @return eingegebener float -Wert
	 * @exception NumberFormatException
	 *                falls kein gueltiger float -Wert eingegeben wird
	 */
	public static float readfloat() throws NumberFormatException {
		String s = readString();
		float n = Float.valueOf(s).floatValue();
		return n;
	}

	/**
	 * Lesen eines double-Werts von der Tastatur.
	 * 
	 * @return eingegebener double -Wert
	 * @exception NumberFormatException
	 *                falls kein gueltiger double -Wert eingegeben wird
	 */
	public static double readdouble() throws NumberFormatException {
		String s = readString();
		double n = Double.valueOf(s).doubleValue();
		return n;
	}
	
	public static boolean readboolean() throws NumberFormatException {
		String s = readString();
		boolean n = Boolean.parseBoolean(s);
		return n;
	}
}
