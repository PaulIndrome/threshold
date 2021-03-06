package application;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StuffCollection {

	private ObservableList<Item> items = FXCollections.observableArrayList();
	private String listname;
	private int index;

	// constructors for new StuffCollection without an already existing NodeList
	// of Items
	public StuffCollection(int index) {
		this.listname = listname;
	}
	
	public StuffCollection(int index, String listname) {
		this.listname = listname;
		this.index = index;
	}

	public StuffCollection(Node parent, int index) {
		// index is equal to the number of the occurrence of the instantiating
		// stuffcollection-node in XML file
		this.index = index;

		this.listname = parent.getAttributes().getNamedItem("listname").getNodeValue();

		// generate modified NodeList of Items in Stuff
		NdList nl = new NdList(parent.getChildNodes());

		for (int i = 0; i < nl.getLength(); i++) {
			// generate attributelist of item
			NamedNodeMap attr = nl.item(i).getAttributes();
			// in case of no name attribute, generate name
			String name = (attr.getNamedItem("name") == null) ? "item " + i : attr.getNamedItem("name").getNodeValue();
			// get weight and value of item
			String weight = attr.getNamedItem("weight").getNodeValue();
			String value = attr.getNamedItem("value").getNodeValue();
			// generate item, add to ObservableList
			items.add(new Item(name, weight, value));
		}
	}

	// add given item to the end of items list if that item isn't already in the list
	public void addItem(Item item) {
		if (!items.contains(item))
			items.add(item);
	}

	// removes any item identifiable by the given name (can be multiple)
	public void removeItem(String name) {
		for (Item i : items) {
			if (i.identifyItemByName(name))
				items.remove(i);
			else
				continue;
		}
	}

	// returns entire observable list of items
	public ObservableList<Item> getItems() {
		return items;
	}

	// sets a new list of items
	public void setItems(ObservableList<Item> items){
		this.items = items;
	}
	
	// returns an item identified by its index in items list
	public Item getItem(int itemIndex) {
		return items.get(itemIndex);
	}

	// returns first occurrence of item identified by its name or null if no
	// such item exists
	public Item getItemByName(String name) {
		for (Item i : items) {
			if (i.identifyItemByName(name)) {
				return i;
			} else {
				continue;
			}
		}
		return null;
	}

	// returns -1 if no items in collection, 1 if successfully cleared
	public int clearCollection() {
		if (items.isEmpty()) {
			return -1;
		} else {
			items.clear();
			return 1;
		}
	}

	public int getSize() {
		return items.size();
	}

	public String getListName() {
		return listname;
	}
	
	public void setListName(String listname){
		this.listname = listname;
	}

}
