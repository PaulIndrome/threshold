import java.io.IOException;
import java.io.RandomAccessFile;

public class RAFwriting {

	// private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = null;
		raf = new RandomAccessFile("FiveDaysAgo.txt", "rw");
		if(args.length==1){
		try{
			raf.writeInt(Integer.parseInt(args[0]));
		} catch (NumberFormatException ne){
			System.out.println("Drrrrr! Falsch! Kein Integer als erstes Argument eingegeben. 5 gesetzt.");
			raf.writeInt(5);
		}
		}
		if(args.length==2){
		raf.writeChar(args[1].charAt(0));
		}
		if(args.length==3){
		raf.writeUTF("ago");
		}
		raf.close();
	}
		// raus kommen soll 5 d ago in der Datei FiveDaysAgo.txt
}
