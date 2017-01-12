
public class ListElement {
	public ListElement next;
	public Object data;
	
	public ListElement(Object data){
		this.data = data;
	}
	
	public ListElement(Object data, ListElement next){
		this.data = data;
		this.next = next;
	}
	
	
	
	
}
