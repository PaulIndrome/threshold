package brotcrunsher_classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;

public class Brotcrunsher_45 {
	public static void main(String[] args){
		File f = new File("C://test.txt"); 						//dem Programm mitteilen, dass da eine Datei mit diesem Namen liegt
		if(!f.exists()){						//Ausrufezeichen invertiert die boole'sche Abfrage
		try{
			Formatter format = new Formatter("C://test.txt");  //überschreibt immer die bestehende Datei
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		}
	}
}
