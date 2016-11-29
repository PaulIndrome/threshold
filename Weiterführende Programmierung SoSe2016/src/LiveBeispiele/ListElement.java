package LiveBeispiele;

public class ListElement{
	private Object data =null;
	private ListElement next =null;
	
	public ListElement (Object data){
		this.data=data;
	}
	public ListElement(Object data, ListElement next){
		this.data=data;
		this.next=next;
	}
	
	public Object getData(){
		return this.data;
		
	}
	
	public ListElement getNext(){
		return this.next;
	}
}