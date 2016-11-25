package isoExtract;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ISOExtractRAF {

	public static void main(String[] args) {
		RandomAccessFile isoRAF = null; // RAF und byte Array initiieren
		byte[] ISOBytes = null;
		// String[] ISOHex = null;
		try {
			isoRAF = new RandomAccessFile("src\\isoExtract\\datei.iso", "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			ISOBytes = new byte[(int) isoRAF.length()];
			// ISOHex = new String[(int) isoRAF.length()];
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			for (int i = 0; i < isoRAF.length(); i += 2) {
				ISOBytes[i] = isoRAF.readByte(); // byte Array mit Wertpaaren
													// füllen
				ISOBytes[i + 1] = isoRAF.readByte(); // Die Staffelung in
														// Zweierschritten ist
														// nicht notwendig,
														// beruht auf einem
														// früheren
														// Lösungsansatz
				// ISOHex[i] = String.format("%02X ", ISOBytes[i]) +
				// String.format("%02X ", ISOBytes[i + 1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * Alle Bytes im Array werden per Converter der HexUtils Klasse in einen
		 * kontinuierlichen String gepackt. Die Nutzung des Converters erfolgte
		 * widerwillig und auf Grund von Zeitmangel
		 */
		String ISOHexConverted = HexUtils.bytesToHex(ISOBytes);
		/*
		 * Im Folgenden werden alle Substrings, beginnend und endend mit den
		 * Signaturen der jeweiligen Dateitypen, extrahiert und separiert
		 */
		String ISOjpg = ISOHexConverted.substring(ISOHexConverted.indexOf("FFD8FF"), ISOHexConverted.indexOf("FFD900"));
		String ISOpng = ISOHexConverted.substring(ISOHexConverted.indexOf("89504E470D0A1A0A"),
				ISOHexConverted.indexOf("49454E44AE426082"));
		String ISOgif = "";
		if (ISOHexConverted.contains("474946383761")) {
			ISOgif = ISOHexConverted.substring(ISOHexConverted.indexOf("474946383761"),
					ISOHexConverted.indexOf("003B"));
		} else if (ISOHexConverted.contains("474946383961")) {
			ISOgif = ISOHexConverted.substring(ISOHexConverted.indexOf("474946383961"),
					ISOHexConverted.indexOf("003B"));
		}
		String ISOpdf = "";
		if (ISOHexConverted.contains("0A2525454F460A")) {
			ISOpdf = ISOHexConverted.substring(ISOHexConverted.indexOf("25504446"),
					ISOHexConverted.indexOf("0A2525454F460A"));
		} else if (ISOHexConverted.contains("0D0A2525454F460D0A")) {
			ISOpdf = ISOHexConverted.substring(ISOHexConverted.indexOf("25504446"),
					ISOHexConverted.indexOf("0D0A2525454F460D0A"));
		} else if (ISOHexConverted.contains("0D2525454F460D")) {
			ISOpdf = ISOHexConverted.substring(ISOHexConverted.indexOf("25504446"),
					ISOHexConverted.indexOf("0D2525454F460D"));
		}
		String ISOzip = ISOHexConverted.substring(ISOHexConverted.indexOf("504B0304"),
				ISOHexConverted.indexOf("504B050600000000050005006E010000530500000000"));
		/*
		 * zip ist invalid, selbst wenn die HEX Werte mit UltraEdit direkt aus
		 * der Datei auf der gemounteten ISO gezogen sind. Keine Lösung dafür
		 * gefunden
		 */

		RandomAccessFile jpg = null;
		RandomAccessFile png = null;
		RandomAccessFile gif = null; // neue Dateien initiieren
		RandomAccessFile pdf = null;
		RandomAccessFile zip = null;

		try {
			jpg = new RandomAccessFile("ISOjpg.jpg", "rw");
			png = new RandomAccessFile("ISOpng.png", "rw");
			gif = new RandomAccessFile("ISOgif.gif", "rw"); // deklarieren
			pdf = new RandomAccessFile("ISOpdf.pdf", "rw");
			zip = new RandomAccessFile("ISOzip.zip", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/*
		 * Neue Dateien schreiben, wieder mit Hilfe der HexUtils Klasse. Immer
		 * zwei Zeichen des Strings werden als Bytes in die jeweiligen Dateien
		 * geschrieben
		 */
		try {
			jpg.write(HexUtils.asBytes(ISOjpg));
			png.write(HexUtils.asBytes(ISOpng));
			gif.write(HexUtils.asBytes(ISOgif));
			pdf.write(HexUtils.asBytes(ISOpdf));
			zip.write(HexUtils.asBytes(ISOzip));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			jpg.close();
			png.close();
			gif.close();
			pdf.close(); //und alles wieder schließen
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
