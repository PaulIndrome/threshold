import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TableViewSample2 extends Application{

	private TableView<Item> table = new TableView<Item>();
	
	
	
	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(new Group());
		stage.setTitle("Table View Sample 2");
		stage.setWidth(450);
		stage.setHeight(550);		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
