package application;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class ChapterSquare {

	private Text text;
	private StackPane stack;
	private Rectangle square = new Rectangle(100, 100);;

	public ChapterSquare(Pane parent, double space, double posX, int posY, String text, Color color, boolean connected) {

		this.text = new Text(text);
		this.stack = new StackPane();

		Line to = new Line();
		Line from = new Line();
		
		double mid = parent.getHeight()/2 - 50;
		double himid = parent.getHeight()/4 - 50;
		double lomid = (parent.getHeight()/4)*3 - 50;
		
		double sqwdth = square.getWidth();
		
		switch (posY) {
		case 1:
			stack.setTranslateY(parent.getTranslateY() + himid);
			square.setStroke(color);
			if(connected){
				to = new Line(posX-space, mid+50, posX, himid+50);
				from = new Line(posX+sqwdth, himid+50, posX+sqwdth+space,mid+50);
			}
			break;
		case 2:
			stack.setTranslateY(parent.getTranslateY() + mid);
			square.setStroke(color);
			if(connected){
				to = new Line(posX-space, mid+50, posX, mid+50);
				from = new Line(posX+sqwdth, mid+50, posX+sqwdth+space,mid+50);
			}
			break;
		case 3:
			stack.setTranslateY(parent.getTranslateY() + lomid);
			square.setStroke(color);
			if(connected){
				to = new Line(posX-space, mid+50, posX, lomid+50);
				from = new Line(posX+sqwdth, lomid+50, posX+sqwdth+space,mid+50);
			}
			break;
		default:
			stack.setTranslateY(0);
			break;
		}

		this.text.setBoundsType(TextBoundsType.VISUAL);
		this.text.setWrappingWidth(square.getWidth()-10);
		
		to.setBlendMode(null);
		from.setBlendMode(null);
		to.setStroke(Color.RED);
		from.setStroke(Color.RED);
		to.setStrokeWidth(2.5);
		from.setStrokeWidth(2.5);
		
		stack.setTranslateX(posX);
		square.setStrokeWidth(2);
		square.setFill(Color.TRANSPARENT);

		stack.getChildren().addAll(square, this.text);
		parent.getChildren().addAll(stack, to, from);

	}

}
