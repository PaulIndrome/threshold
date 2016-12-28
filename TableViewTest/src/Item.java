
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

	private SimpleStringProperty name;
	private SimpleIntegerProperty weight;
	private SimpleIntegerProperty value;
	private SimpleDoubleProperty ratio;

	public Item(String name, int weight, int value) {
		this.name = new SimpleStringProperty(name);
		this.weight = new SimpleIntegerProperty(weight);
		this.value = new SimpleIntegerProperty(value);

		double longDouble = (double) value/weight;
		int temp = (int) (longDouble * 100.0);
		double shortDouble = ((double) temp) / 100.0;

		this.ratio = new SimpleDoubleProperty(shortDouble);
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
