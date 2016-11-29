package rowOfFourGame;

public class Delta {
	
	private int colD;
	private int rowD;
	private int matches;
	
	public Delta(int colD, int rowD){
			this.colD = colD;
			this.rowD = rowD;
			this.matches = 0;
	}

	public int colD() {
		return colD;
	}

	public int rowD() {
		return rowD;
	}
	
	public int matches(){
		return matches;
	}
	
	public void match(){
		this.matches++;
	}
	
	public void clear(){
		this.matches = 0;
	}
}
