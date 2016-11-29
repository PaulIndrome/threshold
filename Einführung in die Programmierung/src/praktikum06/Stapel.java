package praktikum06;
import java.util.ArrayList;

public class Stapel {

	private ArrayList<Object> stapelobjekte;
	
	public Stapel(){
		stapelobjekte = new ArrayList<Object>();
	}
	
	public void push(Object element){
		stapelobjekte.add(element);
	}
	
	public Object pop(){
		if (stapelobjekte.size() == 0){
			return null;
		}
		else {
			Object o = stapelobjekte.get(stapelobjekte.size() - 1);
			stapelobjekte.remove(stapelobjekte.size() - 1);
			return o;
		}
	}
	
	public boolean isEmpty(){
		return stapelobjekte.isEmpty();
	}
	
	public String toString(){
		String o = "Die Werte in diesem Array sind: ";
		if (stapelobjekte.size() != 0){
			o = o + stapelobjekte.get(0);
			for (int i = 1 ; i < stapelobjekte.size() ; i=i+1){
			o = o + ", " + stapelobjekte.get(i);
			}
		}
		else{
			return ("In diesem Array befinden sich keine Werte.");
		}
		return o + ".";
	}
	
	
}