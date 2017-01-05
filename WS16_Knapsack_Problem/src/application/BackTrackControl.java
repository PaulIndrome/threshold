package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;

public class BackTrackControl {

	@FXML
	private ListView<String> listview;

	@FXML
	private TableView<Item> tableview;
	@FXML
	private TableColumn<Item, String> itemCol;
	@FXML
	private TableColumn<Item, Integer> weightCol;
	@FXML
	private TableColumn<Item, Integer> valueCol;
	@FXML
	private TableColumn<Item, Double> ratioCol;

	@FXML
	private Button newStuffButton;
	@FXML
	private Button saveStuffButton;
	@FXML
	private Button randomStuffButton;
	@FXML
	private Button deleteStuffButton;

	@FXML
	private Button addStuffButton;
	@FXML
	private TextField nameField;
	@FXML
	private TextField weightField;
	@FXML
	private TextField valueField;
	@FXML
	private TextField randomAmountField;

	private ArrayList<StuffCollection> allStuffCollections;

	private ObservableList<String> listviewobslist = FXCollections.observableArrayList();
	private ObservableList<Item> tableviewobslist = FXCollections.observableArrayList();

	private Document stuffcollections;
	private NdList allStuff;
	private int currentSelectNo = -2;

	public void initialize() {
		// parsing of XML file is top priority
		parseXML("stuffsave.xml");

		// linking the TableView's datatype to an observable list containing
		// data of that type (Item)
		tableview.setItems(tableviewobslist);

		// linking the TableView columns to the datatype's (Item) properties by
		// name and datatype. JavaFX observes these properties automatically and
		// keeps them updated upon change
		itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		weightCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("weight"));
		valueCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("value"));
		ratioCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("ratio"));
		
		listview.setCellFactory(TextFieldListCell.forListView());
		
		
	}

	public void listViewClicked() {
		// establish what index is selected in ListView
		int selectNo = listview.getSelectionModel().getSelectedIndex();

		// for probable future use the selected index is stored "globally"
		// if a different index is clicked, the tableview is updated, otherwise
		// the tableview is cleared.
		// ONLY ONE COLLECTION CAN BE SELECTED AT A TIME
		if (currentSelectNo != selectNo) {
			currentSelectNo = selectNo;
			tableviewobslist.clear();

			// adding the items of the selected collection to the TableView's
			// observed list after clearing it which automatically updates the
			// view
			for (Item i : allStuffCollections.get(selectNo).getItems()) {
				tableviewobslist.add(i);
			}
			//tableviewobslist = allStuffCollections.get(selectNo).getItems();
		} else {
			tableviewobslist.clear();
			currentSelectNo = -2;
		}

	}

	public void parseXML(String xmlPath) {
		// filling the Document with all Nodes parsed from the XML
		try {
			DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
			stuffcollections = docBuild.parse(getClass().getResourceAsStream(xmlPath));
		} catch (SAXException | IOException | ParserConfigurationException | RuntimeException e) {
			e.printStackTrace();
		}
		// the ArrayList of StuffCollection type is instantiated here
		allStuffCollections = new ArrayList<StuffCollection>();

		// creation of a NodeList via NdList class that automatically eliminates
		// empty TEXT_NODEs on creation
		allStuff = new NdList(stuffcollections.getElementsByTagName("stuffcollection"));

		// Creating a new StuffCollection for each <stuffcollection> in XML
		// file.
		for (int i = 0; i < allStuff.getLength(); i++) {
			String listname = allStuff.item(i).getAttributes().getNamedItem("listname").getNodeValue();
			listviewobslist.add(listname);
			//listviewobslist.add("stuff collection " + (i + 1));
			allStuffCollections.add(new StuffCollection(allStuff.item(i), i));
		}
		listview.setItems(listviewobslist);
	}

	public void addStuff() {
		// collect data from textfields
		String name = nameField.getText();
		String weight = weightField.getText();
		String value = valueField.getText();
		// depending on the validation, perform different tasks
		switch (validateTextFields(name, weight, value)) {
		case 1:
			nameField.requestFocus();
			nameField.selectAll();
			break;
		case 2:
			weightField.requestFocus();
			weightField.selectAll();
			break;
		case 3:
			valueField.requestFocus();
			valueField.selectAll();
			break;
		default:
			// default case means validation on all three TextFields was successfull
			allStuffCollections.get(currentSelectNo).addItem(new Item(name, weight, value));
			updateTableviewobslist();
			nameField.clear();
			weightField.clear();
			valueField.clear();
			nameField.requestFocus();
		}
	}

	// clears the tableviewobslist and refills it from currently selected
	// stuffcollection
	public void updateTableviewobslist() {
		tableviewobslist.clear();
		for (Item i : allStuffCollections.get(currentSelectNo).getItems()) {
			tableviewobslist.add(i);
		}
	}

	// creates a new StuffCollection of highest index and selects it, clearing
	// the tableview
	public void newStuff() {
		String listname = "stuff collection " + (listviewobslist.size() + 1);
		listviewobslist.add(listname);
		allStuffCollections.add(new StuffCollection(listviewobslist.size(), listname));
		tableviewobslist.clear();
		listview.requestFocus();
		currentSelectNo = listviewobslist.size() - 1;
		listview.getSelectionModel().clearAndSelect(currentSelectNo);
	}

	// saves all current StuffCollections to an unschemed XML file
	public void saveStuff() {
		Document saveDoc;
		try {
			DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
			// Document documentToSave =
			// docBuild.parse(getClass().getResourceAsStream("stuff.xml"));
			saveDoc = docBuild.newDocument();

			// create root element called "stuff"
			Element root = saveDoc.createElement("stuff");

			// main building block of xml file, creates <stuffcollection>
			// element for each StuffCollection and separate <item> elements for
			// each Item within
			int counter = 0;
			for (StuffCollection sc : allStuffCollections) {
				Element scNode = saveDoc.createElement("stuffcollection");
				String listname = listviewobslist.get(counter);
				scNode.setAttribute("listname", listname);
				for (Item i : sc.getItems()) {
					Element iN = saveDoc.createElement("item");
					iN.setAttribute("name", i.getName());
					iN.setAttribute("weight", "" + i.getWeight());
					iN.setAttribute("value", "" + i.getValue());
					scNode.appendChild(iN);
				}
				root.appendChild(scNode);
				counter++;
			}
			saveDoc.appendChild(root);

			// weird DOM filebuilding and outputstreaming ... don't ask.
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(saveDoc);
			StreamResult result = new StreamResult(new File("src/application/stuffsave.xml"));
			// StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		} catch (ParserConfigurationException | RuntimeException | TransformerException e) {
			e.printStackTrace();
		}
	}

	// method to validate the textfields used for adding new Items to a
	// StuffCollection according to certain regular expressions. The returning
	// integer is used to determine the first TextField that failed validation
	// and focus it. This is an ease-of-use operation.
	public int validateTextFields(String name, String weight, String value) {
		// name must not be empty and must start with a valid letter
		// weight and value must not be empty and must contain only numbers
		if (name == null || !(Pattern.matches("^\\w+.*", name))) {
			return 1;
		} else if (weight == null || !(Pattern.matches("^\\d+", weight))) {
			return 2;
		} else if (value == null || !(Pattern.matches("^\\d+", value))) {
			return 3;
		}
		// final check if the combination of name, weight and value to be added
		// to the list already exists in it. If that is the case, user is
		// prompted to change name.
		for (Item i : tableviewobslist) {
			if (i.getName().equals(name) && i.getWeight() == Integer.parseInt(weight)
					&& i.getValue() == Integer.parseInt(value)) {
				return 1;
			}
		}
		return 0;
	}
	
	public int validateRandomField(){
		String text = randomAmountField.getText();
		return (text != null && (Pattern.matches("^\\d+", text))) ? Integer.parseInt(text) : -1;
	}
	
	public void randomStuff(){
		int amount = validateRandomField();
		String listname = "" + amount + " random stuff collection " + (listviewobslist.size() + 1);
		listviewobslist.add(listname);
		StuffCollection sc = new StuffCollection(listviewobslist.size(), listname);
		if(amount>0){
			for(int i = 0;i<amount;i++){
				sc.addItem(new Item("L"+(listviewobslist.size())+" item "+i, ""+(int)(Math.random()*30+1), ""+(int)(Math.random()*40+1)));
			}
		}
		//killDuplicates(sc.getItems());
		allStuffCollections.add(sc);
		tableviewobslist.clear();
		listview.requestFocus();
		currentSelectNo = listviewobslist.size() - 1;
		listview.getSelectionModel().clearAndSelect(currentSelectNo);
		updateTableviewobslist();
	}
	
	public void deleteStuff(){
		
	}
	
	public ObservableList<Item> killDuplicates(ObservableList<Item> items){
		for(Item item : items){
			int counter = 0;
			for(Item i : items){
				if(item.getName() != i.getName()){
					if(item.getWeight()==i.getWeight() && item.getValue()==i.getValue()){
						items.remove(counter);
						counter--;
					}
				}
				counter++;
			}
		}
		return items;
	}

}
