package application;

import java.io.IOException;
import java.util.Random;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController {
	@FXML
	private AnchorPane root;
	@FXML
	private Button starter;
	@FXML
	private Button toggle;
	@FXML
	private Button thrower;
	@FXML
	public TextArea textarea;
	@FXML
	public ProgressBar loadingProgress = new ProgressBar();
	@FXML
	public ImageView imageTest;
	
	public static SimpleDoubleProperty progress = new SimpleDoubleProperty(0);
	public static boolean run = true;
	private static DropDownFontList popup = new DropDownFontList();
	private static FullBookFromXML book;
	
	@FXML
	private void initialize() throws IOException {
		
		book = new FullBookFromXML();
		
		imageTest.setImage(book.getChapterMap(1));

		ContextMenu cm = new ContextMenu();
		textarea.setContextMenu(cm);

		root.hoverProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				try {
					if (popup.isShowing() && arg0.getValue()){
						popup.closePopup();
					}
				} catch (NullPointerException e) {
				}
			}
		});
	
		progress.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				loadingProgress.setProgress(newValue.doubleValue());
			}
		});
		
	}

	public void rightclickTextarea(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY) {
			try {
				popup.showPopup();
			} catch (Exception d) {
				d.printStackTrace();
			}
		} 
	}

	public void starting() {
		StringWriterService writer = new StringWriterService(book.getMainText(1), textarea);
		writer.startService();
		//appendService.restart();
	}

	public void toggle() {
		if (run)
			run = false;
		else
			run = true;
	}

	public void throwing() {
		run = false;
		textarea.appendText("CATCH!");
		run = true;
	}

	
	
	public void setProgress(double perc){
		loadingProgress.setProgress(perc);
	}
	
	public static DropDownFontList getPopup(){
		return popup;
	}
}
