package Pr_04_package;

import vorgaben.praktikum4.GeneralNode;
import vorgaben.praktikum4.GeneralTree;

public class Trie implements GeneralTree {

	private GeneralNode root = new Node();

	@Override
	public boolean delete(String arg0) {
		if (exists(arg0)) {
			getNode(arg0, false).setInfo(null);
			return true;
		} else
			return false;
	}

	@Override
	public boolean exists(String arg0) {
		try {
			return getNode(arg0, false).getInfo() != null;
		} catch (RuntimeException e) {
			return false;
		}
	}

	@Override
	public GeneralNode getNode(String arg0, boolean arg1) {
		GeneralNode current = root;
		for (char c : arg0.toCharArray()) {
			if (current.hasNextNode(c)) {
				current = current.getNextNode(c);
			} else {
				if (arg1) {
					current.setNextNode(c, new Node());
					current = current.getNextNode(c);
				} else {
					throw new RuntimeException("Gesuchter Wert nicht vorhanden und nicht erzeugt.");
				}
			}
		}
		return current;

	}

	@Override
	public void insert(String arg0, String arg1) {
		getNode(arg0, true).setInfo(arg1);
	}

	@Override
	public String read(String arg0) {
		if (exists(arg0))
			return getNode(arg0, false).getInfo();
		else
			return null;
	}

}
