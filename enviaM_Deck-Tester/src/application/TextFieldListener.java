package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TextFieldListener implements ChangeListener<String> {

	private final TextField textField;
	private final Model values;
	private final boolean doubleOrInt;

	// DoubleOrInt: true = double, false = int
	TextFieldListener(TextField tF, Model model, boolean doubleOrInt) {
		this.textField = tF;
		this.values = model;
		this.doubleOrInt = doubleOrInt;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		if ((doubleOrInt && !validateDouble(newValue)) || (!doubleOrInt && !validateInt(newValue)))
			textField.replaceText(0, newValue.length(), oldValue);
		else
			values.updateAnyTextField(textField.getText(), textField.getId());
	}

	private boolean validateDouble(String text) {
		return text.matches("[0-9]*,?[0-9]{0,2}");
	}

	private boolean validateInt(String text) {
		return text.matches("[0-9]*");
	}

}
