package brotcrunsher_classes;

import java.io.File;

public class Brotcrunsher_44 {
	public static void main(String[] args){
		File f = new File("C://test.txt");
		if(f.exists()){
			System.out.println("Die Datei " + f.getName() + " existiert!");
		}
		else{
			System.out.println("Die Datei " + f.getName() + " existiert nicht!");
		}
	}
}
