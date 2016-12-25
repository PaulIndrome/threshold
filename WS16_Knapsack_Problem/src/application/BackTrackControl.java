package application;

import java.io.IOException;

import javax.swing.text.html.parser.DocumentParser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BackTrackControl {

	@FXML
	private TableView<Item> itemTable = new TableView<Item>();
	@FXML
	private TableColumn<Item, String> nameCol = new TableColumn<Item, String>();
	@FXML
	private TableColumn<Item, Integer> weightCol = new TableColumn<Item, Integer>();
	@FXML
	private TableColumn<Item, Integer> valueCol = new TableColumn<Item, Integer>();
	@FXML
	private ListView<String> knapsackView = new ListView<String>();
	
	private ObservableList<String> obsListView = FXCollections.observableArrayList();
	private ObservableList<Item> obsListTable = FXCollections.observableArrayList();
	private Document knapsacks;
	private NodeList allKnapsacks;
	
	public void initialize(){
		DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
			knapsacks = docBuild.parse(getClass().getResourceAsStream("Knapsacks.xml"));
			DOMConfiguration config = knapsacks.getDomConfig();
			config.setParameter(name, value);
			//knapsacks.normalize();
		} catch (SAXException | IOException | ParserConfigurationException | RuntimeException e) {
			e.printStackTrace();
		}
		
		allKnapsacks = knapsacks.getElementsByTagName("knapsack");
		for(int i = 0;i<allKnapsacks.getLength();i++){
			obsListView.add("knapsack " + (i+1));
		}
		knapsackView.setItems(obsListView);
		
		itemTable.getColumns().addAll(nameCol, weightCol, valueCol);
	}
	
	public void listViewClicked(){
		int selectNo = knapsackView.getEditingIndex();
		System.out.println("Selected: " + selectNo);
		Node selected = allKnapsacks.item(selectNo+1);
	
		NodeList itemsInSelected = selected.getChildNodes();
		
		for(int i = 0; i<itemsInSelected.getLength();i++){
			NamedNodeMap currentAttributes = itemsInSelected.item(i).getAttributes();
			String name = currentAttributes.getNamedItem("name").getNodeValue();
			int weight = Integer.parseInt(currentAttributes.getNamedItem("weight").getNodeValue());
			int value = Integer.parseInt(currentAttributes.getNamedItem("value").getNodeValue());
			
		}
		
	}
	
}
