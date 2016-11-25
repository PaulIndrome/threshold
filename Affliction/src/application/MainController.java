package application;

import java.util.concurrent.ExecutorService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainController {
	@FXML
	public ImageView mapview;
	@FXML
	public Button btnchap1;
	@FXML
	public Button btnchap2;
	@FXML
	public ScrollPane mainscrollpane;
	@FXML
	public Pane mainpane;
	@FXML
	public TextArea maintext;
	@FXML
	public Label mainlabel;

	Chapter chapter1 = new Chapter("F:\\Java\\Workspace\\Text_test\\src\\application\\chapter1.txt");

	@FXML
	public void showChapter1() {
		Runnable task = new Runnable() {
			public void run() {
				try {
					chapter1.writeChapter(maintext);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread thread = new Thread(task);
		thread.start();
	}
	
	@SuppressWarnings("null")
	@FXML
	public void showChapter1InLabel() {
		Runnable task = new Runnable() {
			public void run() {
				try {
					chapter1.writeChapterInLabel(mainlabel);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		ExecutorService service = null;
		service.submit(task);
	}
}