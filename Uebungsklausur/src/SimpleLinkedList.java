
public class SimpleLinkedList {
	private ListElement first;

	public SimpleLinkedList() {
		first = null;
	}

	public boolean istLeer() {
		return first == null;
	}

	public void leeren() {
		first = null;
	}

	public String toString() {
		ListElement tempfirst = first;
		String s = "[";
		while (tempfirst.next != null) {
			s += tempfirst.data + ", ";
			tempfirst = tempfirst.next;
		}
		return s + "]";
	}

	public boolean equals(Object o) {
		SimpleLinkedList vergleicher = (SimpleLinkedList) o;
		boolean gleich = true;
		ListElement tempfirst = this.first;
		while (gleich == true)
			if (tempfirst.data == vergleicher.first.data) {
				tempfirst = tempfirst.next;
				vergleicher.first = vergleicher.first.next;
				gleich = true;
			} else {
				gleich = false;
				return false;
			}
		return true;

	}

	public Object gibErstesElement() {
		if (first == null) {
			return null;
		}
		return first;
	}

	public void einfuegenAmAnfang(Object o) {
		ListElement nowsecond = new ListElement(first.data, first.next);
		first = new ListElement(o, nowsecond);
	}

	public Object loescheErsten() {
		if (first == null) {
			return null;
		}
		Object geloescht = first.data;
		first = first.next;
		return geloescht;
	}

	public Object entnehmeLetzten() {
		if (first == null) {
			return null;
		}
		ListElement letzter = first.next;
		while (letzter.next != null) {
			letzter = letzter.next;
		}
		return letzter.data;

	}

	public void einfuegenAmEnde(Object o) {
		ListElement letzter = first.next;
		while (letzter.next != null) {
			letzter = letzter.next;
		}
		letzter.next = new ListElement(o, null);
	}

	public Object loeschenLetzten() {
		if (first == null) {
			return null;
		}
		ListElement letzter = first.next;
		while (letzter.next.next != null) {
			letzter = letzter.next;
		}
		Object geloescht = letzter.next.data;
		letzter.next = null;
		return geloescht;
	}

	public int size() {
		int size = 0;
		if (first == null) {
			return size;
		}
		size++;
		ListElement laeufer = first;
		while (laeufer.next != null) {
			laeufer = laeufer.next;
			size++;
		}
		return size;
	}

	public Object hole(int n) {
		if (n > this.size() || n < 0) {
			return null;
		}
		int position = 0;
		ListElement holer = first;
		while (position < n) {
			holer = holer.next;
			position++;
		}
		return holer.data;
	}

	public Object setze(int n, Object o){
		if (n > this.size() || n < 0) {
			return null;
		}
		Object ersetzt = this.hole(n);
		int position = 0;
		ListElement holer = first;
		while (position < n) {
			holer = holer.next;
			position++;
		}
		holer.data = o;
		return ersetzt;
	}
	
	public int posVon(Object o){
		int position = 0;
		ListElement laeufer = first;
		while(!laeufer.data.equals(o)){
			if(laeufer.next==null){
				return -1;
			}
			laeufer = laeufer.next;
			position++;
		}
		return position;
	}
	
	public Object[] toArray(){
		Object[] objekte = new Object[this.size()];
		ListElement laeufer = first;
		int index = 0;
		while(index<this.size()){
			objekte[index] = laeufer.data;
			laeufer = laeufer.next;
			index++;
		}
		return objekte;
		
	}
}
