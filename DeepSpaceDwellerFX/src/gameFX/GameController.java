package gameFX;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;


/**
 * Controller-class for the MainGame-class of "Deep Space Dweller".
 * 
 * @author Alexander Thomas Kühn
 *
 */
public class GameController {

	//declaration of Systems-Object used in the Controller
	private Systems beta;
	
	//declaration of the FXML-elements that need to be addressed
	@FXML
	RadioButton rbtnPlanetA;
	
	@FXML
	RadioButton rbtnPlanetB;
	
	@FXML
	Button btnConnX;
	
	@FXML
	Button btnConnY;
	
	@FXML
	Label lblGameStatus;
	
	@FXML 
	Label lblGroessePlanet;
	
	@FXML
	Label lblHistoryPlanet;
	
	/**
	 * Loads the initial Systems-Object.
	 * 
	 * @return an int-value of 0 or -1, depending on the occurrence of an error.
	 */
	@FXML
	public int loadInitialGameSystems(){
		
		//declaration of the ObjectInputStream
		ObjectInputStream ois;
		
		try{
			//instantiation of the ObjectInputStream to load to the initial Systems-Object Solaris
			ois = new ObjectInputStream(new FileInputStream("systeme/Solaris.sdatei"));
			
			//current Systems-object is overwritten
			beta = (Systems) ois.readObject();
			
			ois.close();
			
			//displaying text on the lblStatus-Label if the Object is successfully loaded
			lblGameStatus.setText("Starte Steuersysteme... überprüfe Antriebe... Systemchecklist wird durchlaufen... alle Systeme einsatzbereit.");
			
			//update the text of the Buttons with the current values of connX and connY
			btnConnX.setText(beta.getConnectionX());
			btnConnY.setText(beta.getConnectionY());
		
			//update the text of the RadioButtons with the current values of getPlanetA().getName() and getPlanetB().getName()
			rbtnPlanetA.setText(beta.getPlanetA().getName());
			rbtnPlanetB.setText(beta.getPlanetB().getName());
		
		}catch(Exception e){
			//displaying text on the lblStatus-Label if an error occurred
			lblGameStatus.setText("KRITISCHER STARTFEHLER!");
			
			e.printStackTrace();
			
			//value returned if an error occurred, method will be stopped
			return -1;
		}
		//value returned if no error occurred
		return 0;
	}
	
	/**
	 * Loads the Systems-Object, which is linked as Systems.connX.
	 * 
	 * @return an int-value of 0 or -1, depending on the occurrence of an error.
	 */
	@FXML
	public int travelToSystemsX(){
		
		//String used to get the name of the Systems-Object that needs to be loaded
		String destination = btnConnX.getText();
		
		//declaration of the ObjectInputStream
		ObjectInputStream ois;
		
		try{
			//instantiation of the ObjectInputStream to load the Systems-Object 
			ois = new ObjectInputStream(new FileInputStream("systeme/"+destination+".sdatei"));
			
			//current Systems-Object is overwritten
			beta = (Systems) ois.readObject();
			
			ois.close();
			
			//displaying text on the lblStatus-Label if the Systems-Object was successfully loaded 
			lblGameStatus.setText("Angekommen im System: "+destination);;
			
			//update the text of the Buttons with the current values of connX and connY
			btnConnX.setText(beta.getConnectionX());
			btnConnY.setText(beta.getConnectionY());

			//update the text of the RadioButtons with the current values of getPlanetA().getName() and getPlanetB().getName()
			rbtnPlanetA.setText(beta.getPlanetA().getName());
			rbtnPlanetB.setText(beta.getPlanetB().getName());
			
		}catch(Exception e){
			//displaying text on the lblStatus-Label if an error occurred
			lblGameStatus.setText("Sprung zum angegebenen System kann nicht berechnet werden. Eine alternative Route wird empfohlen.");
			//value returned if an error occurred, method will be stopped
			return -1;
		}
		//value returned if no error occurred
		return 0;
	}

	/**
	 * Loads the Systems-object, which is linked as System.connY.
	 * 
	 * @return an int-value of 0 or -1, depending on the occurrence of an error.
	 */
	@FXML
	public int travelToSystemsY(){
		
		//String used to get the name of the Systems-Object that needs to be loaded
		String destination = btnConnY.getText();
		
		//declaration of the ObjectInputStream
		ObjectInputStream ois;
		
		try{
			//displaying text on the lblStatus-Label if the Systems-Object was successfully loaded 
			ois = new ObjectInputStream(new FileInputStream("systeme/"+destination+".sdatei"));
			
			//current Systems-Object is overwritten
			beta = (Systems) ois.readObject();
			
			ois.close();
			
			//displaying text on the lblStatus-Label if the Systems-Object was successfully loaded 
			lblGameStatus.setText("Angekommen im System: "+destination);;
			
			//update the text of the Buttons with the current values of connX and connY
			btnConnX.setText(beta.getConnectionX());
			btnConnY.setText(beta.getConnectionY());
			
			//update the text of the RadioButtons with the current values of getPlanetA().getName() and getPlanetB().getName()
			rbtnPlanetA.setText(beta.getPlanetA().getName());
			rbtnPlanetB.setText(beta.getPlanetB().getName());
			
		}catch(Exception e){
			//displaying text on the lblStatus-Label if an error occurred
			lblGameStatus.setText("Sprung zum angegebenen System kann nicht berechnet werden. Eine alternative Route wird empfohlen.");
			//value returned if an error occurred, method will be stopped
			return -1;
		}
		//value returned if no error occurred
		return 0;
	}
	
	
	/**
	 * Displays the information of a planet on the game-window.
	 */
	@FXML
	public void scanPlanet(){
		
		try{
			//checks if any RadioButton is selected
			if(rbtnPlanetA.isSelected()){
				//updating lblStatus-Label
				lblGameStatus.setText("Planet "+beta.getPlanetA().getName()+" wird gescannt.");
			
				//displaying the information about the scanned Planet
				lblGroessePlanet.setText(""+beta.getPlanetA().getGroesse());
				lblHistoryPlanet.setText(beta.getPlanetA().getHistory());
			}
			else if(rbtnPlanetB.isSelected()){
				//updating lblStatus-Label
				lblGameStatus.setText("Planet "+beta.getPlanetB().getName()+" wird gescannt.");
				
				//displaying the information about the scanned Planet
				lblGroessePlanet.setText(""+beta.getPlanetB().getGroesse());			
				lblHistoryPlanet.setText(beta.getPlanetB().getHistory());
			}
			else{
				//updating lblStatus-Label if no RadioButton is selected
				lblGameStatus.setText("Bitte einen Planeten zum scannen auswählen");
			}
		}catch(Exception e){
			//updating lblStatus-Label if an error occurred
			lblGameStatus.setText("Planet kann nicht gescannt werden. Möglicherweise stören Interferenzen die Scanner.");
		}
	}
}
