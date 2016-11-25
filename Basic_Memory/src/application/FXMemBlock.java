package application;

import java.io.IOException;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FXMemBlock {

	private Rectangle rectangle;

	private Color hiddenColor;
	private Color deckColor;
	private int index;

	public FXMemBlock(Pane parent, int posX, int posY, Color hc, Color dc, int index)
			throws IOException {
		this.index = index;
		rectangle = new Rectangle(36, 36);
		rectangle.setFill(dc);
		rectangle.setStroke(Color.WHITE);
		//Pane block = new Pane(rectangle);
		rectangle.setTranslateX(posX);
		rectangle.setTranslateY(posY);
		parent.getChildren().add(rectangle);
		hiddenColor = hc;
		deckColor = dc;

		rectangle.setOnMouseClicked(e -> {
			onClick();
		});

	}

	public void setHiddenColor(Color color) {
		hiddenColor = color;
	}

	public void onClick() {
			rectangle.setFill(hiddenColor);
			System.out.println("Unhiding color of Index " + index + " : " + hiddenColor.toString());
			if(MemoryController.getPicks().intValue()==0){
				MemoryController.setFirstPick(this);
				MemoryController.setPicks(1);
				this.rectangle.setDisable(true);
			} else if (MemoryController.getPicks().intValue()==1){
				MemoryController.setSecondPick(this);
				MemoryController.setPicks(2);
				this.rectangle.setDisable(true);
			} else if (MemoryController.getPicks().intValue()==2){
				MemoryController.setTempPick(this);
				MemoryController.setPicks(3);
				this.rectangle.setDisable(true);
				
				System.out.println("You shouldn't be here.");
			}
		MemoryController.setAlreadyCalled();
	}

	public void hideColor() {
		rectangle.setFill(deckColor);
		this.rectangle.setDisable(false);
	}
	
	public boolean equals(FXMemBlock fxmb){
		return this.hiddenColor == fxmb.hiddenColor;
	}
	
	public void keepColor(){
		this.deckColor = this.hiddenColor;
		this.rectangle.setVisible(false);
		this.rectangle.setDisable(true);
	}
	
	public Rectangle getRectangle(){
		return this.rectangle;
	}
	
	public int getIndex(){
		return index;
	}

}
