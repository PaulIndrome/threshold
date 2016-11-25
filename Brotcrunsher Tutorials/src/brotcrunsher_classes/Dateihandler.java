package brotcrunsher_classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dateihandler {
	Scanner s;
	
	Dateihandler(File f){
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Mensch lesen(){
		if(s.hasNext()){
			int alter = s.nextInt();
			int iq = s.nextInt();
			String name = s.next();
			String haarfarbe = s.next();
			return new Mensch (alter, iq, name, haarfarbe);
		}
		return null;
	}
	
	public void schliessen(){
		s.close();
	}
}
