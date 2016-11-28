import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class IntArrayinDatei {

public static void speichereWerte(int[] werte){
	try{
		FileOutputStream out = new FileOutputStream("file");
		for(int i = 0;i<werte.length;i++){
			out.write(werte[i]);
		}
		out.close();
	} catch(Exception e){
		e.printStackTrace();
	}
}

public static void main(String[] args){
	int[] werte = {23,515,132,512,12,123,};
	speichereWerte(werte);
}
}
