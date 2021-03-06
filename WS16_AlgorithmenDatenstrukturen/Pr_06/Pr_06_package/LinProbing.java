package Pr_06_package;

import vorgaben.praktikum5.Cell;
import vorgaben.praktikum6.CellAccessDictionary;

public class LinProbing implements CellAccessDictionary {

	private Cell[] dates;
	private int size;

	@Override
	public void initialize(int arg0) {
		size = arg0;
		dates = new Cell[size];
	}

	@Override
	public void insert(String key, String info) {
		int keyHash = reducedHashcode(key);
		int s = keyHash;
		boolean repeat = false;
		while (s < size) {
			if (dates[s] == null) {
				Cell c = new Cell();
				c.setKey(key);
				c.setInfo(info);
				dates[s] = c;
				return;
			} else if (dates[s].getKey() == key) {
				Cell c = new Cell();
				c.setKey(key);
				c.setInfo(info);
				dates[s] = c;
				return;
			}
			s++;
			if (s == size) {
				s = 0;
				if (repeat == true) {
					throw new RuntimeException("Datenstruktur voll");
				}
				repeat = true;
			}
		}
	}

	@Override
	public String read(String key) {
		int s = reducedHashcode(key);
		int original = s;
		Cell c = dates[s];
		boolean repeat = false;
		while (c == null || c.getKey()!=key) {
			s++;
			if(s==size || s == original){
				if(repeat){
					return null;
				}
				s=0;
				repeat = true;
			}
			c = dates[s];
		}
		return c.getInfo();
	}

	@Override
	public Cell[] getCells() {
		return dates;
	}

	private int reducedHashcode(String s) {
		return Math.abs(s.hashCode()) % size;
	}

}
