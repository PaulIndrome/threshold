package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChoiceWindow {

	@FXML
	private Pane mainpane = new Pane();

	private static Stage ChoiceStage;

	public void showChoiceWindow() {
		try {

			ChoiceStage = new Stage();
			AnchorPane page = (AnchorPane) FXMLLoader.load(this.getClass().getResource("ChoiceWindow.fxml"));
			mainpane.prefHeightProperty().set(480);
			mainpane.prefWidthProperty().set(1280);

			page.getChildren().add(mainpane);

			page.layout();

			Scene scene = new Scene(page);

			ChoiceStage.setScene(scene);

			ChoiceStage.initOwner(null);
			ChoiceStage.initModality(Modality.WINDOW_MODAL);

			ChoiceStage.show();

			drawSquares();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawSquares() {
		ArrayList<ProgressElement> Progress = MainController.getProgress();
		double chapterX = 50;
		double space = 50;

		for (ProgressElement pe : Progress) {
			if(Progress.indexOf(pe) == 0){
				continue;
			}
			new ChapterSquare(mainpane, space, chapterX, 2, pe.getTitle(), Color.RED, false);
			switch (pe.getChoice()) {

			case 0:
				new ChapterSquare(mainpane, space, chapterX + 100 + space, 1, pe.getChoicetext(), Color.RED, true);
				new ChapterSquare(mainpane, space, chapterX + 100 + space, 2, "The choice you didn't take.", Color.BLACK, false);
				new ChapterSquare(mainpane, space, chapterX + 100 + space, 3, "The choice you didn't take.", Color.BLACK, false);
				break;
			case 1:
				new ChapterSquare(mainpane, space, chapterX + 100 + space, 1, "The choice you didn't take.", Color.BLACK, false);
				new ChapterSquare(mainpane, space, chapterX + 100 + space, 2, pe.getChoicetext(), Color.RED, true);
				new ChapterSquare(mainpane, space, chapterX + 100 + space, 3, "The choice you didn't take.", Color.BLACK, false);
				break;
			case 2:
				new ChapterSquare(mainpane, space, chapterX + 100 + space, 1, "The choice you didn't take.", Color.BLACK, false);
				new ChapterSquare(mainpane, space, chapterX + 100 + space, 2, "The choice you didn't take.", Color.BLACK, false);
				new ChapterSquare(mainpane, space, chapterX + 100 + space, 3, pe.getChoicetext(), Color.RED, true);
				break;
			default:
				System.out.println("Progresselement's getChoice() method returned a value outside of regular bounds");
				break;
			}
			
			chapterX += (100 + space)*2;
		}
	}

	public Stage getStage() {
		return ChoiceStage;
	}

	public boolean isShowing() {
		return this.getStage().isShowing();
	}

}
