package Pr_02_package;
import vorgaben.praktikum2.DoubleSorter;
public class InsertationSort implements DoubleSorter{

	@Override
	public void sort(double[] arg0) {
		for (int i = 1; i <= arg0.length - 1; i++) {
			int j = i;
			double hilf = arg0[i];
			while (j > 0 && arg0[j - 1] > hilf) {
				arg0[j] = arg0[j - 1];
				j = j - 1;
			}
			arg0[j] = hilf;
		}
	}

}
