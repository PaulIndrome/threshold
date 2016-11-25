package Pr_04_package;

import de.beiri22.attestation.Testat;
import de.beiri22.attestation.praktika.Praktika;
import vorgaben.praktikum4.TrieTester;

public class Main04 {

	public static void main(String[] args) {
		Testat t = new Testat(Praktika.Praktikum4);
		t.addClass(Node::new);
		t.addClass(Trie::new);
		t.check();
		
		TrieTester treeTester = new TrieTester(new Trie());
		treeTester.run();
	}

}
