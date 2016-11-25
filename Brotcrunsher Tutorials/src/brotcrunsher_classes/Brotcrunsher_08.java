package brotcrunsher_classes;

import java.util.Scanner;

public class Brotcrunsher_08 {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		int[] dreivariablen = new int[5];
		System.out.println("Vier Zahlen angeben:");
		dreivariablen[0] = s.nextInt();
		dreivariablen[1] = s.nextInt();
		dreivariablen[2] = s.nextInt();
		dreivariablen[3] = s.nextInt();
		dreivariablen[4] = dreivariablen[0]+dreivariablen[1]+dreivariablen[2]+dreivariablen[3];
		
		for (int l = 0; l<4; l++){
			System.out.println(dreivariablen[l]);	
		}
		
		System.out.println("Die Summe dieser vier Zahlen ist:");
		System.out.println(dreivariablen[4]);
	}
}
