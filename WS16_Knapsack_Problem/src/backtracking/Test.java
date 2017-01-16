package backtracking;

public class Test {

	public static void main(String[] args) {
		// boolean[] test = new boolean[10];
		// for(boolean b : test){
		// System.out.println(b);
		// }
		// Long test = new Long(100);
		// int l = Long.toBinaryString(test).length();
		// for(int i=0;i<test;i++){
		// String s = Long.toBinaryString(i);
		// int sl = l-s.length();
		// String pre = "";
		// for(int j = 0;j<sl;j++){
		// pre += "0";
		// }
		// pre += s;
		// System.out.println(pre);
		// }

		int counter = 1;
		for (boolean[] ba : generateBinaries(4)) {
			for (boolean b : ba) {
				System.out.print(b + "\t");
			}
			System.out.print("Zeile " + counter + "\n");
			counter++;
		}

	}

	public static boolean[][] generateBinaries(int n) {
		int allComb = (int) Math.pow(2, n);
		int l = Long.toBinaryString(allComb).length() - 1;
		boolean[][] binaries = new boolean[allComb][n];
		String pre = null;
		for (int i = 0; i < allComb; i++) {
			pre = "";
			String s = Long.toBinaryString(i);
			int sl = l - s.length();
			for (int j = 0; j < sl; j++) {
				pre += "0";
			}
			pre += s;
			int index = 0;
			for (char c : pre.toCharArray()) {
				binaries[i][index] = (c == '1') ? true : false;
				index++;
			}
		}

		return binaries;
	}

}
