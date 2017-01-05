package backtracking;

import application.Item;
import application.StuffCollection;

public class Backtracking {

	private int maxWeight;
	private String[] names;
	private int[] weights;
	private int[] values;
	private double[] ratios;

	// this iteration of Backtracking is dependant on a collection of stuff
	// generated via an application
	public Backtracking(StuffCollection sc, int MaxWeight) {
		this.maxWeight = MaxWeight;
		
		// check size of StuffCollection
		int size = sc.getSize();
		
		//instantiate arrays with appropriate size
		names = new String[size];
		weights = new int[size];
		values = new int[size];
		ratios = new double[size];
		
		//fill arrays with appropriate values
		int counter = 0;
		for (Item i : sc.getItems()) {
			names[counter] = i.getName();
			weights[counter] = i.getWeight();
			values[counter] = i.getValue();
			ratios[counter] = i.getRatio();
			counter++;
		}

		
		
		
	}
	
	

}
