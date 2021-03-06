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

import backtracking.Backtracking;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
	private Button packStuffButton;

	@FXML
	private TextField nameField;
	@FXML
	private TextField weightField;
	@FXML
	private TextField valueField;
	@FXML
	private TextField randomAmountField;
	@FXML
	private TextField randomMaxWeightField;
	@FXML
	private TextField randomMaxValueField;
	@FXML
	private TextField packCapacityField;
	@FXML
	private TextField packVersionField;
	@FXML
	private TextFlow versionsConsole;
	@FXML
	private TextFlow console;
	@FXML
	private ScrollPane scrollpane;
	@FXML
	private Canvas canvas;

	private ArrayList<StuffCollection> allStuffCollections;

	private ObservableList<String> listviewobslist = FXCollections.observableArrayList();
	private ObservableList<Item> tableviewobslist = FXCollections.observableArrayList();

	private Document stuffcollections;
	private NdList allStuff;
	private int currentSelectNo = -2;

	public void initialize() {
		System.out.println("Hello1");
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

		scrollpane.vvalueProperty().bind(console.heightProperty());
		console.setMouseTransparent(true);
		console.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg2) {
					scrollpane.requestFocus();
				}
			}
		});

		int i = 1;
		versionsConsole.getChildren().add(new Text("Descriptions of versions:\n\n"));
		while (getVersionDescription(i) != "Invalid Version Integer") {
			versionsConsole.getChildren().add(new Text(" " + i + ":\t" + getVersionDescription(i) + "\n\n"));
			i++;
		}
		System.out.println("Hello2");
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
			// listviewobslist.add("stuff collection " + (i + 1));
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
			// default case means validation on all three TextFields was
			// successfull
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

	// check if the given TextField shows a valid number
	public int validateNumberField(TextField field) {
		String text = field.getText();
		return (text != null && (Pattern.matches("^\\d+", text))) ? Integer.parseInt(text) : -1;
	}

	// generates a collection containing random Items in the amount set by the
	// corresponding TextField. The maximum weight and value of the items is
	// also set via TextFields.
	public void randomStuff() {
		// get the needed numbers by validating the corresponding TextFields
		int randomItemsAmount = validateNumberField(randomAmountField);
		int randomMaxWeight = validateNumberField(randomMaxWeightField);
		int randomMaxValue = validateNumberField(randomMaxValueField);

		if (randomItemsAmount > 1) {
			// construct parameters that make sense for random numbers
			randomMaxWeight = (randomMaxWeight > 2) ? randomMaxWeight : 10;
			randomMaxValue = (randomMaxValue > 2) ? randomMaxValue : 10;

			// create StuffCollection and fill it with random Items
			StuffCollection sc = new StuffCollection(listviewobslist.size());
			for (int i = 0; i < randomItemsAmount; i++) {
				sc.addItem(new Item("L" + (listviewobslist.size()) + " item " + i,
						"" + (int) (Math.random() * randomMaxWeight + 1),
						"" + (int) (Math.random() * randomMaxValue + 1)));
			}
			// strip randomly generated duplicates for algorithm purposes
			sc = killDuplicates(sc);

			// listname reflects user input parameters
			String listname = "" + randomItemsAmount + " (" + sc.getSize() + ") randoms maxW: " + randomMaxWeight
					+ " & maxV: " + randomMaxValue + " - " + (listviewobslist.size() + 1);
			sc.setListName(listname);

			// put collection into application and show it
			listviewobslist.add(listname);
			allStuffCollections.add(sc);
			tableviewobslist.clear();
			listview.requestFocus();
			currentSelectNo = listviewobslist.size() - 1;
			listview.getSelectionModel().clearAndSelect(currentSelectNo);
			updateTableviewobslist();
		}
	}

	// deletes the selected StuffCollection from the application. This is done
	// in both the ArrayList of all StuffCollections AND the observable list of
	// Strings used for the ListView.
	public void deleteStuff() {
		int selected = listview.getSelectionModel().getSelectedIndex();
		listview.getSelectionModel().clearSelection();
		allStuffCollections.remove(selected);
		listviewobslist.remove(selected);
		tableviewobslist.clear();
		// in case the deleted collection is not the only one, the preceding
		// collection is selected and the tableview filled accordingly
		if (selected > 0) {
			currentSelectNo = selected - 1;
			listview.getSelectionModel().clearAndSelect(currentSelectNo);
			updateTableviewobslist();
		}
	}

	// deletes all duplicate items in a StuffCollections item list. A duplicate
	// is determined via the Item's equals() method.
	public StuffCollection killDuplicates(StuffCollection sc) {
		ObservableList<Item> items = sc.getItems();
		ObservableList<Item> individuals = FXCollections.observableArrayList();
		for (Item i : items) {
			if (!individuals.contains(i))
				individuals.add(i);
		}
		sc.setItems(individuals);
		return sc;
	}

	// method to create an iteration of the Backtracking class from the selected
	// StuffCollection. This class then applies one of several variants of
	// backtracking algorithms according to variables based on user input. The
	// results of the specific algorithm pass are shown on the TextArea,
	// formatted to be user readable.
	// TODO: Output results and steps to completion as visually appealing
	// graphics. (dynamically built tree or sump'n)
	public void packStuff() {
		StuffCollection sc = allStuffCollections.get(currentSelectNo);
		int maxWeight = validateNumberField(packCapacityField);
		int version = validateNumberField(packVersionField);
		if (maxWeight > 0) {
			Backtracking bt = new Backtracking(sc, maxWeight);
			Text t1 = new Text("\n\t\t---- start of line ----\n");
			Text t2 = new Text("\nVersion used:\t\t\t- " + getVersionDescription(version) + " -");
			Text t3 = new Text("\nMaximum Weight of:\t " + bt.getMaxWeight());
			t3.setFill(Color.RED);
			Text t4 = new Text("\nCollection used:\t\t \"" + listviewobslist.get(currentSelectNo) + "\"");
			Text t5 = new Text("\nSize of collection used:\t" + sc.getSize());
			Text t6 = new Text("\nOptimal value found:\t" + bt.startBacktrack(version));
			t6.setFill(Color.GREEN);
			Text t7 = new Text("\nElapsed time:");
			Text t8 = new Text("\n\t\t(nanoseconds):\t" + bt.getLastElapsedTime());
			Text t9 = new Text("\n\t\t(milliseconds):\t\t" + bt.getLastElapsedTime() / 1000000);
			Text t10 = new Text("\n\t\t(seconds):\t\t" + bt.getLastElapsedTime() / 1000000000);
			Text t11 = new Text("\nPacked items: " + bt.getPackedItems());
			Text t12 = new Text("\n\n\t\t\t---- end of line ----\n");
			console.getChildren().addAll(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
		}
	}

	// generates a short description of different versions of the backtracking
	// algorithm
	public String getVersionDescription(int version) {
		switch (version) {
		case 1:
			return "weight & value via combined array[][]";
		case 2:
			return "weight & value via ArrayList<Item>";
		case 3:
			return "combined array[][], memorize matrix";
		case 4:
			return "greedy by ratio, full iteration";
		case 5:
			return "backtrack iterative (unreliable, work in progress)";
		case 6:
			return "bruteforce by binary (max n = 24)";
		default:
			return "Invalid Version Integer";
		}
	}

}
