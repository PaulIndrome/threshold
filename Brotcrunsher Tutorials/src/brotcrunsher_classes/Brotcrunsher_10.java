package brotcrunsher_classes;

public class Brotcrunsher_10 {
	public static void main(String[] args){
		int x;
		x = 1;
		System.out.println("Ich bin die Main" + x);
		Au�en();
		Innen();
		Au�en();
		System.out.println("Ich bin die Main" + x);
	}
		private static void Au�en(){
			int x;
			x = 2;
			System.out.println("Ich bin Au�en" + x);
		}
		
		private static void Innen(){
			int x;
			x = 3;
			System.out.println("Ich bin Innen." + x);
		}
		
	}
