import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Suchalgorithmus {

	public static String Suchteil = "";
	public char[] Suchmuster = {'d','e','r'};
	public static StringBuilder SuchText;
	public static FileOutputStream out;
	
	
	public static void main(String[] args){
		
		try {
			out = new FileOutputStream("file");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader blindtext = null;
		try {
			blindtext = new BufferedReader(new FileReader("blindtext.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		SuchText = new StringBuilder();
		
		try {
			while(blindtext.ready()){
			SuchText.append(blindtext.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}