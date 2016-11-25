package Uebungsklausur;

public class ZweiPunktEins {

	public static void main(String[] args){
		
	int[] array = new int[]{2,4,6,8};
	int j = 0;
	
	for (int i = array.length-1; i>0; i--){
		System.out.println("i =" + i);
		array[i]=j;
		System.out.println(array[i]);
		j=i;
	}
	System.out.println(array[0]);
}
	
}
