package Pr_06_package;

import de.beiri22.attestation.Testat;
import de.beiri22.attestation.praktika.Praktika;

public class Main_06 {

	public static void main(String[] args) {
		Testat t = new Testat(Praktika.Praktikum6);
		t.addClass(LinProbing::new);
		t.addClass(DoubleHashing::new);
		t.check();
	}

}
