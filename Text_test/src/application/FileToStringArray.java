package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileToStringArray {
	
	public FileToStringArray(){
		
	}

	public String[] StringArrayFromFile(String path) {
		String[] outputArray = null;
		String output = "";
		BufferedReader x = null;
		try {
			// Der Reader als Buffer
			x = new BufferedReader(new FileReader(path));
			// Output String vorbereiten
			// For loop zum auslesen jeder verfuegbaren zeile in den String,
			// jeweils mit einem Zeilenumbruch (Kann sein dass hier leere zeilen
			// verschwinden O.o )
			for (String o = x.readLine(); o != null; o = x.readLine())
				output += "\n" + o;
			// Erstes Zeilenumbruchzeichen entfernen
			output = output.substring(1, output.length());
			outputArray = output.split("[*]");
		} catch (Exception e) {
			// Hier sollten nur IO und FileNotFoundExceptions auftreten.
			System.out.println("File not found!");
			e.printStackTrace();
		} finally {
			try {
				x.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(output);
		return outputArray;

	}
	
	/*public static void main(String[] args){
		String [] test = StringArrayFromFile("F:\\Java\\Workspace\\Text_test\\src\\application\\accusation.txt");
		for(int i = 0; i < test.length; i++){
			System.out.print(test[i]);
			System.out.print("HIER WIRD GETRENNT!");
		}*/
	}