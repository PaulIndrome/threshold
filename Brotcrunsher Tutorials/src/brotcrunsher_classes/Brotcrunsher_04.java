package brotcrunsher_classes;

public class Brotcrunsher_04 {
	public static void main(String[] args){
		
		int x, y, z;
		x = 1;
		y = 2;
		
		System.out.println("x=" + x + ", y=" + y);
	
		z = y;
		y = x;
		x = z;
		
		System.out.println("x=" + x + ", y=" + y);
		
		System.out.println("Ab hier ohne z-Variable");
		
		x = 1;
		y = 2;
		
		System.out.println("x=" + x + ", y=" + y);
		
		// Fuck the z integer! I can do it without! Handstand, no-hands, flashbang throw!
		
		y = y / y;
		x = x + y;
		
		System.out.println("x=" + x + ", y=" + y);
		
	}
}
