
public class IterationRekursion {

	public static void IterateNmal(int n, String text){
	    for(int i = 0; i < n; i++){
	        System.out.println( i + ". Durchlauf");
	    }
	}
	
	public static void RecurseNmal(int n, String text){
		n--;
		if(n>0)
		RecurseNmal(n,text);
		System.out.println(n + ". Durchlauf");
	}
	
	
	public static void main(String[] args) {
		int anzahl = 12;
		IterateNmal(anzahl,"text");
		System.out.println("");
		RecurseNmal(anzahl,"text");

	}

}
