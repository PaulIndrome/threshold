package brotcrunsher_classes;

public class Brotcrunsher_10 {
	public static void main(String[] args){
		int x;
		x = 1;
		System.out.println("Ich bin die Main" + x);
		Auﬂen();
		Innen();
		Auﬂen();
		System.out.println("Ich bin die Main" + x);
	}
		private static void Auﬂen(){
			int x;
			x = 2;
			System.out.println("Ich bin Auﬂen" + x);
		}
		
		private static void Innen(){
			int x;
			x = 3;
			System.out.println("Ich bin Innen." + x);
		}
		
	}
