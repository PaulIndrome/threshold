package Pr_02_package;

import vorgaben.praktikum2.SelectiveDoubleSorter;

public class SelectionSort implements SelectiveDoubleSorter {

	@Override
	public void sort(double[] zahlen) {
		for(int i = 0; i<=zahlen.length-1;i++){
			int minPos = findMin(zahlen, i);
			if(minPos != i){
				double hilf = zahlen[i];
				zahlen[i] = zahlen[minPos];
				zahlen[minPos] = hilf;
			}
		}
	}

	@Override
	public int findMin(double[] zahlen, int pos) throws IllegalArgumentException {
		if(zahlen == null || pos < 0 || pos >= zahlen.length || zahlen.length == 0){
			throw new IllegalArgumentException();
		}
		int minPos = pos;
		for(int i = pos+1;i<zahlen.length;i++){
			if(zahlen[i] < zahlen[minPos]){
				minPos = i;
			}
		}
		return minPos;
	}
	

}
