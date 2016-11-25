package application;

import java.util.ArrayList;

public class TestField {

	private ArrayList<Field> fields; 
	
	public TestField(){
		fields.add(new Field(-1,1,3,2,-1,-1));
		fields.add(new Field(-1,-1,4,3,0,-1));
		fields.add(new Field(0,3,5,-1,-1,-1));
		fields.add(new Field(1,4,6,5,2,0));
		fields.add(new Field(-1,-1,-1,6,3,1));
		fields.add(new Field(3,6,-1,-1,-1,2));
		fields.add(new Field(4,-1,-1,-1,5,3));
	}
	
	
}
