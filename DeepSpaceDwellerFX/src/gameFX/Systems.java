package gameFX;

import java.io.Serializable;

/**
 * Representation of a small solar system, containing 2 Planets.
 * The solar system is linked with two other solar systems.
 * 
 * @author Alexander Thomas Kühn
 *
 */
public class Systems implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//name of the solar system
	private String name;
	//link to other Systems-Objects
	private String connX;
	private String connY;
	//Planet-Objects contained by this solar system
	private Planet a;
	private Planet b;
	
	/**
	 * Creates a empty Systems-object.
	 * The name-value is "Proxima Centauri" by default.
	 */
	public Systems(){
		name = "Proxima Centauri";
	}
	
	
	//SET ------------------------------------------------------------------------------------------------------
	/**
	 * Sets the name of a Systems-object.
	 * 
	 * @param nameNeu	new Name of the Systems-object.
	 */
	public void setName(String nameNeu){
		name = nameNeu;
	}
	
	/**
	 * Sets the connections of a Systems-object.
	 * 
	 * @param x	name of a Systems-object to which is "connected" to this Systems-object.
	 * @param y	name of a second Systems-object to which is "connected" to this Systems-object.
	 */
	public void setConnection(String x, String y){
		connX = x;
		connY = y;
	}
	
	/**
	 * Sets the Planet-objects belonging to this System.
	 * 
	 * @param aNeu	First Planet-object.
	 * @param bNeu	Second Planet-object.
	 */
	public void setPlanets(Planet aNeu, Planet bNeu){
		a = aNeu;
		b = bNeu;
	}
	
	//GET ------------------------------------------------------------------------------------------------------
	/**
	 * Returns the name of the Systems-object.
	 * 
	 * @return name of the Systems-object.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the name of a Systems-object "connected" to this Systems-object.
	 * 
	 * @return	String, containing the name of the "connected" Systems-object.
	 */
	public String getConnectionX(){
		return connX;
	}
	
	/**
	 * Returns the name of a Systems-object "connected" to this Systems-object.
	 * 
	 * @return	String, containing the name of the "connected" Systems-object.
	 */
	public String getConnectionY(){
		return connY;
	}
	
	/**
	 * Returns the first Planet-object.
	 * 
	 * @return Planet A
	 */
	public Planet getPlanetA(){
		return a;
	}
	
	/**
	 * Returns the second Planet-object
	 * 
	 * @return Planet b
	 */
	public Planet getPlanetB(){
		return b;
	}
}
