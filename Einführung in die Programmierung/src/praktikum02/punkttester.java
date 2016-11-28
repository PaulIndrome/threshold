package praktikum02;

public class punkttester {

	public static void main(String[] args){
		
		Display d1 = new Display(400, 500);
		
		System.out.println("Erzeugen und Anzeigen 1. Punkt. Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
		Punkt p1 = new Punkt(5,7); //das erste "Punkt" referenziert die Klasse, "new Punkt(...)" referenziert den Konstruktor und erstellt eine Instanz daraus
		System.out.println("1. Punkt erzeugt");
	
		System.out.println("x = " + p1.getx());
		System.out.println("y = " + p1.gety()); //funktionieren, weil die Instanzierung von "Punkt" als p1 betitelt ist.
		
				d1.show(p1);
		
/* Umbruch */ System.out.println("\n");

		System.out.println("Erzeugen und Anzeigen Ursprungspunkt. Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
		Punkt p0 = new Punkt();
		System.out.println("Ursprungspunkt p0 erzeugt");
		
		System.out.println("x = " + p0.getx());
		System.out.println("y = " + p0.gety());
		
		System.out.println("Ursprung: " + p0.toString()); //p0 ist eine Instanz bzw. KindKlasse aus der Punkt Klasse, beinhaltet also alle seine Methoden. Diese werden über <Instanzname>.Methode referenziert
		System.out.println("Punkt 1: " + p1.toString());
		
				d1.show(p0);
		
/* Umbruch */ System.out.println("\n");		

		System.out.println("Versetzen p0 nach (8|9). Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
	
/* Umbruch */ System.out.println("\n");
		
		p0.versetzen(8, 9); //aufrufen der versetzen Methode in Punkt Klasse nach Art s.o.
		System.out.println("Ursprung versetzt nach: " + p0.toString());
		
				d1.show(p0);
				
/* Umbruch */ System.out.println("\n");				
		System.out.println("Verschieben p0 um (2|4). Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
/* Umbruch */ System.out.println("");
		
		p0.verschieben(2, 4);
		System.out.println("Ursprung verschoben nach: " + p0.toString());
		
				d1.show(p0);		
		
/* Umbruch */ System.out.println("\n");

		System.out.println("Vergleichsoperationen p0 und p1. Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
/* Umbruch */ System.out.println("");
		
		System.out.println("Vergleichen p0 und p1 über 'if ==' ");
		if (p0 == p1){
			System.out.println("p0 und p1 zeigen auf gleichen Punkt");	
		}
		else{
			System.out.println("p0 und p1 zeigen auf unterschiedlichen Punkt");
		}
		
/* Umbruch */ System.out.println("");
		
		System.out.println("Vergleichen p0 und p1 über 'equals Methode' ");
		if (p1.equals(p0)){ //equals Methode in Klammer, weil in den if-Befehl eingebettet
			System.out.println("p0 und p1 sind deckungsgleich");	
		}
		else{
			System.out.println("p0 und p1 sind NICHT deckungsgleich");
		}

/* Umbruch */ System.out.println("\n");

		System.out.println("Erstellen und Anzeigen p3 durch Nutzer. Drücke [ENTER], um fortzufahren.");
		Keyboard.readString();
		
/* Umbruch */ System.out.println("");
		
		System.out.println("Gib die Koordinaten für Punkt 3 (p3) an:");
		Punkt p3 = new Punkt(Keyboard.readdouble(), Keyboard.readdouble());
		System.out.println("3. Punkt p3 erzeugt an x|y = ( " + p3.getx() + " | " + p3.gety() + " )");
		
				d1.show(p3);
		
/* Umbruch */ System.out.println("");

		System.out.println("Versetzen p0 nach p3. Drücke [ENTER], um fortzufahren.\n");
		Keyboard.readString();
		
		p0.versetzen(p3.getx(), p3.gety());
		d1.show(p0);
		System.out.println("p0 versetzt nach: " + p0.toString() + " = p3:" + p3.toString());
		
/* Umbruch */ System.out.println("");

		System.out.println("Vergleichen p0 und p3 über 'equals Methode'. Drücke [ENTER], um fortzufahren.\n");
		Keyboard.readString();
		if (p0.equals(p3)){ //equals Methode in Klammer, weil in den if-Befehl eingebettet
			System.out.println("p0 und p3 sind deckungsgleich");	
		}
		else{
			System.out.println("p0 und p3 sind NICHT deckungsgleich");
		}
		
/* Umbruch */ System.out.println(""); //MIRRORPOINT, SPIEGELN EINES PUNKTES
		System.out.println("Drücke [ENTER], um fortzufahren.\n");
		Keyboard.readString();
		
		System.out.println("Spiegeln eines Punktes. Welcher Punkt soll gespiegelt werden? p0: " + p0.getx() + "|" + p0.gety() + " / p1: " + p1.getx() + "|" + p1.gety() + " / p3: " + p3.getx() + "|" + p3.gety());
			String mirrorwhichpoint = Keyboard.readString();
			
			switch (mirrorwhichpoint){
				case "p0": 	p0.spiegeln(p0);
							d1.show(p0);
							System.out.println("p0 gespiegelt zu " + p0.getx() + "|" + p0.gety());
							break;
				case "p1": 	p1.spiegeln(p1);
							d1.show(p1);
							System.out.println("p1 gespiegelt zu " + p1.getx() + "|" + p1.gety());
							break;
				case "p3": 	p3.spiegeln(p3); 
							d1.show(p3);
							System.out.println("p3 gespiegelt zu " + p3.getx() + "|" + p3.gety());
							break;
				default: System.out.println("Kein gültiger Punkt angegeben.");
			}
			
/* Umbruch */ System.out.println(""); //PYTHAGORAS, DISTANZ ZUM NULLPUNKT
			System.out.println("Drücke [ENTER], um fortzufahren.\n");
			Keyboard.readString();
			
			System.out.println("Abstand eines Punktes zum Nullpunkt. Für welchen Punkt soll der Abstand berechnet werden? p0: " + p0.getx() + "|" + p0.gety() + " / p1: " + p1.getx() + "|" + p1.gety() + " / p3: " + p3.getx() + "|" + p3.gety());
				String distancezerowhichpoint = Keyboard.readString();
				
				switch (distancezerowhichpoint){
					case "p0": 	System.out.println("Der Abstand von p0 zu (0|0) beträgt: " + p0.distancezero(p0) + " Längeneinheiten");
								break;
					case "p1": 	System.out.println("Der Abstand von p1 zu (0|0) beträgt: " + p1.distancezero(p1) + " Längeneinheiten");
								break;
					case "p3": 	System.out.println("Der Abstand von p3 zu (0|0) beträgt: " + p3.distancezero(p3) + " Längeneinheiten");
								break;
					default: System.out.println("Kein gültiger Punkt angegeben.");
				}
				
/* Umbruch */ System.out.println(""); //TRIGONOMETRIE, WINKEL ZW. BASISLINIE UND X-ACHSE
				System.out.println("Drücke [ENTER], um fortzufahren.\n");
				Keyboard.readString();
				
				System.out.println("Winkel zwischen Basislinie und x-Achse. Für welchen Punkt soll der Winkel berechnet werden? p0: " + p0.getx() + "|" + p0.gety() + " / p1: " + p1.getx() + "|" + p1.gety() + " / p3: " + p3.getx() + "|" + p3.gety());
					String degreeofwhichpoint = Keyboard.readString();
					
					switch (degreeofwhichpoint){
						case "p0": 	System.out.println("Der Winkel der Basislinie p0 zur x-Achse beträgt: " + p0.degreex(p0) + "°");
									break;
						case "p1": 	System.out.println("Der Winkel der Basislinie p1 zur x-Achse beträgt: " + p1.degreex(p1) + "°");
									break;
						case "p3": 	System.out.println("Der Winkel der Basislinie p3 zur x-Achse beträgt: " + p3.degreex(p3) + "°");
									break;
						default: System.out.println("Kein gültiger Punkt angegeben.");
					}
		
		
		
		
		
	}
}
