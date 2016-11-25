package gameFX;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller-class for the MainEditor-class of "Deep Space Dweller".
 * 
 * @author Alexander Thomas Kühn
 *
 */
public class EditorController {

	//declaration of the Systems-Object used in the Controller
	private Systems alpha;
	//declaration of the Planet-Objects used in the Controller
	private Planet planetA;
	private Planet planetB;
	
	//declaration of the FXML-elements that need to be addressed
	@FXML
	TextField txfSysName;
	@FXML
	TextField txfSysConnX;
	@FXML
	TextField txfSysConnY;
	@FXML
	TextField txfNamePlanet1;
	@FXML
	TextField txfNamePlanet2;
	@FXML
	TextField txfGroessePlanet1;
	@FXML
	TextField txfGroessePlanet2;
	@FXML
	TextArea txaHistoryPlanet1;
	@FXML
	TextArea txaHistoryPlanet2;
	@FXML
	Label lblStatus;
	
	
	/**
	 * Clears all TextFields and Areas of the editor-window.
	 */
	@FXML
	public void clearAll(){
		txfSysName.clear();
		txfSysConnX.clear();
		txfSysConnY.clear();
		txfNamePlanet1.clear();
		txfNamePlanet2.clear();
		txfGroessePlanet1.clear();
		txfGroessePlanet2.clear();
		txaHistoryPlanet1.clear();
		txaHistoryPlanet2.clear();
	}
	
	
	/**
	 * This method takes the information edited by the user, creating an Systems-object.
	 * After the creation of the Systems-object, the object is saved.
	 * The name of the saved file is -value of Systems.name-.sdatei.
	 * All files are found in the /systeme-directory.
	 * The method returns a int-value depending on possible errors that can occur.
	 * <ul>
	 * <li>0 equals that no errors occurred.
	 * <li>-1 equals an error during the creation of the Systems-object, due to a parsing-error or no value found in txfSysName.
	 * <li>-2 equals an error while saving the Systems-object.
	 * </ul>
	 * 
	 * @return	a int-value ranging from 0 to -2
	 */
	@FXML
	public int saveSystems(){
		//instantiation of the Systems-Object
		alpha = new Systems();
		
		//checks if txfSysName has any content in it, if not the Object would be saved without a name
		//this would result in the file ".sdatei"
		if (txfSysName.getText().equals("")){
			//updates lblStatus to alert the user to the missing name
			lblStatus.setText("System muss einen Namen haben.");
			//value returns when a minor error occurs
			return -1;
		}
		
		//checks if txfSysConnX and txfSysConnY have any content
		if ((txfSysConnX.getText().equals(""))&&(txfSysConnY.getText().equals(""))){
			//updates lblStatus to alert the user to the missing connections
			lblStatus.setText("System braucht Verbindung zu anderen Systemen.");
			//value returns when a minor error occurs
			return -1;
		}
		
		//checks if txfNamePlanet1, txfGroessePlanet1 and txaHistoryPlanet1 have any content
		if ((txfNamePlanet1.getText().equals(""))&&(txfGroessePlanet1.getText().equals(""))&&(txaHistoryPlanet1.getText().equals(""))){
			//updates lblStatus to alert the user to the missing parameters
			lblStatus.setText("Planet 1 besitzt keinerlei Angaben. Bitte Parameter eingeben.");
			//value returns when a minor error occurs
			return -1;
		}
		
		//checks if txfNamePlanet2, txfGroessePlanet2 and txaHistoryPlanet2 have any content
		if ((txfNamePlanet2.getText().equals(""))&&(txfGroessePlanet2.getText().equals(""))&&(txaHistoryPlanet2.getText().equals(""))){
			//updates lblStatus to alert the user to the missing parameters
			lblStatus.setText("Planet 2 besitzt keinerlei Angaben. Bitte Parameter eingeben.");
			//value returns when a minor error occurs
			return -1;
		}
		
		//sets the name and the connections by gathering the content of the TextFields
		alpha.setName(txfSysName.getText());
		alpha.setConnection(txfSysConnX.getText(), txfSysConnY.getText());
		
		//outer try-catch to deal with the possible parsing-errors
		try{
			//String from TextField is parsed into double-values, Planet-Objects are created
			double groesseA = Double.parseDouble(txfGroessePlanet1.getText());
			planetA = new Planet(txfNamePlanet1.getText(), groesseA, txaHistoryPlanet1.getText());
		
			double groesseB = Double.parseDouble(txfGroessePlanet2.getText());
			planetB = new Planet(txfNamePlanet2.getText(), groesseB, txaHistoryPlanet2.getText());
			
			//checks if txaHistoryPlanet1 and txaHistoryPlanet2 have any content, sets a default history
			if(txaHistoryPlanet1.getText().equals("")){
				planetA.setHistory("Ein Planet frisch aus der Planetenschmiede");
			}
			
			if(txaHistoryPlanet2.getText().equals("")){
				planetB.setHistory("Ein Planet frisch aus der Planetenschmiede");
			}
			
			//assigns the created Planet-Objects to the Systems-Object
			alpha.setPlanets(planetA, planetB);
			
			//declaration of the ObjectOutputStream
			ObjectOutputStream out;
			
			//inner try-catch to deal with possible IOExceptions
			try{
				//instantiation of the ObjectOutputStream, generated file is named after Systems.name
				out = new ObjectOutputStream(new FileOutputStream("systeme/"+alpha.getName()+".sdatei"));
				
				out.writeObject(alpha);
				
				out.close();
				
				//updates lblStatus if the saving was successful
				lblStatus.setText("System erfolgreich gespeichert.");
				
				//clears all entries to prepare the creation of further Systems-Objects
				clearAll();
				
				//value returned if no error occurred
				return 0;
				
			}catch (IOException e){	
				//updates lblStatus if a error in the saving process occurred
				lblStatus.setText("Fehler beim Speichern aufgetreten.");
				//value returned if a error in the saving/loading process occurred
				return -2;
			}
			
		}catch (Exception e){
			//updates lblStatus if a error occurs while parsing
			lblStatus.setText("Fehler beim Parsen der Größen-Werte. Vielleicht ein Komma statt einem Punkt geschrieben?");
			//value returned if a minor error occurs
			return -1;
		}
	}	
	
	
	/**
	 * This method loads existing Systems-objects.
	 * The Systems-object is found via its name, which is taken as a String-value from the txfSysName-TextField.
	 * The method returns an int-value depending on the possible errors that can occur:
	 * <ul>
	 * <li>0 equals no errors occurred.
	 * <li>-2 equals an error while loading the Systems-object.
	 * </ul>
	 * 
	 * @return a int-value of 0 or -2
	 */
	@FXML
	public int loadSystem(){
		
		//gets content from txfSysName to determine which file needs to be loaded
		String suchtext = txfSysName.getText();
		
		//declaration of the ObjectInputStream
		ObjectInputStream ois;
		
		try{
			//instantiation of the ObjectInputStream, using the String suchtext
			ois = new ObjectInputStream(new FileInputStream("systeme/"+suchtext+".sdatei"));
			
			//current Systems-Object is overwritten
			alpha = (Systems) ois.readObject();
			
			ois.close();
			
			//updates all TextFields and TextAreas with the information of the Systems-Object
			txfSysConnX.setText(alpha.getConnectionX());
			txfSysConnY.setText(alpha.getConnectionY());
			
			txfNamePlanet1.setText(alpha.getPlanetA().getName());
			txfNamePlanet2.setText(alpha.getPlanetB().getName());
			
			txfGroessePlanet1.setText(""+alpha.getPlanetA().getGroesse());
			txfGroessePlanet2.setText(""+alpha.getPlanetB().getGroesse());
			
			txaHistoryPlanet1.setText(alpha.getPlanetA().getHistory());
			txaHistoryPlanet2.setText(alpha.getPlanetB().getHistory());
			
			//updates lblStatus if the system was successfully loaded
			lblStatus.setText("System erfolgreich geladen.");
			
		}catch(Exception e){
			//updates lblStatus if an error occurred while loading the Systems-Object
			lblStatus.setText("Fehler beim Laden der Datei aufgetreten.");
			//value returned if an error in the saving/loading process occurred
			return -2;
		}
		//value returned if no error occurred
		return 0;
	}
	
}
