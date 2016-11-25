import java.util.HashMap;

public class SportErgebnisse {

	public static void main(String[] args) {
		HashMap<String, String> ergebnisse = new HashMap<String, String>();
		for(int k = 0;k<args.length; k=k+2){
			ergebnisse.put(args[k], args[k+1]);
		}
		ergebnisse.forEach((name,zeit) -> {
			System.out.println(name + "\t - " + zeit);
		});
	}

}
