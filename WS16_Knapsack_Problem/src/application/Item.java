package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

	private SimpleStringProperty name;
	private SimpleIntegerProperty weight;
	private SimpleIntegerProperty value;
	private SimpleDoubleProperty ratio;

	//constructs a new item using simple data types
	//properties are instantiated within the constructor
	public Item(String name, String weight, String value) {
		this.name = new SimpleStringProperty(name);
		this.weight = new SimpleIntegerProperty(Integer.parseInt(weight));
		this.value = new SimpleIntegerProperty(Integer.parseInt(value));

		double longDouble = (double) this.value.doubleValue()/this.weight.doubleValue();
		int temp = (int) (longDouble * 100.0);
		double shortDouble = ((double) temp) / 100.0;

		this.ratio = new SimpleDoubleProperty(shortDouble);
	}

	//returns true if this item's name equals the argument
	public boolean identifyItemByName(String name){
		return this.name.getValue() == name;
	}
	
	public String getName() {
		return name.get();
	}

	public int getWeight() {
		return weight.get();
	}

	public int getValue() {
		return value.get();
	}

	public double getRatio() {
		return ratio.get();
	}

}
