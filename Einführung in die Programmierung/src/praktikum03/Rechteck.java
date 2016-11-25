package praktikum03;

public class Rechteck {

	private Punkt startpunkt;
	private double hoehe;
	private double breite;
	
	public Rechteck (Punkt start){
		startpunkt = start;
		System.out.println("Bitte trage die Hoehe des Rechtecks ein.");
		hoehe = Keyboard.readdouble();
		System.out.println("Bitte trage die Breite des Rechtecks ein.");
		breite = Keyboard.readdouble();
	}
	
	public Rechteck (double xstart, double ystart, double h, double b){
		startpunkt = new Punkt(xstart, ystart);
		hoehe = h;
		breite = b;
	}
	
	public Punkt getStartpunkt(){
		return startpunkt;
	}
	
	public double getHoehe(){
		return hoehe;
	}
	
	public double getBreite(){
		return breite;
	}
	
	public double berechneFlaeche(){
		return (breite*hoehe);
	}
	
	public double berechneUmfang(){
		return (breite*2 + hoehe*2);
	}
	
	public void versetzen(double xNeu, double yNeu){
		startpunkt.versetzen(xNeu, yNeu);
	}
	
	public void verschieben(double dx, double dy){
		startpunkt.verschieben(dx, dy);
	}
	
	public String toString(){
		return ("Dies ist ein Rechteck mit Hoehe=" + hoehe + " und Breite=" + breite + " und dem Startpunkt " + startpunkt.toString() + ".");
	}
	
	public boolean equals(Rechteck r){
		return ((hoehe == r.hoehe) && (breite == r.breite) && (getStartpunkt().getx() == r.getStartpunkt().getx()) && (getStartpunkt().gety() == r.getStartpunkt().gety()));
	}
	
	public void hoehebreiteangleichen(double anderesrechteckh, double anderesrechteckb){
		hoehe = anderesrechteckh;
		breite = anderesrechteckb;
	}
}

