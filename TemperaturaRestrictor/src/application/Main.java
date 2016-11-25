package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static boolean threadEnde = true;
	public static Controls theFatCuntRoller = null;

	@Override
	public void start(Stage primaryStage) {
		new Thread(new Runnable() {
			Data data = new Data();

			@Override
			public void run() {
				while (threadEnde) {
					data.holeDaten();

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Main.theFatCuntRoller.updateWerte();
						}
					});
				}
			}
		}).start();
		
		try {
			// Laden der FXML-Datei
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Anzeige.fxml"));
			AnchorPane root = loader.load();

			// Übergabe der AnchorPane an die Scene
			Scene scene = new Scene(root);

			// setzen der PrimaryStage
			primaryStage.setResizable(false);
			primaryStage.setTitle("Temperatura Restrictor");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
