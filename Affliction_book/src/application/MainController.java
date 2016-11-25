package application;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainController {
	// HERE BE ONLY FXML ATTRIBUTES!
	@FXML
	public ImageView mapview;
	@FXML
	public ImageView coverview;
	@FXML
	public Button btn1;
	@FXML
	public Button btn2;
	@FXML
	public Button btn3;
	@FXML
	public Button btnshowtext;
	@FXML
	public Pane mainpane;
	@FXML
	public TextArea maintext;
	@FXML
	public Label scorelabel;

	// HERE BE THE USUAL SORT OF ATTRIBUTES!
	public static boolean showfulltext;
	public static int score = 50;
	public static String chapnumber = "0";
	public static Lock threadlock = new ReentrantLock();
	public static Thread thread = new Thread();
	public XMLchapter currentChapter = new XMLchapter("0");
	public Pattern capitals = Pattern.compile("[A-Z]");

	public void initialize() {
		showfulltext = false;
		appendTextInMaintext("\n\n\t\t\t\t\t" + currentChapter.getFlourishes().getAttribute("flourish1"), false);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// write introduction (chapter 0) text
		appendTextInMaintext("\n\n" + currentChapter.getMaintext(), true);
		updateUI(currentChapter.getChoice(1).getAttribute("text"), currentChapter.getChoice(2).getAttribute("text"),
				currentChapter.getChoice(3).getAttribute("text"));

	}

	public void actualize(int choice) throws InterruptedException {
		showfulltext = false;
		// raise score by chosen choice's score number
		score = score + Integer.parseInt(currentChapter.getChoice(choice).getAttribute("score"));
		// keep score between 0 and 100
		if (score < 0)
			score = 0;
		if (score > 100)
			score = 100;
		scorelabel.setText(String.valueOf(score));

		// append chosen choice's appendix text to TextArea
		appendTextInMaintext("\n\n" + currentChapter.getChoice(choice).getAttribute("appendix"), false);

		/*
		 * At this point a query is inserted looking for the numbers of planned
		 * final chapters. If a final chapter is found the story is concluded with an epilogue.
		 * Credits are included after the epilogue in each final chapter's maintext.
		 * For this version of the game, chapter 3 is treated like a final chapter
		 */
		if (chapnumber.equals("3") || chapnumber == "3"){
			System.out.println("Hallo");
			currentChapter = new XMLchapter("3f", true);
			appendTextInMaintext("\n\n\t\t\t\t\t" + currentChapter.getFlourishes().getAttribute("flourish1") + "\n\n" + evaluateScore() + "\n\n" + currentChapter.getMaintext(), true);
		} else if (chapnumber.equals("12") || chapnumber == "12") {
			currentChapter = new XMLchapter("12f", true);
			appendTextInMaintext("\n\n\t\t\t\t\t" + currentChapter.getFlourishes().getAttribute("flourish1") + "\n\n" + evaluateScore() + "\n\n" + currentChapter.getMaintext(), true);
		} else if (chapnumber.equals("15") || chapnumber == "15") {
			currentChapter = new XMLchapter("15f", true);
			appendTextInMaintext("\n\n\t\t\t\t\t" + currentChapter.getFlourishes().getAttribute("flourish1") + "\n\n" + evaluateScore() + "\n\n" + currentChapter.getMaintext(), true);
		} else if (chapnumber.equals("16") || chapnumber == "16") {
			currentChapter = new XMLchapter("16f", true);
			appendTextInMaintext("\n\n\t\t\t\t\t" + currentChapter.getFlourishes().getAttribute("flourish1") + "\n\n" + evaluateScore() + "\n\n" + currentChapter.getMaintext(), true);
			
		} else {
			// if no final chapter has been reached, continue normally
			
			// change chapter number to next chapter according to choice
			chapnumber = currentChapter.getChoice(choice).getAttribute("nextchapter");
			
			currentChapter = new XMLchapter(chapnumber);
			Thread.sleep(10); // Thread sleeps to maintain order of text appends
			appendTextInMaintext("\n\n\t\t\t\t\t" + currentChapter.getFlourishes().getAttribute("flourish1"), false);
			Thread.sleep(10);
			
			appendTextInMaintext("\n\n" + currentChapter.getMaintext(), true);
			
			// update buttons with next choices if not finaltext beforehand
			updateUI(currentChapter.getChoice(1).getAttribute("text"), currentChapter.getChoice(2).getAttribute("text"),
					currentChapter.getChoice(3).getAttribute("text"));
		}
	}

	public void appendTextInMaintext(String text, boolean ismaintext) {
		Runnable task = new Runnable() {
			volatile boolean exit = false;

			public void run() {
				threadlock.lock();

				while (!exit) {
					try {
						/*
						 * disabling the buttons is done in a superfluous loop
						 * because it was randomly skipped during test runs
						 */
						while (!btn1.isDisabled() || !btn2.isDisabled() || !btn3.isDisabled()) {
							btn1.setDisable(true);
							btn2.setDisable(true);
							btn3.setDisable(true);
						}
						int i = 0;
						try {
							while (i < text.length()) {
								// String to be filled with a single char
								String appendchar = text.charAt(i) + "";
								i++;
								/*
								 * main if-else query for writing text
								 */
								if (showfulltext == false) {
									// create matcher for capital letters
									Matcher caps = capitals.matcher(appendchar);
									// if matcher finds the pattern [A-Z] the
									// text is appended slower
									if (caps.find()) {
										Thread.sleep((long) 75);
										maintext.appendText(appendchar);
										Thread.sleep((long) (150 + Math.random() * 45));
									} else {
										maintext.appendText(appendchar);
										Thread.sleep((long) (90 + Math.random() * 20));
									}
								} else {
									maintext.appendText(appendchar + text.substring(i, text.length()));
									i = text.length();
								}
								// if a chapter's maintext is being written
								// update mapstep and cover
								if (ismaintext == true) {
									mapview.setImage(currentChapter.getMapstep());
									coverview.setImage(currentChapter.getCover());
								}
								// if end of text is reached, kill while(!exit)
								// loop and reset showfulltext variable
								if (i >= text.length()) {
									exit = true;
									showfulltext = false;
								}
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} finally {
						threadlock.unlock();
					}
				}
			}
		};
		thread = new Thread(task);
		thread.start();
	}

	public void updateUI(String choice1, String choice2, String choice3) {
		Runnable task = new Runnable() {
			public void run() {
				threadlock.lock();
				try {
					// update button texts
					/*
					 * The reason the setText() methods for the buttons are in a
					 * nested thread is because changes to javaFX elements can
					 * not be made from outside the main application thread and
					 * this circumvents the problem well enough. It is a quick
					 * and dirty fix to a problem arising from a quick and dirty
					 * solution of the consecutive-threads problem above
					 * involving locks.
					 */
					Platform.runLater(() -> {
						btn1.setText(choice1);
						btn2.setText(choice2);
						btn3.setText(choice3);
					});

				} finally {
					threadlock.unlock();
					/*
					 * reenabling the buttons is done in a superfluous loop
					 * because it was randomly skipped during test runs
					 */
					do {
						btn1.setDisable(false);
						btn2.setDisable(false);
						btn3.setDisable(false);
					} while (btn1.isDisabled() || btn2.isDisabled() || btn3.isDisabled());

				}
			}
		};
		thread = new Thread(task);
		thread.start();
	}

	/*
	 * buttons invoke actualize() with different integers - this means having to
	 * make sure there are always exactly 3 choices in any given chapter
	 */

	public String evaluateScore() {
		if (score < 20)
			return "Your choices have revealed you to be cold-hearted and goal-driven.";
		else if (20 <= score && score < 40)
			return "Your choices have revealed you to be goal-oriented and - at times - selfish for selfish reasons.";
		else if (40 <= score && score < 60)
			return "Your choices have revealed you to be calculating and morally flexible to a certain degree.";
		else if (60 <= score && score <= 80)
			return "Your choices have revealed you to be generally good-hearted and willing to help selflessly.";
		else if (score > 80)
			return "Your choices have revealed you to good-hearted and for a good cause willing to step back from your own.";
		else
			return "If this evaluation of your score is shown, something went wrong.";
	}

	public void choice1() throws InterruptedException {
		if (thread.isAlive()) {
			thread.join();
		}
		actualize(1);
	}

	public void choice2() throws InterruptedException {
		if (thread.isAlive()) {
			thread.join();
		}
		actualize(2);
	}

	public void choice3() throws InterruptedException {
		if (thread.isAlive()) {
			thread.join();
		}
		actualize(3);
	}

	public void showfulltext() {
		showfulltext = true;
	}
}