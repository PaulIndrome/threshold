package Uebungsklausur;

public class Quader {
	
	private double breite;
	private double tiefe;
	private double hoehe;
	
	public Quader(){
		breite = 1;
		tiefe = 1;
		hoehe = 1;
	}
	
	public Quader(double b, double t, double h){
		breite = b;
		tiefe = t;
		hoehe = h;
	}
	
	public int getTiefe(){
		return (int)tiefe;
	}
	
	public int getBreite(){
		return (int)breite;
	}
	
	public void setBreite(int b){
		breite = b;
	}
	
	public int getHoehe(){
		return (int)hoehe;
	}
	
	public void setHoehe(int h){
		hoehe = h;
	}
	
	public boolean equals(Quader q){
		if((int)breite==q.getBreite() && (int)hoehe==q.getHoehe() && (int)tiefe==q.getTiefe()){
			return true;
		}
		else{return false;}
	}
	
	public static boolean vergleiche(Quader q1, Quader q2){
		if (q1.getBreite()==q2.getBreite()){
			if (q1.getHoehe()==q2.getHoehe()){
				if (q1.getTiefe()==q2.getTiefe()){
					return true;
				}
				else{return false;}
			}
			else{return false;}
		}
		else{return false;}
	}
	
	public String toString(){
		return ("Breite:"+ breite + "  Hoehe:" + hoehe + "  Tiefe:" + tiefe);
	}
	
	public double berechneVolumen(){
		return hoehe*breite*tiefe;
	}
	
	public double getOberflaechenInhalt(){
		return 2*(breite*hoehe + breite*tiefe + hoehe*tiefe);
	}
	
	public static double berechneRaumdiagonale(Quader q){
		return Math.sqrt( (q.getBreite()*q.getBreite()) + (q.getHoehe()*q.getHoehe()) + (q.getTiefe()*q.getTiefe()));
	}
	
	public static void main(String[] args){
		Quader Ulf = new Quader(3,4,5);
		Quader Herm = new Quader();
		
		System.out.println(Ulf.toString());
		
		System.out.println(Ulf.equals(Herm));
		
		Quader AndererUlf = new Quader(3,4,5);
		
		System.out.println(vergleiche(Ulf, AndererUlf));
	}
}
