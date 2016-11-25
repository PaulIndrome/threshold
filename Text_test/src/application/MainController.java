package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class MainController {

	public Chapter chapter1 = new Chapter("F:\\Java\\Workspace\\Text_test\\src\\application\\chapter1.txt");

	@FXML
	public ScrollPane TextWindow;
	@FXML
	public Label TextLabel;
	@FXML
	public Button btnschreibwas;

	@FXML
	public void schreibmalwas(){
		String ct = "";
		
		for (int i = 0; i < chapter1.getKapitelText().length; i++) {
			ct += chapter1.getKapitelText()[i];
			TextLabel.setText(ct);
		}
		
	}

}
