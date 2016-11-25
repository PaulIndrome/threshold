package Pr_03_package;

import java.util.Arrays;
import java.util.Comparator;

import de.beiri22.attestation.Testat;
import de.beiri22.attestation.praktika.Praktika;
import vorgaben.praktikum3.MultiSearch;
import vorgaben.praktikum3.Wikipedia;

public class Main03 {

	public static void main(String[] args) {
		Testat t = new Testat(Praktika.Praktikum3);
		t.addClass(SequentialSearch::new);
		t.addClass(BinarySearch::new);
		t.addClass(ExponentialSearch::new);
		t.check();
		
		MultiSearch ms = new MultiSearch();
		ms.addAlgorithm(new SequentialSearch());
		ms.addAlgorithm(new BinarySearch());
		ms.addAlgorithm(new ExponentialSearch());
		String[] data = Wikipedia.getWordsOfAlgorithms();
		ms.compareSearch(data, "Algorithmus");
		ms.compareSearch(data, "Hälfte");
		Arrays.sort(data, Comparator.comparing(Object::hashCode));
		ms.compareSearch(data, "Algorithmus");
		ms.compareSearch(data, "Hälfte");

		// String[] test = new String[] { "b", "bb", "bbb", "bbbb", "bbbbb",
		// "bbbbbb" };
		//
		// BinarySearch bs = new BinarySearch();
		// System.out.println(bs.performSearch(test, 0, test.length, "bbbb"));
	}

}
