public class SortedLinkedList {
	private ComparableListElement first;
	
	public SortedLinkedList(){
		first = null;
	}
	
	public boolean isEmpty(){
		return first==null;
	}
	
	public void add(Comparable data){
		ComparableListElement einfueger = new ComparableListElement(data,null);
		ComparableListElement tempfirst = first;
		ComparableListElement vorhergehender = null;
		if(this.isEmpty()){
			first = einfueger;
			return;
		}
		while(einfueger.data.compareTo(tempfirst.data)>0){
			vorhergehender = tempfirst;
			tempfirst = tempfirst.next;
			
		}
			einfueger.next = tempfirst.next;
			vorhergehender.next = einfueger;
	}
	
	public Comparable getMin(){
		return first.data;
	}
	
	public Comparable getMax(){
		ComparableListElement tempfirst = first;
		while(tempfirst.next != null){
			tempfirst = tempfirst.next;
		}
		return tempfirst.data;
	}
	
	public Comparable removeMin(){
		Comparable geloescht = first.data;
		first = first.next;
		return geloescht;
	}
	
	public boolean contains(Comparable data){
		ComparableListElement tempfirst = first;
		while(!tempfirst.data.equals(data)){
			tempfirst = tempfirst.next;
		}
		return tempfirst.data.equals(data);
	}
}
