package brotcrunsher_classes;

public class Brotcrunsher_03 {
	public static void main (String[] args){
		
		int x, y, z;
		float v;
		double w;
		x = 15;
		y = 25;
		z = y + x;
		System.out.println(y + " plus " + x + " = " + z);
		v = (float) z / x;
		System.out.println(v);
		
		y = 24;
		z = y + x;
		System.out.println(y + " plus " + x + " = " + z);
		v = (float) z / x;
		System.out.println(v);
		
		w = (float)(y * x * z) / (float) v;
		System.out.println(w);
	}
}
