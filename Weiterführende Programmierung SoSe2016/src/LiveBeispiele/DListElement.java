package LiveBeispiele;

public class DListElement {
	private Object data = null;
	private DListElement next = null; // Nachfolger
	private DListElement prev = null; // Vorgaenger
	public DListElement() {
	}
	public DListElement(Object data, DListElement prev, DListElement next) {
		this.data = data;
		this.prev = prev;
		this.next = next;
	}
	public Object getData() {
		return this.data;
	}
	public DListElement getPrev() {
		return this.prev;
	}
	public DListElement getNext() {
		return this.next;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setPrev(DListElement prev) {
		this.prev = prev;
	}
	public void setNext(DListElement next) {
		this.next = next;
	}
}