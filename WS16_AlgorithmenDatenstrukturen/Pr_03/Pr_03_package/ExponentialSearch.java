package Pr_03_package;

import vorgaben.praktikum3.SearchAlgorithm;

public class ExponentialSearch implements SearchAlgorithm {

	@Override
	public int performSearch(String[] data, int start, int end, String target) throws IllegalArgumentException {
		if (data == null || data.length == 0 || start < 0 || end >= data.length || target == null) {
			throw new IllegalArgumentException();
		}
		BinarySearch bs = new BinarySearch();
		int j = 1;

		while (start + j <= end && target.hashCode() > data[start + j].hashCode()) {
			j = 2 * j;
		}
		if (start + j < end) {
			return bs.performSearch(data, start + j / 2, start + j, target);
		} else {
			return bs.performSearch(data, start + j / 2, end, target);
		}
	}

}
