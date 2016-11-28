package praktikum02;

public class Punkt{

	private double x;
	private double y;
	
	public Punkt(double xwert, double ywert){
		x = xwert; //Konstruktor schreibt die Variablen in x und y Wert der jeweiligen Instanzierung
		y = ywert;
	}
	
	public Punkt(){ //Konstruktor mit gleichem Namen wie oben, erzeugt aber bei Weglassen von Variablen einen Punkt in 0,0
		x = 0;
		y = 0;
	}
	
	public double getx(){
		return x; //gibt x Wert der entsprechenden Instanzierung aus bzw. stellt ihn zur Verf�gung
	}

	public double gety(){
		return y; //gibt y Wert der entsprechenden Instanzierung aus bzw. stellt ihn zur Verf�gung
	}
	
	public void versetzen(double xneu, double yneu){
		x = xneu; //versetzen des instanzierten Punktes an neue Koordinaten
		y = yneu;
	}
	
	public void verschieben(double dx, double dy){
		x += dx; //versetzen des instanzierten Punktes um angegebene Variablen
		y += dy;
	}
	
	public String toString(){
		return "( " + x + " | " + y + " )";
	}
	
	public boolean equals(Punkt p){ //gibt nur true aus, wenn Punkte �bereinstimmen 
		return (x == p.x) && (y == p.y); //equals (Punkt p) bedeutet, die Instanz, f�r die "equals" ausgef�hrt wird, ben�tigt als Variable eine weitere Instanz der Punkt Klasse und gibt ihr den Platzhalter "p". 
	// der erste x/y Wert geh�rt zu der Instanz, f�r die "equals" ausgef�hrt wird, der zweite Wert p.x/p.y geh�rt zur Instanz, die als Variable angegeben ist und die die Bezeichnung "p" bekommen hat
	}

	public void spiegeln (Punkt mp){
		mp.x = mp.x - 2 * mp.x;
		mp.y = mp.y - 2 * mp.y;
	}
	
	public double distancezero(Punkt zp){ //Abstand eines Punktes zum Nullpunkt
		double distancezero = Math.sqrt((zp.x * zp.x) + (zp.y * zp.y));
		return distancezero;
	}
	
	public double degreex(Punkt dp){ //Winkel einer Punktgeraden vom Nullpunkt zur x-Achse ... hehe, "dp"... hehe...
		double degreex = Math.toDegrees(Math.atan(dp.gety() / dp.getx()));
		return degreex;
	}
}
