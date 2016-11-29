package LiveBeispiele;

import java.util.NoSuchElementException;

public class EinfachListe{
	private ListElement first;
	public EinfachListe(){
		first=null;
	}
	private EinfachListe (ListElement first){
		this.first=first;
	}
	
	public void insertFirst(Object data){
		ListElement e = new ListElement(data, first);
		first=e;
	}
	
	public Object getFirst() throws NoSuchElementException{
		if(first==null){
			throw new NoSuchElementException ("Element nicht vorhanden");
		}
		
		return first.getData();
	}
	
	public Object deleteFirst() throws NoSuchElementException{
		if (first == null){
			throw new NoSuchElementException("Element nicht vorhanden");
		}
		
		ListElement e = first;
		first=first.getNext();
		return e.getData();
	}

	public boolean isEmpty(){
		return first==null;
	}
	
	public void clear(){
		first=null;
	}
	
	public String toString(){
		String str="";
		if(first != null){
			EinfachListe rest = new EinfachListe(first.getNext());
			str = " -> " + first.getData() + rest.toString ();
		}
		
		return str;
	}
}