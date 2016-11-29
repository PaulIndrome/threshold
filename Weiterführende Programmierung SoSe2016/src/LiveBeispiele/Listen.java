package LiveBeispiele;

public class Listen {
	public static void main (String[] args){
		einfacheListe();
		testLIFO();
		
	}
	
	private static void testLIFO(){
		int n = 7;
		LIFOListe liste = new LIFOListe();
		System.out.println(liste);
		for (int i=0;i<n;i++){
			liste.pop();
			System.out.println(liste);
		}
	}
	
	private static void einfacheListe(){
		int n = 7;
		EinfachListe liste = new EinfachListe();
		System.out.println(liste);
		for (int i=0;i<n;i++){
			liste.insertFirst (new Integer(i));
			System.out.println(liste);
		}
		for (int i=0;i<n;i++){
			liste.deleteFirst();
			System.out.println(liste);
			
		}
	}
}                                                                                                      