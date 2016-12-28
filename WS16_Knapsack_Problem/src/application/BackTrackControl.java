package application;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
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
	private Button addStuffButton;
	@FXML
	private TextField nameField;
	@FXML
	private TextField weightField;
	@FXML
	private TextField valueField;
	
	
	private ArrayList<StuffCollection> allStuffCollections;
	private ObservableList<String> listviewobslist = FXCollections.observableArrayList();
	private ObservableList<Item> tableviewobslist = FXCollections.observableArrayList();
	private ObservableList<Item> newKnapList = FXCollections.observableArrayList();
	private Document stuffcollections;
	private NdList allStuff;
	private int currentSelectNo = -2;

	public void initialize() {
		
		parseXML("stuff.xml");

		// itemTable.getColumns().addAll(itemCol, weightCol, valueCol,
		// ratioCol);
		itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		weightCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("weight"));
		valueCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("value"));
		ratioCol.setCellValueFactory(new PropertyValueFactory<Item, Double>("ratio"));
		tableview.setItems(tableviewobslist);

	}

	public void listViewClicked() {
		int selectNo = listview.getSelectionModel().getSelectedIndex();
		if (currentSelectNo != selectNo) {
			currentSelectNo = selectNo;
			tableviewobslist.clear();

			for(Item i : allStuffCollections.get(selectNo).getItems()){
				tableviewobslist.add(i);
			}
			
			
//			for (int i = 0; i < itemsInSelected.getLength(); i++) {
//				NamedNodeMap currentAttributes = itemsInSelected.item(i).getAttributes();
//				String name = (currentAttributes.getNamedItem("name") == null) ? "item " + i
//						: currentAttributes.getNamedItem("name").getNodeValue();
//				int weight = Integer.parseInt(currentAttributes.getNamedItem("weight").getNodeValue());
//				int value = Integer.parseInt(currentAttributes.getNamedItem("value").getNodeValue());
//				System.out.println(name + " " + weight + " " + value);
//				tableviewobslist.add(new Item(name, weight, value));
//			}
		} else {
			tableviewobslist.clear();
			currentSelectNo = -2;
		}

	}

	public void parseXML(String xmlPath) {
		try {
			DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
			stuffcollections = docBuild.parse(getClass().getResourceAsStream(xmlPath));
		} catch (SAXException | IOException | ParserConfigurationException | RuntimeException e) {
			e.printStackTrace();
		}
		allStuffCollections = new ArrayList<StuffCollection>();
		allStuff = new NdList(stuffcollections.getElementsByTagName("stuffcollection"));
		for (int i = 0; i < allStuff.getLength(); i++) {
			listviewobslist.add("stuff collection " + (i + 1));
			allStuffCollections.add(new StuffCollection(allStuff.item(i), i));
		}
		listview.setItems(listviewobslist);
	}

	public void addStuff() {
		//TODO check TextFields, add new Item to TableView
	}
	
	public void newStuff(){
		//TODO start new StuffCollection, add to ListView, clear TableView etc
	}
	
	public void saveStuff(){
		//TODO save all StuffCollections to XML document
	}
	

}
