package backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import application.Item;
import application.StuffCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Backtracking {

	private int maxWeight;
	private String[] names;
	private int[][] items;
	// private int[] values;
	private double[] ratios;
	private ArrayList<Item> itemsList;
	private ArrayList<Knapsack> knapsacks;
	private long elapsedTime;
	private ArrayList<String> packedItems;
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

	public int startBacktrack(int backtrackVersion) {
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

	public int backtrackM(int amount, int capacity, int[][] items) {
		int take = 0, dontTake = 0;

		if (matrix[amount][capacity] != 0)
			return matrix[amount][capacity];

		if (amount == 0) {
			if (items[0][0] <= capacity) {
				matrix[amount][capacity] = items[0][1];
				return items[0][1];
			} else {
				matrix[amount][capacity] = 0;
				return 0;
			}
		}

		if (items[amount][0] <= capacity)
			take = items[amount][1] + backtrackM(amount - 1, capacity - items[amount][0], items);
		dontTake = backtrackM(amount - 1, capacity, items);
		matrix[amount][capacity] = max(take, dontTake);
		return matrix[amount][capacity];
	}

	public int backtrackG(int capacity, int[][] items) {
		int currentWeight = 0;
		int counter = 0;
		int value = 0;
		while (currentWeight <= capacity && counter < ratios.length) {
			if (currentWeight + items[counter][0] == capacity) {
				return value += items[counter][1];
			} else if (currentWeight + items[counter][0] < capacity) {
				value += items[counter][1];
				counter++;
				continue;
			} else {
				counter++;
				continue;
			}
		}
		return value;
	}

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
		return packedItems.toString();
	}

	public void sortItemsByRatio() {
		for (Item i : itemsList) {
			System.out.println(i.getRatio());
		}
		Comparator c = new Comparator<Item>() {
			public int compare(Item i1, Item i2) {
				Double i1r = i1.getRatio();
				Double i2r = i1.getRatio();
				return (int) Math.signum(i1r-i2r);
			}
		};
		Collections.sort(itemsList, c);
		for (Item i : itemsList) {
			System.out.println(i.getRatio());
		}

	}

}
