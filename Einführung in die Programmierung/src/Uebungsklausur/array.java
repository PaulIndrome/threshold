package Uebungsklausur;

public class array {
	
		public static int[] meinArray = new int[5];
		
		public static void main(String[] args){
			System.out.println(meinArray[0] + " " + meinArray[1]);

			int z = 0;
			
			++z;
			
			System.out.println(z);
			
			z = z++ + ++z;
			
			System.out.println(z);
		}
		

}
