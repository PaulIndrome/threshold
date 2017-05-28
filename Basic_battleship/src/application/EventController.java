package application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class EventController {

	AnchorPane root = new AnchorPane();

	EventHandler<Event> topHandler = new EventHandler<Event>() {

		@Override
		public void handle(Event event) {
			if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
				if(event.getTarget() instanceof Node){
					Node target = (Node) event.getTarget();
					while(target.getParent() != root){
						target = target.getParent();
					}
					System.out.println(target.toString());
					target.toFront();
				}
			}
			event.consume();
			
		}
	};
	
	public void initialize() {
		root.setId("The false mcRooty");

		

	}

	public void registerRoot(Node root) {
		this.root = (AnchorPane) root;
	}
	
	public void registerHandlerOnRoot(){
		root.addEventFilter(Event.ANY, topHandler);
	}

	public void makeNoise(){
		System.out.println("Eventcontroller making noise. My root has width " + root.getWidth());
	}
}
