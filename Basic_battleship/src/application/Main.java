package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../gameboard/GameBoard.fxml"));
			System.out.println("1 " + loader.getRoot());
			
			AnchorPane root = loader.load();
			System.out.println("2 " + root);
			Scene scene = new Scene(root);
			
			EventController eC = loader.getController();
			System.out.println("3 " + eC.root);
			
			eC.registerRoot(loader.getRoot());
			System.out.println("4 " + eC.root);
			
			System.out.println("5 " + loader.getRoot());
			
			eC.registerHandlerOnRoot();
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
