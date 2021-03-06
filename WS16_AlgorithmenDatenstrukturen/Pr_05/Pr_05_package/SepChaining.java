package Pr_05_package;

import vorgaben.praktikum5.Cell;
import vorgaben.praktikum5.CellAccessDictionary;

public class SepChaining implements CellAccessDictionary {

	private int size;
	private Cell[] dates;

	@Override
	public boolean delete(String key) {
		int hashKey = reducedHashcode(key);
		if(this.read(key) == null){
			return false;
		} else {
			if(dates[hashKey].getKey() == key){
				dates[hashKey] = dates[hashKey].getNext();
				return true;
			} else {
				Cell current = dates[hashKey].getNext();
				Cell prevCell = dates[hashKey];
				while(current.hasNext() && current.getKey()!=key){
					prevCell = current;
					current = current.getNext();
				}
				prevCell.setNext(current.getNext());
				return true;
			}
		}
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
