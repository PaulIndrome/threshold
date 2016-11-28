import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class IntArraySpeichern {
	public static void main(String[] args) {
		int[] werte = new int[]{3,4,2,56,78,3,4,90};
			try{
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("file"));
				out.writeObject(werte);
				out.close();
			} catch (IOException e){
				e.printStackTrace();
			}
	}
}
