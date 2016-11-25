package brotcrunsher_classes;

import java.io.File;

public class Brotcrunsher_46 {
	public static void main(String[] args){
		File f = new File("C://Mensch.txt");
		Dateihandler dh = new Dateihandler(f);
		Mensch m = dh.lesen();
		Mensch u = dh.lesen();
		System.out.println(m.toString());
		System.out.println("\n" + u.toString());
		dh.schliessen();
	}
}
