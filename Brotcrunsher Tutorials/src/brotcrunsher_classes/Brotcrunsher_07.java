package brotcrunsher_classes;

import java.util.Scanner;

public class Brotcrunsher_07 {
	public static void main(String[] args){
		/* Scanner s = new Scanner(System.in);
		int i;
		
		System.out.println("Wie alt bist du?");
		i = s.nextInt();
	
		if (i>=18)
			System.out.println("Du bist vollj�hrig.");
		else
			System.out.println("Du bist nicht vollj�hrig.");
		*/
		
		Scanner p = new Scanner(System.in);
		
		int x,y;
		System.out.println("F�r welche Zahlen sollen die Grundrechenarten durchgegangen werden?");
		System.out.print("Erste Zahl:");
		x = p.nextInt();
		System.out.print("Zweite Zahl:");
		y = p.nextInt();
		
		System.out.println("Hier bittesch�n:");
		System.out.println(x+y);
		System.out.println(x-y);
		System.out.println(x*y);
		System.out.println(x/y);
	}
}
