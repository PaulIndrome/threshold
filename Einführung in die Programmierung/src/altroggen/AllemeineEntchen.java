package altroggen;

public class AllemeineEntchen {
	
	public static void main(String[] args){

	String lied[][] = {
			{"Alle", "meine", "Entchen"}, 
			{"schwimmen", "auf", "dem", "See"}
		};
	
	for (int i=0;i<lied.length;i++){
		for(int j=0; j<lied[i].length;j++){
			System.out.print(lied[i][j] + " ");
		}
		System.out.println();
	}

	}
}
