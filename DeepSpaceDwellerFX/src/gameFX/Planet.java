package gameFX;

import java.io.Serializable;

/**
 * Representation of a Planet.
 * 
 * @author Alexander Thomas Kühn
 *
 */
public class Planet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private double groesse;
	private String history;
	
	/**
	 * Creates a default Planet.
	 */
	public Planet(){
		name = "X";
		groesse = 100.0;
		history = "Ein generischer Standartplanet, wie er in jedem zweitklassigen SciFi-Film vorkommt.";
	}
	
	/**
	 * Creates a completely customizable Planet. 
	 * 
	 * @param nameNeu		Name of the Planet
	 * @param groesseNeu	Size of the Planet
	 * @param historyNeu	History or characteristics of the Planet.
	 */
	public Planet(String nameNeu, double groesseNeu, String historyNeu){
		name = nameNeu;
		groesse = groesseNeu;
		history = historyNeu;
	}
	
	//SET ------------------------------------------------------------------------------------------------------
	
	/**
	 * Sets the Name of the Planet.
	 * 
	 * @param nameNeu Name of the Planet
	 */
	public void setName(String nameNeu){
		name = nameNeu;
	}
	
	/**
	 * Sets the Size of the Planet.
	 * 
	 * @param groesseNeu Size of the Planet
	 */
	public void setGroesse(int groesseNeu){
		groesse = groesseNeu;
	}
	
	/**
	 * Sets the History and the Characteristics of the Planet.
	 * 
	 * @param historyNeu History and Characteristics of the Planet
	 */
	public void setHistory(String historyNeu){
		history = historyNeu;
	}
	
	//GET ------------------------------------------------------------------------------------------------------
	
	/**
	 * Returns the Name of the Planet.
	 * 
	 * @return String, containing the name of the Planet
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the Size of the Planet.
	 * 
	 * @return	double
	 */
	public double getGroesse(){
		return groesse;
	}
	
	/**
	 * Returns the history and characteristics of the Planet.
	 * 
	 * @return	String, containing the history and characteristics of the Planet
	 */
	public String getHistory(){
		return history;
	}
	
}
