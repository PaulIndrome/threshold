package gameFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main-class for the editor of "Deep Space Dweller".
 * 
 * @author Alexander Thomas Kühn
 *
 */
public class MainEditor extends Application{

	private AnchorPane root;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//declaration and instantiation of the FXMLLoader
			FXMLLoader loader = new FXMLLoader();
			//sets the location of the .fxml-file
			loader.setLocation(MainEditor.class.getResource("LayoutEditor.fxml"));
			//loads the layout from the .fxml-file
			root = (AnchorPane) loader.load();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			//sets the title of the window
			primaryStage.setTitle("Deep Space Dweller - Editor");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
