package fehler;

public class Taschenrechner {
	private int zahl1;
	private int zahl2;

	public Taschenrechner(int zahl1, int zahl2) {
		this.zahl1 = zahl1;
		this.zahl2 = zahl2;
	}

	public void berechneQuadratAusZahl1(){
		System.out.println(Math.pow(zahl1,2));
	}
	public void berechneQuadratAusZahl2(){
		System.out.println(Math.pow(zahl2,2));
	}
	public void berechneZahl1DurchZahl2(){
		System.out.println(zahl1/zahl2);
	}
	public void berechneWurzel(){
		System.out.println("Wurzel Zahl 1: " + Math.sqrt((double)zahl1));
		System.out.println("Wurzel Zahl 2: " + Math.sqrt((double)zahl2));
	}
}
