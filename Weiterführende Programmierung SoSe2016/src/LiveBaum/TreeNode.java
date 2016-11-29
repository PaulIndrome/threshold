package LiveBaum;

public class TreeNode {
	private Object data;
	private TreeNode links;
	private TreeNode rechts;

	public TreeNode(Object data, TreeNode links, TreeNode rechts) {
		this.data = data;
		this.links = links;
		this.rechts = rechts;
	}

	public TreeNode(Object data) {
		this(data, null, null);
	}

	public Object getData() {
		return this.data;
	}

	public TreeNode getLinkerTeilbaum() {
		return this.links;
	}

	public TreeNode getRechterTeilbaum() {
		return this.rechts;
	}

	public void setLinkerTeilbaum(TreeNode links) {
		this.links = links;
	}

	public void setRechterTeilbaum(TreeNode rechts) {
		this.rechts = rechts;
	}

	public boolean equals(TreeNode node) {
		if ((Integer) this.data > (Integer) node.getData()) {
			return true;
		}
		return false;
	}

}
