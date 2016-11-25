package Pr_03_package;

import vorgaben.praktikum3.SearchAlgorithm;

public class SequentialSearch implements SearchAlgorithm {

	@Override
	public int performSearch(String[] data, int start, int end, String target) throws IllegalArgumentException {
		if(data == null || data.length == 0 || start < 0 || end>=data.length || target == null){
			throw new IllegalArgumentException();
		}
		for(int i = start ; i<=end ; i++){
			if(target.equals(data[i])){
				return i;
			}
		}
		
		return -1;
	}

}
