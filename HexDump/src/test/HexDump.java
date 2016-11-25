package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HexDump {
	
	public static String getHex(byte b){
		if(b<=15){
		switch(b){
		case 0: return "0";
		case 1: return "1";
		case 2: return "2";
		case 3: return "3";
		case 4: return "4";
		case 5: return "5";
		case 6: return "6";
		case 7: return "7";
		case 8: return "8";
		case 9: return "9";
		case 10: return "A";
		case 11: return "B";
		case 12: return "C";
		case 13: return "D";
		case 14: return "E";
		case 15: return "F";
		default: return null;
		}
		}
		return null;
	}

	public static String dump(String filename){
		int BREITE = 16;
		String dump = "";
		String hexline = "";
		String txtline = "";
		Path filepath = Paths.get(filename);
		FileInputStream in = null;
		try {
			in = new FileInputStream(filepath.toFile());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte b = 0;
		try {
			b = (byte)in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 1;
		while(b!=-1){
			if (b<=15)
				hexline += "0";
			hexline += getHex(b);
		}
		
	
	}
}
