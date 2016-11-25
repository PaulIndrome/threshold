package brotcrunsher_classes;

public class Mensch {

	int alter;
	int iq;
	String name;
	String haarfarbe;
	
	private static int anzahlMenschen = 0;

	static int getAnzahlMenschen() {
		return anzahlMenschen;
	}

	void bildung() {
		iq += 5;
	}

	void bildung(int wieviel) {
		iq += wieviel;
	}
	
	Mensch(int alter, int iq, String name, String haarfarbe) {
		this.alter = alter;
		this.iq = iq;
		this.name = name;
		this.haarfarbe = haarfarbe;

		anzahlMenschen++;
	}

	
	@Override
	public String toString() {
		return String.format("Name: %s\nAlter: %s\nIQ: %s\nHaarfarbe: %s\n",
				name, alter, iq, haarfarbe);

	}
	
	int getAlter() {
		return alter;
	}
	
	void setAlter(int alter){
		this.alter = alter;
	}
}
