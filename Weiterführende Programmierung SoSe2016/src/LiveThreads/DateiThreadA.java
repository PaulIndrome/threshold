package LiveThreads;

public class DateiThreadA implements Runnable {
	public void run() {
		for (int i = 0; i < 20; i++) {
			System.out.println(Thread.currentThread().getName() + " " + (new java.util.Date()));
			try {
				Thread.sleep((int) (Math.random() * 10000));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

		}

	}

}
