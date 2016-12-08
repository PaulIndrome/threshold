package Pr_07_package;

import de.beiri22.attestation.Testat;
import de.beiri22.attestation.praktika.Praktika;

public class Main_07 {

	public static void main(String[] args){
		//timeTest();
		test1();
		
	}
	
	public static void test1(){
		Testat t = new Testat(Praktika.Praktikum7);
		t.addClass(NaiveSearch::new);
		t.addClass(KMPSearch::new);
		t.check();
	}
	
	public static void timeTest(){
		NaiveSearch ns = new NaiveSearch();
		
		
		long time1 = System.currentTimeMillis();
		for(int i = 0; i<10000000 ; i++){
			ns.search("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut l", "ipX");
		}
		System.out.println("fabiSearch: " + (System.currentTimeMillis() - time1));
		
		
		long time2 = System.currentTimeMillis();
		for(int i = 0; i<10000000 ; i++){
			ns.ronjaSearch("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut l", "ipX");
		}
		System.out.println("ronjaSearch: " + (System.currentTimeMillis() - time2));
		
	}
}
