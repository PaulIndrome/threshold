package backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import application.Item;
import application.StuffCollection;

public class Backtracking {

	private int maxWeight;
	private String[] names;
	private int[][] items;
	// private int[] values;
	private double[] ratios;
	private ArrayList<Item> itemsList;
	private long elapsedTime;
	private ArrayList<String> packedItems;
	private String packedItemsString;
	private int[][] matrix;
	private int size;

	// this iteration of Backtracking is dependant on a collection of stuff
	// generated via an application
	public Backtracking(StuffCollection sc, int MaxWeight) {
		this.maxWeight = MaxWeight;
		itemsList = new ArrayList<Item>();
		packedItems = new ArrayList<String>();
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

		// fill arrays with appropriate values
		int counter = 0;
		for (Item i : sc.getItems()) {
			itemsList.add(i);
			names[counter] = i.getName();
			items[counter][0] = i.getWeight();
			items[counter][1] = i.getValue();
			ratios[counter] = i.getRatio();
			counter++;
		}

	}

	// wrapper method to start backtracking with the version specified by a user
	// input integer
	public int startBacktrack(int backtrackVersion) {
		packedItemsString = "";
		long start;
		long end;
		int value;
		switch (backtrackVersion) {
		case 1:
			start = System.nanoTime();
			value = backtrackA(maxWeight, this.items, itemsList.size());
			end = System.nanoTime();
			elapsedTime = end - start;
			return value;
		case 2:
			start = System.nanoTime();
			value = backtrackB(maxWeight, itemsList, itemsList.size());
			end = System.nanoTime();
			elapsedTime = end - start;
			return value;
		case 3:
			start = System.nanoTime();
			value = backtrackM(size - 1, maxWeight, items);
			end = System.nanoTime();
			elapsedTime = end - start;
			return value;
		case 4:
			sortItemsByRatio();
			start = System.nanoTime();
			value = backtrackG(maxWeight, items);
			end = System.nanoTime();
			elapsedTime = end - start;
			return value;
		default:
			return -1;
		}
	}

	public int backtrackA(int capacity, int[][] items, int amount) {
		if (amount == 0 || capacity == 0)
			return 0;

		if (items[amount - 1][0] > capacity)
			return backtrackA(capacity, items, amount - 1);
		else
			return max(items[amount - 1][1] + backtrackA(capacity - items[amount - 1][0], items, amount - 1),
					backtrackA(capacity, items, amount - 1));
	}

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
		 * has already been calculated.
		 * 
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

	// greedy knapsack packing based on weight-to-value ration
	// this finds a good approximation of the optimal value rather fast, but not
	// necessarily the optimal value itself
	// As this method is an iterative one, a list of packed items is easily made
	public int backtrackG(int capacity, int[][] items) {
		int currentWeight = 0;
		int counter = 0;
		int value = 0;
		// iterative check whether the capacity has been reached and if the item
		// on top of the unchecked part of the list still fits into the knapsack
		// if said item still fits into the knapsack, prior sorting of the
		// entire itemsList by decreasing ratio ensures that the item in
		// question is the next best item by ratio
		while (currentWeight <= capacity && counter < ratios.length) {
			// if the item fills the capacity to max, return the collected value
			if (currentWeight + items[counter][0] == capacity) {
				packedItemsString += "\n" + names[counter];
				return value += items[counter][1];
				// if the item doesn't fill the capacity, add its value and its
				// weight
			} else if (currentWeight + items[counter][0] < capacity) {
				packedItemsString += "\n" + names[counter];
				currentWeight += items[counter][0];
				value += items[counter][1];
				counter++;
				continue;
			}
			counter++;
		}
		return value;
	}

	// convenience method to calculate the maximum of two values
	public int max(int a, int b) {
		return (a >= b) ? a : b;
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

	// method to be called before backtracking with greedy-by-ratio
	// convenience method to make sure the itemsList is sorted by ratio in
	// descending order
	public void sortItemsByRatio() {
		itemsList.sort(Comparator.comparing(Item::getRatio));
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

}
