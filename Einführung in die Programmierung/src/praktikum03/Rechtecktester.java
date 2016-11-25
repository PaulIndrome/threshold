package praktikum03;

import praktikum03.Keyboard;

public class Rechtecktester {

	public static void main(String[] args){
		
		Display1 d1 = new Display1(400,500);
		Rechteck r1 = new Rechteck(2,2,8,8);
		d1.show(r1);
		System.out.println("Rechteck r1 konstruiert. " + r1.toString());
		
		System.out.println(r1.getStartpunkt());
		
		System.out.println("Erzeugen und Anzeigen eines weiteren Rechtecks (r0). Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
		Rechteck r0 = new Rechteck(new Punkt());
		
		d1.show(r0);
		
		System.out.println("Now what?");
		
		System.out.println("Für welches Rechteck sollen Fläche und Umfang berechnet werden? (r0, r1, beide)");
		String welchesrechteckberechnen = Keyboard.readString();
		
		switch (welchesrechteckberechnen){
			case "r0":
				System.out.println("Die Fläche des Rechtecks r0 beträgt " + r0.berechneFlaeche() + "² Flächeneinheiten.");
				System.out.println("Der Umfang des Rechtecks r0 beträgt " + r0.berechneUmfang() + " Längeneinheiten.");
				break;
			case "r1":
				System.out.println("Die Fläche des Rechtecks r1 beträgt " + r1.berechneFlaeche() + "² Flächeneinheiten.");
				System.out.println("Der Umfang des Rechtecks r1 beträgt " + r1.berechneUmfang() + " Längeneinheiten.");
				break;
			case "beide":
				System.out.println("Die Fläche des Rechtecks r0 beträgt " + r0.berechneFlaeche() + "² Flächeneinheiten.");
				System.out.println("Der Umfang des Rechtecks r0 beträgt " + r0.berechneUmfang() + " Längeneinheiten.");
				System.out.println("\nDie Fläche des Rechtecks r1 beträgt " + r1.berechneFlaeche() + "² Flächeneinheiten.");
				System.out.println("Der Umfang des Rechtecks r1 beträgt " + r1.berechneUmfang() + " Längeneinheiten.");
				break;
			default:
				System.out.println("Dann halt nich.");
		}
		
		System.out.println("Versetzen des Rechtecks r0 nach r1 an Startpunkten. Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
		double r1startx = r1.getStartpunkt().getx();
		double r1starty = r1.getStartpunkt().gety();
		
		r0.versetzen(r1startx, r1starty);
		
		d1.show(r0);
		
		System.out.println("Verschieben eines Rechtecks um eingegebenen Wert. Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
		System.out.println("Welches Rechteck soll verschoben werden? (r0, r1)");
		String verschiebewelchesrechteck = Keyboard.readString();
		
		switch (verschiebewelchesrechteck){
			case "r0":
				System.out.println("Um welche Werte soll das Rechteck " + verschiebewelchesrechteck + " verschoben werden?");		
				double r0dx = Keyboard.readdouble();
				double r0dy = Keyboard.readdouble();
				r0.verschieben(r0dx, r0dy);
				System.out.println("Das Rechteck " + verschiebewelchesrechteck + " ist um " + r0dx + "|" + r0dy + " nach " + r0.getStartpunkt() + " verschoben worden.");
				d1.show(r0);
				break;
			case "r1":
				System.out.println("Um welche Werte soll das Rechteck " + verschiebewelchesrechteck + " verschoben werden?");
				double r1dx = Keyboard.readdouble();
				double r1dy = Keyboard.readdouble();
				r1.verschieben(r1dx, r1dy);
				System.out.println("Das Rechteck " + verschiebewelchesrechteck + " ist um " + r1dx + "|" + r1dy + " nach " + r1.getStartpunkt() + " verschoben worden.");
				d1.show(r1);
				break;
			default:
				System.out.println("Ungültiger Wert.");
				break;
			}
		
		System.out.println("Vergleichen der Rechtecke r0 und r1. Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
		if (r1.equals(r0) == true){
			System.out.println("Die Rechtecke r0 und r1 sind deckungsgleich.");
		}
		else{
			System.out.println("Die Rechtecke r0 und r1 sind NICHT deckungsgleich.");
		}
				
		System.out.println("Rechtecke deckungsgleich machen. Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
		if ((r0.getStartpunkt().getx() > r1.getStartpunkt().getx()) && (r0.getStartpunkt().gety() > r1.getStartpunkt().gety())){
			r0.versetzen(r1.getStartpunkt().getx(), r1.getStartpunkt().gety());
			System.out.println("Startpunkt Rechteck r0 höher als Startpunkt Rechteck r1.\nStartpunkt Rechteck r0 zu Startpunkt Rechteck r1 versetzt.");
			r0.hoehebreiteangleichen(r1.getHoehe(), r1.getBreite());
			System.out.println("Hoehe und Breite Rechteck r0 and Rechteck r1 angepasst.");
			d1.show(r0);
				if (r1.equals(r0) == true){
					System.out.println("Die Rechtecke r0 und r1 sind deckungsgleich.");
				}
				else{
					System.out.println("Die Rechtecke r0 und r1 sind NICHT deckungsgleich.");
				}
		}
		else{
			r1.versetzen(r0.getStartpunkt().getx(), r0.getStartpunkt().gety());
			System.out.println("Startpunkt Rechteck r1 höher als Startpunkt Rechteck r0.\nStartpunkt Rechteck r1 zu Startpunkt Rechteck r0 versetzt.");
			r1.hoehebreiteangleichen(r0.getHoehe(), r0.getBreite());
			System.out.println("Hoehe und Breite Rechteck r1 and Rechteck r0 angepasst.");
			d1.show(r1);
				if (r0.equals(r1) == true){
					System.out.println("Die Rechtecke r0 und r1 sind deckungsgleich.");
				}
				else{
					System.out.println("Die Rechtecke r0 und r1 sind NICHT deckungsgleich.");
				}
		}
		
	}
	
}
