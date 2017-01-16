package backtracking;

public class Combination {

	private int value;
	private int weight;
	private String packedItems;
	
	public Combination(){
		value = 0;
		weight = 0;
		packedItems = "";
	}
	
	public Combination(int value, int weight, String packedItems){
		this.value = value;
		this.weight = weight;
		this.packedItems = packedItems;
	}
	
	public void addItem(int value, int weight, String name){
		this.value += value;
		this.weight += weight;
		packedItems += "\n\t" + name + "  :\t\tW" + weight + "\t\tV" + value;
	}
	
	
	
	
}
