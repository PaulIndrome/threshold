package backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import application.Item;
import application.StuffCollection;

public class Backtracking {

	private int size;
	private int maxWeight;
	private long elapsedTime;
	private String packedItemsString;
	private int maxCombinationIndex;

	private ArrayList<Item> itemsList;
	private String[] names;
	private double[] ratios;
	private int[] indizes;
	private int[][] items;
	private int[][] matrix;

	// this iteration of Backtracking is dependent on a collection of stuff
	// generated via an application
	public Backtracking(StuffCollection sc, int MaxWeight) {
		this.maxWeight = MaxWeight;
		itemsList = new ArrayList<Item>();
		elapsedTime = 0;

		// check size of StuffCollection
		size = sc.getSize();

		// generate matrix for backtrackM
		// this matrix saves the values of all weight states (0 to MaxWeight+1)
		// for each item
		// thus, all possible individual combinations can be saved/memorized
		matrix = new int[size][MaxWeight + 1];
		for (int i = 0; i < size; i++) {
			for (int x = 0; x < MaxWeight + 1; x++) {
				matrix[i][x] = 0;
			}
		}

		// instantiate arrays with appropriate size
		names = new String[size];
		items = new int[size][2];
		// values = new int[size];
		ratios = new double[size];
		indizes = new int[size];

		// fill arrays with appropriate values
		int counter = 0;
		for (Item i : sc.getItems()) {
			itemsList.add(i);
			names[counter] = i.getName();
			items[counter][0] = i.getWeight();
			items[counter][1] = i.getValue();
			ratios[counter] = i.getRatio();
			indizes[counter] = counter;
			counter++;
		}

		// if(size<=24)
		// combinations = new int[(int) Math.pow(2, size)];

	}

	// wrapper method to start backtracking with the version specified by a user
	// input integer
	public int startBacktrack(int backtrackVersion) {
		packedItemsString = "";
		long start;
		long end;
		int value;
		switch (backtrackVersion) {
		// backtrack recursive with array
		case 1:
			start = System.nanoTime();
			value = backtrackA(maxWeight, this.items, itemsList.size());
			end = System.nanoTime();
			elapsedTime = end - start;
			return value;
		// backtrack recursive with ArrayList
		case 2:
			start = System.nanoTime();
			value = backtrackB(maxWeight, itemsList, itemsList.size());
			end = System.nanoTime();
			elapsedTime = end - start;
			return value;
		// backtrack recursive memorized
		case 3:
			start = System.nanoTime();
			value = backtrackM(size - 1, maxWeight, items);
			end = System.nanoTime();
			elapsedTime = end - start;
			findPackedItemsFromMatrix();
			return value;
		// backtrack greedy
		case 4:
			sortItemsByRatio();
			start = System.nanoTime();
			value = backtrackG(maxWeight, items);
			end = System.nanoTime();
			elapsedTime = end - start;
			return value;
		// backtrack iterative memorized
		case 5:
			start = System.nanoTime();
			value = backtrackIterative(maxWeight, items, size);
			end = System.nanoTime();
			elapsedTime = end - start;
			findPackedItemsFromMatrix();
			return value;
		// bruteforce over all combinations in binary
		case 6:
			if (size <= 24) {
				start = System.nanoTime();
				value = findMaxValueOfBinarySubsets(generateValidBinarySubsets(size));
				end = System.nanoTime();
				elapsedTime = end - start;
				return value;
			} else {
				return -1;
			}
		default:
			return -1;
		}
	}

	// Scan all generated binary subsets for the one that amounts to the highest value.
	// This could also be done upon generating the binary subsets with relative ease
	public int findMaxValueOfBinarySubsets(boolean[][] binaries) {
		int max = 0;
		int currentValue;

		for (boolean[] ba : binaries) {
			String tempItemString = "";
			int index = 0;
			currentValue = 0;
			for (boolean b : ba) {
				if (b) {
					currentValue += items[index][1];
					tempItemString += "\n\t" + names[index] + "  :\t\tW" + items[index][0] + "\t\tV" + items[index][1]
							+ "\t\tR" + ratios[index];
				}
				index++;
			}
			if (currentValue > max) {
				max = currentValue;
				packedItemsString = tempItemString;
			}
		}
		return max;
	}

	// this method counts upwards to the max possible amount of 1-0 combinations
	// in binary and converts those numbers bit by bit into boolean arrays
	// representing those combinations of packed/unpacked items.
	// During combination of items by binary string, the items' weight is
	// already included in the pack/nopack decision.
	// During transfer of the currently computed combination into the collection
	// of combinations, a check is performed whether or not this combination has
	// already been computed and subsequently ignored in that case.
	public boolean[][] generateValidBinarySubsets(int n) {
		// maximum amount of individual combinations: 2 to the item amount'th
		int allComb = (int) Math.pow(2, n);
		// length of binary representation of maximum amount
		int l = Long.toBinaryString(allComb).length() - 1;
		// instantiation of arrays to be computed/filled
		boolean[][] binaries = new boolean[allComb][n];
		ArrayList<boolean[]> binaries2 = new ArrayList<boolean[]>();
		// string to be added in front of the binary representation
		for (int i = 0; i < allComb; i++) {
			boolean[] current = new boolean[n];
			int currentWeight = 0;
			// string to be added in front of the binary representation
			String pre = "";
			// binary representation of current count number
			String s = Long.toBinaryString(i);
			// difference between s.length and desired string length
			int sl = l - s.length();
			// add the necessary amount of zeroes to fill string representation
			for (int j = 0; j < sl; j++) {
				pre += "0";
			}
			pre += s;

			// fill the current boolean array according to the string
			// representation and whether or not adding the item's weight
			// exceeds the maximum Weight
			int index = 0;
			for (char c : pre.toCharArray()) {
				current[index] = (c == '0' || currentWeight + items[index][0] > maxWeight) ? false : true;
				if (current[index]) {
					currentWeight += items[index][0];
				}
				index++;
			}

			// check if the current boolean array has already been computed and
			// skip it if necessary
			if (binaries2.size() < 1) {
				// first boolean array is always added
				binaries2.add(current);
			} else {
				for (int b = 0; b < binaries2.size(); b++) {
					if (Arrays.equals(binaries2.get(b), current)) {
						break;
					} else if (b + 1 == binaries2.size()) {
						binaries2.add(current);
					}
				}
			}

		}
		// write all validated boolean arrays back into an array of boolean
		// arrays (for future ease of access to computed combinations)
		int counter = 0;
		for (boolean[] ba : binaries2) {
			binaries[counter] = ba;
			counter++;
		}
		return binaries;
	}

	// capacity decreases by weight of an added item
	// capacity in further recursions of the method equals capacity left in the
	// knapsack
	public int backtrackA(int capacity, int[][] items, int amount) {
		// if we've reached the last item or maximum capacity
		// (capacity - capacity = 0),
		// the call returns with no value / zero value
		if (amount == 0 || capacity == 0)
			return 0;

		// if the item doesn't fit into the knapsack, skip it
		if (items[amount - 1][0] > capacity)
			return backtrackA(capacity, items, amount - 1);
		else
			// this line basically starts building the binary tree for every
			// item taken/not taken combination, which is then resolved from the
			// bottom up by eliminating the combination that has lead to a
			// lesser value (by dead-ending at full capacity)
			return max(items[amount - 1][1] + backtrackA(capacity - items[amount - 1][0], items, amount - 1),
					backtrackA(capacity, items, amount - 1));
	}

	//same as backtrackA but accessing an ArrayList (for purposes of comparison)
	public int backtrackB(int capacity, ArrayList<Item> items, int amount) {
		if (amount == 0 || capacity == 0)
			return 0;
		if (items.get(amount - 1).getWeight() > capacity)
			return backtrackB(capacity, items, amount - 1);
		else
			return max(
					items.get(amount - 1).getValue()
							+ backtrackB(capacity - items.get(amount - 1).getWeight(), items, amount - 1),
					backtrackB(capacity, items, amount - 1));
	}

	// backtracking with memorized values of already calculated combinations of
	// items -> matrix[itemIndex][capacity left]
	public int backtrackM(int amount, int capacity, int[][] items) {
		// take and dontTake are integers that are calculated to be incremented
		// or not incremented based on whether or not an item fits into the
		// knapsack and is taken or not taken
		int take = 0, dontTake = 0;

		// if the integer at the position of the method arguments is already
		// set, this means the value of that combination has already been
		// calculated
		if (matrix[amount][capacity] != 0)
			return matrix[amount][capacity];

		// if the amount is zero, check if the very first item of the array (the
		// last to be computed) still fits inside the capacity
		// if that is the case, save the value in the matrix and return from the
		// method call with it
		// if not (item doesn't fit), write the matrix position of this method
		// call as 0 and return with it
		if (amount == 0) {
			if (items[0][0] <= capacity) {
				matrix[amount][capacity] = items[0][1];
				return items[0][1];
			} else {
				matrix[amount][capacity] = 0;
				return 0;
			}
		}

		/*
		 * This block is the bones of the calculation for take and dontTake.
		 * Basically, this checks if an item can be added to the knapsack and
		 * adds its value to the recursion if that is the case
		 */
		/*
		 * The recursion of backtracking with memorized states branches out very
		 * far. Every item is recursively checked whether it fits into the
		 * knapsack in the individual method call's branch or not. What improves
		 * the speed of this calculation is that every method call checks
		 * whether or not its combination of amount of items and capacity left
		 * (mapped in a two-dimensional array) has already been calculated.
		 */
		if (items[amount][0] <= capacity)
			take = items[amount][1] + backtrackM(amount - 1, capacity - items[amount][0], items);

		// the dontTake branch of the recursion is always expanded to ensure
		// that every item is checked at least once (but never twice in a
		// similar combination, f.e. vItem1 + vItem2 = vItem2 + vItem1
		dontTake = backtrackM(amount - 1, capacity, items);

		// the maximum of either branch is calculated and returned
		// this resolves itself from the farthest leaf back to the root
		matrix[amount][capacity] = max(take, dontTake);
		return matrix[amount][capacity];
	}

	// convenience method to recollect the items that the backtrackM method has
	// decided to pack into the knapsack
	public void findPackedItemsFromMatrix() {
		int i = size - 1;
		int k = maxWeight;

		// from the furthest (bottom-right) [item][capacity]coordinate, check
		// upwards from i=amount if the value changes in the i-1th row.
		// If that is the case, it can be deduced that the i'th item belongs to
		// the list of packed items
		while (i >= 0 && k > 0) {
			// if the top most item (row 0) is being checked, check against zero
			if (i == 0) {
				if (matrix[i][k] != 0) {
					packedItemsString += "\n\t" + names[i] + "  :\t\tW" + items[i][0] + "\t\tV" + items[i][1] + "\t\tR"
							+ ratios[i];
				}
				i--;
				// check the i'th item against its predecessor
			} else if (matrix[i][k] != matrix[i - 1][k]) {
				packedItemsString += "\n\t" + names[i] + "  :\t\tW" + items[i][0] + "\t\tV" + items[i][1] + "\t\tR"
						+ ratios[i];
				k = k - items[i][0];
				i--;
				// if nothing has changed from i,k to i-1,k, move further up
			} else {
				i--;
			}
		}
	}

	// greedy knapsack packing based on weight-to-value ratio.
	// this finds a good approximation of the optimal value rather fast, but not
	// necessarily the optimal value itself
	// As this method is an iterative one, a list of packed items is easily made
	public int backtrackG(int capacity, int[][] items) {
		int currentWeight = 0;
		int counter = 0;
		int value = 0;
		// iterative check whether the capacity has been reached and if the item
		// on top of the unchecked part of the list still fits into the
		// knapsack.
		// if said item still fits into the knapsack, prior sorting of the
		// entire itemsList by decreasing ratio ensures that the item in
		// question is the next best item by ratio
		while (currentWeight <= capacity && counter < ratios.length) {
			// if the item fills the capacity to max, return the collected value
			if (currentWeight + items[counter][0] == capacity) {
				packedItemsString += "\n\t" + names[counter] + "  :\t\tW" + items[counter][0] + "\t\tV"
						+ items[counter][1] + "\t\tR" + ratios[counter];
				return value += items[counter][1];
				// if the item doesn't fill the capacity, add its value and its
				// weight
			} else if (currentWeight + items[counter][0] < capacity) {
				packedItemsString += "\n\t" + names[counter] + "  :\t\tW" + items[counter][0] + "\t\tV"
						+ items[counter][1] + "\t\tR" + ratios[counter];
				currentWeight += items[counter][0];
				value += items[counter][1];
				counter++;
				continue;
			}
			counter++;
		}
		return value;
	}

	public int backtrackIterative(int capacity, int[][] items, int amount) {
		for (int i = 0; i < amount; i++) {
			for (int w = 1; w <= capacity; w++) {
				if (i == 0) {
					matrix[i][w] = 0;
				} else {
					if (items[i][0] <= w)
						matrix[i][w] = max(items[i][1] + matrix[i - 1][w - items[i][0]], matrix[i - 1][w]);
					else
						matrix[i][w] = matrix[i - 1][w];
				}
			}
		}
		return matrix[amount - 1][maxWeight];
	}

	// convenience method to calculate the maximum of two values
	public int max(int a, int b) {
		return (a >= b) ? a : b;
	}

	
	// method to be called before packing with greedy-by-ratio
	// convenience method to make sure the itemsList is sorted by ratio in
	// descending order
	public void sortItemsByRatio() {
		itemsList.sort(new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				return (int) (o1.getRatio() * 100 - o2.getRatio() * 100);
			}
		});
		Collections.reverse(itemsList);

		// refill arrays with appropriate values
		int counter = 0;
		for (Item i : itemsList) {
			names[counter] = i.getName();
			items[counter][0] = i.getWeight();
			items[counter][1] = i.getValue();
			ratios[counter] = i.getRatio();
			counter++;
		}
	}
	
	public int getMaxWeight() {
		return maxWeight;
	}

	public long getLastElapsedTime() {
		return (elapsedTime > 0) ? elapsedTime : -1;
	}

	public String getPackedItems() {
		return packedItemsString;
	}


}
