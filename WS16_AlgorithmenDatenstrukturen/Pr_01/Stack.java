import java.util.NoSuchElementException;

import vorgaben.praktikum1.GenericStack;

public class Stack<T> implements GenericStack<T>{

	private ListElement<T> first;
	
	public Stack(){
		first = new ListElement<T>();
	}
	
	public void push(T arg0){
		//System.out.println("Element in Stack gepusht.");
		first = new ListElement<T>(arg0, first);
	}
	
	public T pop() throws NoSuchElementException{
		if (first.getNext() == null){
			throw new NoSuchElementException();
		}
		ListElement<T> help = first;
		first = first.getNext();
		return help.getData();
		
	}
	
	public boolean isEmpty(){
		return first.getNext() == null;
	}
	
	
	

}
