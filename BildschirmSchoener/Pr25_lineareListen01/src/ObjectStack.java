
public class ObjectStack {
	SimpleList stack;
	
	public ObjectStack(){
		stack = new SimpleList();
	}
	
	public void push(Object data){
		stack.insertFirst(data);
	}
	
	public Object pop(){
		return stack.deleteFirst();
	}
	
	public boolean isEmpty(){
		return stack.isEmpty();
	}
	
	public String toString(){
		return stack.toString();
	}
	
}
