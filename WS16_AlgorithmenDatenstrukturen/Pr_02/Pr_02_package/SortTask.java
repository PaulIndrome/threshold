package Pr_02_package;

import vorgaben.praktikum2.DoubleSorter;
import vorgaben.praktikum2.DoubleSorterTask;

public class SortTask implements DoubleSorterTask {

	private DoubleSorter sorter;
	private double[] array;
	
	@Override
	public void doTask() {
		sorter.sort(array);
	}

	@Override
	public void initialize(int arg0) {
		this.array = new double[arg0];
		for(int i=0;i<array.length;i++){
			array[i] = Math.random();
		}
	}

	@Override
	public void setSorter(DoubleSorter arg0) {
		this.sorter = arg0;
	}

}
