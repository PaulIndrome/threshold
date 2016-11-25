import java.util.HashMap;
public class WortZaehler {
	public static void main(String[] args) {
		HashMap<String, Integer> Strings = new HashMap<String, Integer>();
		for (String s : args) {
			if (Strings.containsKey(s)) {
				Strings.put(s, Strings.get(s) + 1);
			} else {
				Strings.put(s, 1);
			}
		}
		Strings.forEach((String, Integer) -> {
			System.out.println(String + " " + Integer);
		});
	}
}
