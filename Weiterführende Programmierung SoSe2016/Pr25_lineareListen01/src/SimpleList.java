import java.util.NoSuchElementException;

public class SimpleList {

	public ListElement first;

	public SimpleList() {
		first = null;
	}

	public SimpleList(ListElement first) {
		this.first = first;
	}

	public boolean isEmpty() {
		if (first == null) {
			return true;
		} else {
			return false;
		}
	}

	public void clear() {
		first = null;
	}

	public Object getFirst() {
		return first;
	}

	public void insertFirst(Object data) {
		ListElement oldfirst = first;
		first = new ListElement(data, oldfirst);
	}

	public Object deleteFirst() throws NoSuchElementException {
		if (first == null) {
			throw new NoSuchElementException();
		}
		Object f = first.data;
		first = first.next;
		return f;
	}

	public String toString() {
		String s = "";
		if (first != null) {
			SimpleList rest = new SimpleList(first.next);
			s = " -> " + first.data + rest.toString();
		}
		return s;
	}
}
