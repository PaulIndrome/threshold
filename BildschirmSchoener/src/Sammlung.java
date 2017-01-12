

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeSet;
public class Sammlung {
	public static void main(String[] args) {
		benutzungHashSet();
		benutzungTreeSet();
		benutzungHashMap();
	}
	
	private static void benutzungHashMap(){
		HashMap<String, String> namen = new HashMap<String, String> ();
		namen.put("Uwe Schneider", "uschneid@hs-mittweida.de");
		namen.put("Dirk Pawlaszczyk", "pawlaszc@hs-mittweida.de");
		namen.put("Knut Altroggen", "altrogge@hs-mittweida.de");
		
		System.out.println(namen.get ("Uwe Schneider"));
		
		Iterator iter = namen.entrySet().iterator();
		while(iter.hasNext()){
			Entry eintrag = (Entry)iter.next(); //muss importiert werden, java.util.Map (!!!)
			System.out.println(eintrag.getKey() +" - " + eintrag.getValue());
			
		}
	}
	
	private static void benutzungTreeSet(){
		Collection<Double> zahlen = new TreeSet<Double>();
		
		zahlen.add(5.46);
		zahlen.add(5.45);
		zahlen.add(5.47);
		
		Iterator iter = zahlen.iterator();
		
		while(iter.hasNext()){
			System.out.println(iter.next());
			
		}
		
		System.out.println("---------------------");
		
		zahlen.add(5.48);
		zahlen.add(5.49);
		zahlen.add(5.44);
		zahlen.add(5.44);
		zahlen.add(5.43);
		
		iter = zahlen.iterator();
		
		while(iter.hasNext()){
			System.out.println(iter.next());
			
		}
		System.out.println("----------------------");
		zahlen.remove(5.46);
		iter=zahlen.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
	}
	
	private static void benutzungHashSet(){
		Collection<Double> zahlen = new HashSet<>();
		
		zahlen.add(5.46); //zufaellig ausgewaehlte Daten
		zahlen.add(5.45);
		zahlen.add(5.47);
		
		Iterator iter = zahlen.iterator();
		
		while(iter.hasNext()){//existiert noch ein weiteres Element
			System.out.println(iter.next());
		}
		
		System.out.println("-----------------------");
		
		zahlen.add(5.48);
		zahlen.add(5.49);
		zahlen.add(5.44);
		zahlen.add(5.44);
		zahlen.add(5.43);
		
		iter = zahlen.iterator();
		
		while(iter.hasNext()){
			System.out.println(iter.next());
			
		}
		
		System.out.println("----------------------");
		zahlen.remove(5.46);
		iter = zahlen.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
			
		}
	}

}