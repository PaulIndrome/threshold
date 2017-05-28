package application;

import javafx.beans.property.SimpleDoubleProperty;

public class Model {

	
	
	double zielWert;

	double kartenTeilerH;
	double kartenTeilerM;
	double kartenTeilerL;

	double kartenWertH;
	double kartenWertM;
	double kartenWertL;
	
	SimpleDoubleProperty kartenWertHProperty = new SimpleDoubleProperty();
	SimpleDoubleProperty kartenWertMProperty = new SimpleDoubleProperty();
	SimpleDoubleProperty kartenWertLProperty = new SimpleDoubleProperty();
	
	int anzahlTests;
	
	int kartenMengeH;
	int kartenMengeM;
	int kartenMengeL;

	// used to recreate a deck setup from a loaded file containing a String
	public Model(String loaded){
		
	}
	
	public Model() {
		// empty constructor for program loadup
	}

	public void berechneKartenWerte() {
		kartenWertH = zielWert / kartenTeilerH;
		kartenWertHProperty.set(kartenWertH);
		
		kartenWertM = kartenWertH / kartenTeilerM;
		kartenWertMProperty.set(kartenWertM);
		
		kartenWertL = kartenWertM / kartenTeilerL;
		kartenWertLProperty.set(kartenWertL);
	}

	public void updateAnyTextField(String newValue, String tFID) {
		if (newValue.isEmpty() || tFID.isEmpty())
			return;
		
		switch (tFID) {
		case "aT":
			anzahlTests = Integer.parseInt(newValue);
			break;
		case "zW":
			newValue = newValue.replace(',', '.');
			zielWert = Double.parseDouble(newValue);
			break;
		case "kM0":
			kartenMengeH = Integer.parseInt(newValue);
			break;
		case "kM1":
			kartenMengeM = Integer.parseInt(newValue);
			break;
		case "kM2":
			kartenMengeL = Integer.parseInt(newValue);
			break;
		case "kT0":
			newValue = newValue.replace(',', '.');
			kartenTeilerH = Double.parseDouble(newValue);
			berechneKartenWerte();
			break;
		case "kT1":
			newValue = newValue.replace(',', '.');
			kartenTeilerM = Double.parseDouble(newValue);
			berechneKartenWerte();
			break;
		case "kT2":
			newValue = newValue.replace(',', '.');
			kartenTeilerL = Double.parseDouble(newValue);
			berechneKartenWerte();
			break;
		default:
		}
	}
	
	

}
