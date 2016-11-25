import de.beiri22.attestation.Testat;
import de.beiri22.attestation.praktika.Praktika;
import vorgaben.praktikum1.GUIBracketMatcher;

public class Main01 {

	public static void main(String[] args) {
		Testat t = new Testat(Praktika.Praktikum1);
		t.addClass(Stack::new);
		t.addClass(BracketMatcher::new);
		new GUIBracketMatcher(new BracketMatcher()).setVisible(true);
		t.check();
		
		
//		BracketMatcher bm = new BracketMatcher();
//		System.out.println(""+ bm.matchSingle('(','['));
//		System.out.println(""+ bm.matchSingle(']',']'));
//		System.out.println(""+ bm.matchSingle(']','}'));
//		System.out.println(""+ bm.matchSingle(']','>'));
//		bm.matchMultiple("()]");
		
	}

}
