package application;

/*
 * remaining to implement:
 * Score
 * score-handling at end
 * choice-tree
 * saving & loading of progress and choices up to that point
 * 
 */

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
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
	private Button choice1;
	@FXML
	private Button choice2;
	@FXML
	private Button choice3;

	@FXML
	private TextArea textarea;
	@FXML
	public ProgressBar loadingProgress = new ProgressBar();
	@FXML
	public ImageView mapview;
	@FXML
	public ImageView coverview;
	@FXML
	public CheckBox jumper;
	@FXML
	public Label scorelabel;

	public static boolean run = true;
	private static DropDownFontList popup = new DropDownFontList();
	private static ChoiceWindow choicewindow = new ChoiceWindow();
	private static FullBookFromXML book = new FullBookFromXML();;
	public static boolean jump = false;

	public static SimpleBooleanProperty MaintextFinished = new SimpleBooleanProperty(false);
	public static SimpleBooleanProperty AppendixFinished = new SimpleBooleanProperty(false);
	public static SimpleDoubleProperty progress = new SimpleDoubleProperty(0);
	public static SimpleIntegerProperty score = new SimpleIntegerProperty(50);

	public static int currentChapter = 0;
	public static int selectedChoice;

	private static ArrayList<ProgressElement> Progress = new ArrayList<ProgressElement>();

	@FXML
	private void initialize() throws IOException {

		// Delete standard context menu of textarea
		ContextMenu cm = new ContextMenu();
		textarea.setContextMenu(cm);

		// if mouse hovers over children of root element, close popup
		root.hoverProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				try {
					if (popup.isShowing() && arg0.getValue()) {
						popup.closePopup();
					}
				} catch (NullPointerException e) {
				}
			}
		});

		// add listener for progress to fill progressbar accordingly
		progress.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				loadingProgress.setProgress(newValue.doubleValue());
			}
		});

		// add listener for checkbox to either skip writing or not
		jumper.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				jump = newValue.booleanValue();
			}
		});

		/*
		 * Add listener for being done with writing the maintext of a chapter.
		 * As long as a maintext is being written, the choice buttons are
		 * disabled. If a maintext has been written completely, the choice
		 * buttons are reenabled.
		 */
		MaintextFinished.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue() == true) {
					Platform.runLater(() -> {
						choice1.setText(book.getChoiceButtonText(currentChapter, 0));
						choice2.setText(book.getChoiceButtonText(currentChapter, 1));
						choice3.setText(book.getChoiceButtonText(currentChapter, 2));
						choice1.setDisable(false);
						choice2.setDisable(false);
						choice3.setDisable(false);
					});
				} else {
					choice1.setDisable(true);
					choice2.setDisable(true);
					choice3.setDisable(true);
				}
			}
		});

		/*
		 * Add listener for being done with writing the appendix of a chapter's
		 * choice. While an appendix is being written, the listener for a
		 * maintext being done writing is reset as well, leading to the
		 * choicebuttons being disabled again. After an appendix has been
		 * written completely, a new ProgressElement is generated, the
		 * currentChapter is set to the following chapter according to the
		 * matching information in the XML document, a flourish is added to the
		 * textarea, the new chapter's cover and map are shown and the new
		 * chapter's maintext starts writing.
		 */
		/*
		 * This sequence of actions automates the chapter turn after a choice
		 * has been made and an appendix has been written (completely).
		 */
		AppendixFinished.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.booleanValue()) {
					Progress.add(new ProgressElement(currentChapter, selectedChoice, score.intValue(),
							book.getChapterTitle(currentChapter),
							book.getChoiceButtonText(currentChapter, selectedChoice)));

					currentChapter = book.getNextChapter(currentChapter, selectedChoice);
					textarea.appendText(
							System.lineSeparator() + "\t\t\t" + book.getFlourish(1) + System.lineSeparator());

					new StringWriterService(book.getMainText(currentChapter), textarea, false).startService();
					coverview.setImage(book.getChapterCover(currentChapter));
					mapview.setImage(book.getChapterMap(currentChapter));
				}
			}
		});

		// automatically sets the scorelabel to the current score whenever it
		// changes
		score.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				scorelabel.setText("" + newValue.intValue());
			}
		});

		// set default score (50)
		scorelabel.setText("" + score.intValue());

		// append first flourish
		textarea.appendText(System.lineSeparator() + "\t\t\t" + book.getFlourish(1) + System.lineSeparator());

		// safety sleep because of introduction sometimes failing to start
		// writing
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// default disabling of buttons for writing of introduction
		choice1.setDisable(true);
		choice2.setDisable(true);
		choice3.setDisable(true);

		// start writing the introduction
		Platform.runLater(() -> {
			new StringWriterService(book.getMainText(currentChapter), textarea, false).startService();
		});

	}

	// when the textarea is rightclicked, construct a popup and show it
	public void rightclickTextarea(MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY) {
			try {
				popup.showPopup();
			} catch (Exception d) {
				d.printStackTrace();
			}
		}
	}

	// currently generates the choiceWindow
	public void starting() {
		for (ProgressElement pe : Progress) {
			System.out.println(pe.toString());
		}
		choicewindow.showChoiceWindow();
	}

	// toggle a pause in writing text
	public void toggle() {
		if (run)
			run = false;
		else
			run = true;
	}

	// test function to throw the word "CATCH!" into the textarea at random
	// points
	public void throwing() {
		double randomindex = Math.random() * textarea.getLength();
		textarea.insertText((int) randomindex, "CATCH!");
	}

	public static DropDownFontList getPopup() {
		return popup;
	}

	/*
	 * Following are the three different methods called by the three different
	 * choicebuttons. Each choicebutton corresponds to a specific appendix to be
	 * written with a new StringWriterService object. Following the click of a
	 * button, the selected choice's appendix starts being written on the
	 * textarea and the buttons are disabled.
	 */
	public void writeChoiceAppendix1() {
		new StringWriterService(book.getChoiceAppendix(currentChapter, 0), textarea, true).startService();
		selectedChoice = 0;
		score.set(score.intValue() + book.getChoiceScore(currentChapter, 0));
	}

	public void writeChoiceAppendix2() {
		new StringWriterService(book.getChoiceAppendix(currentChapter, 1), textarea, true).startService();
		selectedChoice = 1;
		score.set(score.intValue() + book.getChoiceScore(currentChapter, 1));
	}

	public void writeChoiceAppendix3() {
		new StringWriterService(book.getChoiceAppendix(currentChapter, 2), textarea, true).startService();
		selectedChoice = 2;
		score.set(score.intValue() + book.getChoiceScore(currentChapter, 2));
	}

	public static ArrayList<ProgressElement> getProgress() {
		return Progress;
	}

	public TextArea getTextarea() {
		return textarea;
	}

}
