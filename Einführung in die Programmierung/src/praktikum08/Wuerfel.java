package praktikum08;

public class Wuerfel {

	public static int wurfanzahl = 300000;
	public static int wuerfelart = 6; 						//bis zu welcher Zahl kann der Würfel würfeln
	
	public Wuerfel(){
		int[] wuerfe = new int[wurfanzahl];					//das Arrayfeld für die Würfe ist so groß wie die eingestellte Wurfanzahl
		for(int i=0;i<wurfanzahl;i++){ 						//für die Anzahl der Würfe werden die Würfe ausgeführt, in ihr eigenes Arrayfeld geparkt und ausgegeben
			wuerfe[i] = (int)(Math.random()*wuerfelart)+1; 
			System.out.print(wuerfe[i]+" ");
		}
		System.out.println("\n\nEs wurden " + wuerfe.length + " Würfe ausgeführt mit einem W" + wuerfelart);
		
		int augenzahlgesamt = 0;
		for (int i=0;i<wuerfe.length;i++){
			augenzahlgesamt = augenzahlgesamt + wuerfe[i]; //je nach Menge der Würfe ist die Augenzahl gesamt entspr. höher.
		}
		System.out.println("Die Augenzahl in Summe ergibt: " + augenzahlgesamt);
		double durchschnitt = (double)augenzahlgesamt / (double)wurfanzahl;
		System.out.println("Der Durchschnittswert beträgt: " + durchschnitt);

		/* For-schleife erstellt je nach wuerfelart.
 * Die wuerfelart(int) beginnt, damit die Wurfergebnisse in wuerfe[] mit der 
 * Zählung (int d) bis zum Maximalergebnis (d=wuerfelart) verglichen werden können
 * mit 1 und endet bei Zählschritt d > wuerfelart, schließt also das Maximale Ergebnis (d)
 * noch mit ein.
 * Dann wird für jede mögliche Zahl der wuerfelart (1 bis d) 
 * jedes einzelne Würfelergebnis mit ihr verglichen und
 * bei einem Treffer die haeufigkeit des derzeitigen Vergleichswerts
 * um 1 erhöht*/
		
		int[] haeufigkeit = new int[wuerfelart+1];
		
		for(int d=1;d<=wuerfelart;d++){						

			for(int i=0;i<wuerfe.length;i++){
				if (wuerfe[i]==d){
					haeufigkeit[d] = haeufigkeit[d]+1;
				}
				else{
					continue;
				}	
			}
			/* switch (wuerfe[i]){
			case 1 : 	haeufigkeit[0] = haeufigkeit[0]+1;
						continue;
			case 2 : 	haeufigkeit[1] = haeufigkeit[1]+1;
						continue;
			case 3 : 	haeufigkeit[2] = haeufigkeit[2]+1;
						continue;
			case 4 : 	haeufigkeit[3] = haeufigkeit[3]+1;
						continue;
			case 5 : 	haeufigkeit[4] = haeufigkeit[4]+1;
						continue;
			case 6 : 	haeufigkeit[5] = haeufigkeit[5]+1;
						continue;
			default: 	System.out.println("Da war wohl ein Fehler.");
						break;
			} */
		}		
		for(int h=1;h<=wuerfelart;h++){
		System.out.println("Anzahl " + h + " : " + haeufigkeit[h]);		
		}
		
		double[] relativehaeufigkeit = new double[haeufigkeit.length];
		for(int r=1;r<=haeufigkeit.length-1;r++){
			relativehaeufigkeit[r] = ((double)haeufigkeit[r] / wurfanzahl)*100;
			System.out.println("\tRel. Häufigkeit der " + r + " : " + relativehaeufigkeit[r] + "%");
		}
	}
	
	public static void main(String[] args){
		Wuerfel w630 = new Wuerfel();
	}
	
}
