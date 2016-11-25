package application;

import java.awt.MouseInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DropDownFontList {

	@FXML
	ListView<String> dropDownFontList = new ListView<String>();
	@FXML
	Slider FontSizeSlider;
	@FXML
	Button CustomFont;

	public String fs = System.getProperty("file.separator");
	public ArrayList<Font> FontList = new ArrayList<Font>();
	public ObservableList<String> ObsFontList = FXCollections.observableArrayList();
	private static Stage popupStage;
	private static Font selectedFont = new Font("Arial", 12);
	private static double selectedFontSize = 12;
	private static boolean FontListUpdated = false;

	@FXML
	public void initialize() {
		MainController.run = false;
		FontListUpdated = false;
		while(FontListUpdated == false){
				updateFontList();
		}
		updateObsFontList();
		MainController.run = true;

	}

	public void loadCustomFont() {
		MainController.run = false;
		FileChooser fc = new FileChooser();
		fc.setInitialFileName("*.ttf");
		fc.getExtensionFilters().add(new ExtensionFilter("Font Files", "*.ttf"));
		File newFontFile = fc.showOpenDialog(this.getStage().getOwner());
		try {
			
			String[] FileList = new File("fonts").list();
			String FileListFonts = "";
			for(String string : FileList){
				FileListFonts += string+" ";
			}
			if (newFontFile == null) {
				System.out.println("Did you cancel something?");
			} else if (!FileListFonts.contains(newFontFile.getName())) {
				FileInputStream ReadFontFromExternal = new FileInputStream(newFontFile);
				FileOutputStream WriteFontToLocal = new FileOutputStream("fonts" + fs + newFontFile.getName());
				for (int a = 1; a < newFontFile.length()+1; a++) {
					WriteFontToLocal.write(ReadFontFromExternal.read());
					System.out.println(a);
				}
				ReadFontFromExternal.close();
				WriteFontToLocal.close();
			}
			if (newFontFile != null && FileList.toString().contains(newFontFile.toString())) {
				FontList.add(Font.loadFont("fonts" + fs + newFontFile.getName(), selectedFontSize));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		MainController.run = true;
	}

	public void updateFontList() {
		File FontFolder = new File("fonts");
		for (File fontFile : FontFolder.listFiles()) {
			try {
				FontList.add(Font.loadFont(new FileInputStream(fontFile), selectedFontSize));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		FontListUpdated = true;
	}

	public void updateObsFontList() {
		for (int i = 0; i < FontList.size(); i++) {
			String FontName = FontList.get(i).getFamily();
			dropDownFontList.getItems().add(FontName);
		}
	}

	public ArrayList<Font> getFontList() {
		return FontList;
	}

	public ListView<String> getListView() {
		return dropDownFontList;
	}

	public void clickListView() {
		selectedFontSize = selectedFont.getSize();
		selectedFont = new Font(FontList.get(dropDownFontList.getSelectionModel().getSelectedIndex()).getName(),
				selectedFontSize);
	}

	public void changedSliderValue() {
		selectedFontSize = FontSizeSlider.getValue();
		selectedFont = new Font(selectedFont.getFamily(),
				selectedFontSize);
	}

	public Font getSelectedFont() {
		return selectedFont;
	}

	public void closePopup() {
		popupStage.close();
	}

	public void showPopup() {
		try {

			popupStage = new Stage();
			popupStage.initStyle(StageStyle.UNDECORATED);
			AnchorPane page = (AnchorPane) FXMLLoader.load(DropDownFontList.class.getResource("DropDownFonts.fxml"));
			Scene scene = new Scene(page);
			popupStage.setScene(scene);

			popupStage.initOwner(null);
			popupStage.initModality(Modality.WINDOW_MODAL);

			popupStage.setX(MouseInfo.getPointerInfo().getLocation().getX() - 10);
			popupStage.setY(MouseInfo.getPointerInfo().getLocation().getY() - 10);

			popupStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stage getStage() {
		return popupStage;
	}

	public boolean isShowing() {
		return this.getStage().isShowing();
	}

}
