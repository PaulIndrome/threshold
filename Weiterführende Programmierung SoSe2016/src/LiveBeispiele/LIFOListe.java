package LiveBeispiele;


public class LIFOListe {
	private EinfachListe stack;
	public LIFOListe(){
		stack=new EinfachListe ();
	}
	
	public void push (Object data){
		stack.insertFirst (data);
	}
	
	public Object pop(){
		return stack.deleteFirst();
	}
	
	public String toString(){
		return stack.toString();
	}
}