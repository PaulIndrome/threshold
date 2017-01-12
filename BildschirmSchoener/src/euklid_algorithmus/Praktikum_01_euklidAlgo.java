package euklid_algorithmus;

public class Praktikum_01_euklidAlgo {
	private static int A;
	private static int B;
	private static int R; //Zahlen A, B, Rest und Ergebnis
	private static int GGT;
	private static boolean nochmal = true;
	
	
	public static void ZahlenLesen(){
		A = 0;
		B = 0;
		System.out.println("Bitte geben Sie für A und B Zahlen größer als 0 ein.");
		while (A <= 0){				//Sicherung gegen Falscheingaben
		A = Keyboard.readint();
		}
		while (B <= 0){
		B = Keyboard.readint();
		}
		System.out.println("A = " + A + "; B = " + B);

	}
	
	public static int Algorithmus(int A, int B){
		if(A < B){
			int C = A;
			A = B;
			B = C;
		}
					//Algorithmus, erstes Modulo muss "manuell" ausgeführt werden
		R = A % B;
		
		while (R>0){
		A = B;
		B = R;
		R = A % B;
		}
		GGT = B;
		return GGT;
	}
	
	public static void main (String[] args){
		while(nochmal == true){
		ZahlenLesen();
		Algorithmus(A,B);
		System.out.println(GGT);
		System.out.println("Nochmal? (J / N)");
		String s = Keyboard.readString();
			switch (s){
			case "J":	nochmal = true;
						break;
			case "j":	nochmal = true;
						break;
			case "N":	nochmal = false;
						break;
			case "n": 	nochmal = false;
						break;
			default:	nochmal = false;
						break;
			}
		}
	}
}
