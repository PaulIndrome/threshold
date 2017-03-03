package application;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

public class GameController {

	@FXML
	private MenuItem loadGame;
	private MenuItem newGame;
	private MenuItem saveGame;
	private MenuItem showSolution;
	private MenuItem checkSolution;
	private MenuItem exitGame;

	@FXML
	private GridPane gameGrid;
	
	
	private GameModel model = new GameModel();
	
	public void initialize(){
		
	}
	
	public void loadBoard(){
		model.loadBoard();
	}
	
	
	public void saveBoard(String filename){
		for(row[] : board[][])
	}
	

}
