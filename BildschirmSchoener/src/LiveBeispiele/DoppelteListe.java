package LiveBeispiele;
public class DoppelteListe{
	private DListElement entry;
	private int size;
	public DoppelteListe(){
		entry = new DListElement();
		entry.setNext (entry);
		entry.setPrev(entry);
		size=0;
	}	
	public void addFirst(Object data){
		addBefore(data, entry.getNext());
	}
	public void addLast(Object data){
		addBefore(data, entry);
	}	
	private void addBefore (Object data, DListElement current){
		DListElement e = new DListElement (data, current.getPrev(), current);
		e.getPrev().setNext(e);	//rote Markierung an Tafel
		e.getNext().setPrev(e);
		size++;
	}	
	public Object removeFirst(){
		return removeElement(entry.getNext());
	}	
	public Object removeLast(){
		return removeElement (entry.getPrev());
	}
	private Object removeElement(DListElement current){
		Object olddata=current.getData();
		current.getPrev().setNext(current.getNext());
		current.getNext().setPrev(current.getPrev());
		current.setData(null);
		current.setNext(null);
		current.setPrev(null);
		size--;
		return olddata;
	}	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append("[");
		for( DListElement current = entry.getNext(); current!=null;current=current.getNext()){
			buf.append(current.getData() ==this ? "this List" : current.getData().toString());
		}
		buf.append("]");
		return buf.toString();
	}		
}