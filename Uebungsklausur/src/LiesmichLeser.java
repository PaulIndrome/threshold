import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LiesmichLeser {

	public static void main(String[] args) {
		try {
			FileReader in = new FileReader("liesmich.txt");
			while (in.ready()) {
				System.out.print((char)in.read());
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
