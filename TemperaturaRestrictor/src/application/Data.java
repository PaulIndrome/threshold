package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Data {

	/*
	 * Variablen für 2 Thermometer und 1 Hydrometer (noch nicht als Hardware
	 * vorhanden), sowie Optimalwerte und Zustand der Heizmatte
	 */

	public static double aktTemp1 = 0;
	public static double aktTemp2 = 0;
	public static double aktHydr = 100;

	public static double maxTemp = 28;
	public static double minTemp = 23;
	public static double minHydr = 50;
	public static double maxHydr = 80;

	public static boolean statusMatte;

	public Data() {

		aktHydr = 65;
		maxTemp = 28;
		minTemp = 23;
		minHydr = 50;
		maxHydr = 80;

	}

	// Methode: Abrufen der Daten vom Server

	public void holeDaten() {
		try {

			URL url = new URL(
					"http://shuffler.temp-hosts.thejeremail.net:8086/query?db=scarlet_the_scarred_snake&q=SELECT%20last(value)%20FROM%20temperature%20WHERE%20\"thermometer\"%20=%20'1'&u=christina&p=123456");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream()));

			String wert = buffer.readLine();
			String split = wert.split("values\"")[1].split("\",")[1].split("]")[0];

			aktTemp1 = Double.parseDouble(split);
			
			URL url2 = new URL(
					"http://shuffler.temp-hosts.thejeremail.net:8086/query?db=scarlet_the_scarred_snake&q=SELECT%20last(value)%20FROM%20temperature%20WHERE%20\"thermometer\"%20=%20'2'&u=christina&p=123456");
			BufferedReader buffer2 = new BufferedReader(new InputStreamReader(url2.openStream()));

			String wert2 = buffer2.readLine();
			String split2 = wert2.split("values\"")[1].split("\",")[1].split("]")[0];
			
			aktTemp2 = Double.parseDouble(split2);
			
			//System.out.println(wert);
			//System.err.println(split);
			//System.out.println("aktTemp1:" + aktTemp1);
			//System.out.println(wert2);
			//System.out.println(split2);
			//System.out.println("aktTemp2:" + aktTemp2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Abgleich, ob Temperaturen im eingestellten Bereich liegen
	public int tempAbgleich(double temp) {

		if (temp <= maxTemp && temp >= minTemp) {
			return 0;
		} else if (temp < minTemp) {
			return 1;
		} else if (temp > maxTemp) {
			return 2;
		} else {
			return -1;
		}

	}

	// Abgleich der Luftfeuchtigkeit mit eingestelltem Optimalwert
	public int hydrAbgleich(double hydr) {

		if (hydr >= minHydr && hydr <= maxHydr) {
			return 0;
		} else if (hydr < minHydr) {
			System.out.println("Luftfeuchtigkeit zu gering");
			return 1;
		} else if (hydr > maxHydr) {
			System.out.println("Luftfeuchtigkeit zu hoch");
			return 2;
		}
		return -1;
	}

	// Methode zum Einschalten der Heizmatte
	public void heizmatteAn() {
		statusMatte = true;
	}

	// Methode zum Ausschalten der Heizmatte
	public void heizmatteAus() {
		statusMatte = false;
	}

	// Methode zur automatischen Temperaturregelung
	public void autoRegulierung() {

		System.out.println("Auto-Regulierung");

		if (tempAbgleich(aktTemp1) == 2 || tempAbgleich(aktTemp2) == 2) {
			statusMatte = false;
			System.out.println("Matte ausgeschaltet");
		} else {
			statusMatte = true;
			System.out.println("Matte eingeschaltet");
		}
	}
}
