package Pr_02_package;
import de.beiri22.attestation.Testat;
import de.beiri22.attestation.praktika.Praktika;
import vorgaben.benchmark.Range;
import vorgaben.benchmark.Reporter;
import vorgaben.benchmark.TaskTimer;
import vorgaben.praktikum2.GraphDisplay;

public class Main02 {

	public static void main(String[] args) {
		Testat t = new Testat(Praktika.Praktikum2);
		t.addClass(InsertationSort::new);
		t.addClass(SelectionSort::new);
		t.addClass(SortTask::new);
		
		
		/*GraphDisplay gd = new GraphDisplay("SelectSort - InsertSort");
		gd.setVisible(true);
		Reporter select = gd.getReporter("selectSort");
		Reporter ins = gd.getReporter("insertSort");
		Range r = new Range(0, 25000, 150);
		SortTask st = new SortTask();
		st.setSorter(new SelectionSort());
		new TaskTimer(select, st).run(r);
		st.setSorter(new InsertationSort());
		new TaskTimer(ins, st).run(r);*/
		
		t.check();
	}

}
