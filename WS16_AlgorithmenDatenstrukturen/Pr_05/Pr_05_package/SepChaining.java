package Pr_05_package;

import vorgaben.praktikum5.Cell;
import vorgaben.praktikum5.CellAccessDictionary;

public class SepChaining implements CellAccessDictionary {

	private int size;
	private Cell[] dates;

	@Override
	public boolean delete(String key) {
		Cell c1 = dates[reducedHashcode(key)];
		while (c1 != null) {
			if (c1.getKey() == key) {
				c1 = c1.getNext();
				return true;
			} else {
				c1 = c1.getNext();
			}
		}
		return false;
	}

	@Override
	public void initialize(int size) {
		this.size = size;
		dates = new Cell[size];
	}

	@Override
	public void insert(String key, String info) {
		int keyHash = reducedHashcode(key);
		Cell c = dates[keyHash];
		if (c == null) {
			c = new Cell();
			c.setKey(key);
			c.setInfo(info);
			dates[keyHash] = c;
		} else if (c.getKey() == key) {
			c.setInfo(info);
		} else {
			while (c.hasNext()) {
				c = c.getNext();
				if (c.getKey() == key) {
					c.setInfo(info);
					return;
				}
			}
			Cell cNew = new Cell();
			cNew.setKey(key);
			cNew.setInfo(info);
			c.setNext(cNew);
		}

	}

	@Override
	public String read(String key) {
		Cell c = dates[reducedHashcode(key)];
		while (c != null) {
			if (c.getKey() == key) {
				return c.getInfo();
			} else {
				c = c.getNext();
			}
		}
		return null;
	}

	@Override
	public Cell[] getCells() {
		return dates;
	}

	private int reducedHashcode(String s) {
		return Math.abs(s.hashCode()) % size;
	}
}
