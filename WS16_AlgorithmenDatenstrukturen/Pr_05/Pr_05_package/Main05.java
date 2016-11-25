package Pr_05_package;

import de.beiri22.attestation.Testat;
import de.beiri22.attestation.praktika.Praktika;
import vorgaben.praktikum5.SepChainingTester;

public class Main05 {

	public static void main(String[] args) {
//		SepChainingTester tester = new SepChainingTester(new SepChaining());
//		tester.run();
		
		Testat t = new Testat(Praktika.Praktikum5);
		t.addClass(SepChaining::new);
		t.check();
	}

}
