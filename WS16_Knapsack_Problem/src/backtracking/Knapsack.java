package backtracking;

import java.util.ArrayList;

import application.Item;

public class Knapsack {

	private ArrayList<Item> items;
	private int weightTotal = 0;
	private int valueTotal = 0;
	private int maxWeight;

	public Knapsack(int MaxWeight) {
		this.maxWeight = MaxWeight;
		items = new ArrayList<Item>();
	}

	public boolean add(Item i) {
		if (weightTotal + i.getWeight() <= maxWeight) {
			items.add(i);
			weightTotal += i.getWeight();
			valueTotal += i.getValue();
			return false;
		} else
			//true means knapsack is full
			return true;
	}

}
