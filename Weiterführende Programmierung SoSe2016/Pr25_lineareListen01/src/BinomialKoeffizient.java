
public class BinomialKoeffizient {

	public int n;
	public int k;

	public BinomialKoeffizient(int n, int k) {
		this.n = n;
		this.k = k;
	}

	public String toString() {
		return "(" + n + "|" + k + ")";
	}

	public static void main(String[] args){
		
		ObjectStack problemstapel = new ObjectStack();
		//BinomialKoeffizient newBino = new BinomialKoeffizient(5,3);
		problemstapel.push(new BinomialKoeffizient(5,3));
		
		int Ergebnis = 0;
		
		while(!problemstapel.isEmpty()){
			BinomialKoeffizient oberst = (BinomialKoeffizient) problemstapel.pop();
			if((oberst.k==0) || oberst.k==oberst.n){
				Ergebnis += 1;
			} else {
				problemstapel.push(new BinomialKoeffizient(oberst.n-1,oberst.k-1));
				problemstapel.push(new BinomialKoeffizient(oberst.n-1, oberst.k));
			}
		}
		System.out.println(Ergebnis);
	}
}
