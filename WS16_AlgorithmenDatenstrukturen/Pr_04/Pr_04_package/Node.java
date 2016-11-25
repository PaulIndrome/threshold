package Pr_04_package;

import vorgaben.praktikum4.GeneralNode;

public class Node implements GeneralNode {

	private String info;
	private final GeneralNode[] map = new Node[127];
	
	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public GeneralNode getNextNode(char arg0) {
		return map[arg0];
	}

	@Override
	public boolean hasNextNode(char arg0) {
		return map[arg0] != null;
	}

	@Override
	public void setInfo(String arg0) {
		this.info = arg0;
	}

	@Override
	public void setNextNode(char arg0, GeneralNode arg1) {
		map[arg0] = arg1;
	}

}
