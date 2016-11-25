package LiveThreads;

public class DateiThreadB implements Runnable {
	public void run() {
		for (int i = 0; i < 20; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			try {
				Thread.sleep((int) (Math.random() * 10000));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

		}
	}

}
