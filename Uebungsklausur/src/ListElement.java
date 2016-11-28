
public class ListElement {
	Object data;

	ListElement next;

	public ListElement(Object d, ListElement n) throws IllegalArgumentException {

		if (d == null)
			throw new IllegalArgumentException();

		data = d;

		next = n;

	}
}
