package application;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StuffCollection {

	private ObservableList<Item> items = FXCollections.observableArrayList();
	private int index;
	
	public StuffCollection(Node parent, int index){
		this.index = index;
		
		//generate modified NodeList of Items in Stuff
		NdList nl = new NdList(parent.getChildNodes());
		
		for(int i = 0;i<nl.getLength();i++){
			//generate attributelist of item
			NamedNodeMap attr = nl.item(i).getAttributes();
			//in case of no name attribute, generate name
			String name = (attr.getNamedItem("name") == null) ? "item " + i : attr.getNamedItem("name").getNodeValue();
			//get weight and value of item
			int weight = Integer.parseInt(attr.getNamedItem("weight").getNodeValue());
			int value = Integer.parseInt(attr.getNamedItem("value").getNodeValue());
			//generate item, add to ObservableList
			items.add(new Item(name, weight, value));
		}
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	
	public void removeItem(String name){
		for(Item i : items){
			if(i.identifyItemByName(name))
				items.remove(i);
			else
				continue;
		}
	}
	
	public ObservableList<Item> getItems(){
		return items;
	}
	
	public Item getItem(int itemIndex){
		return items.get(itemIndex);
	}
	
	public int clearCollection(){
		//returns -1 if no items in collection, 1 if successfully cleared
		if(items.isEmpty()){
			return -1;
		} else {
			items.clear();
			return 1;
		}
	}

	
	
}
