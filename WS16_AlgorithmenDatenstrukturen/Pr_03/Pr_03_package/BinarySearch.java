package Pr_03_package;

import vorgaben.praktikum3.SearchAlgorithm;

public class BinarySearch implements SearchAlgorithm {

	@Override
	public int performSearch(String[] data, int start, int end, String target) throws IllegalArgumentException {
		if (data == null || data.length == 0 || start < 0 || end >= data.length || target == null) {
			throw new IllegalArgumentException();
		}

		int middle = (start + end) / 2;

		if (start > end) {
			return -1;
		} else if (target.hashCode() == data[middle].hashCode()) {
			return middle;
		} else if (target.hashCode() < data[middle].hashCode()) {
			return performSearch(data, start, middle - 1, target);
		} else if (target.hashCode() > data[middle].hashCode()) {
			return performSearch(data, middle + 1, end, target);
		}
		return -1;
	}

}
