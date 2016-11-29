package LiveThreads;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadTest {

	public static void main(String[] args) {
		threadTest();
		kritischerBereich();

	}

	private static void threadTest() {
		Thread threadA = new Thread(new DateiThreadA());
		Thread threadB = new Thread(new DateiThreadB());
		threadA.start();
		threadB.start();
	}

	private static void kritischerBereich() {
		ExecutorService exe = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap();
		ReadWriteLock lock = new ReentrantReadWriteLock();

		exe.submit(() -> {
			lock.writeLock().lock();
			try {

				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				map.put("Dirk", "Pawlaszyk");
				map.put("Uwe", "Schneider");
				map.put("Knut", "Altroggen");
			} finally {
				lock.writeLock().unlock();
			}

		});

		Runnable lesen = () -> {
			lock.readLock().lock();
			try {
				System.out.println(map.get("Dirk"));
				try {
					TimeUnit.MILLISECONDS.sleep(2000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			} finally {
				lock.readLock().unlock();
			}
		};
		exe.submit(lesen);
		exe.submit(lesen);

		try {
			exe.shutdown();
			exe.awaitTermination(60, TimeUnit.SECONDS);
		} catch (Exception ex) {
			ex.printStackTrace();
			;
		}
	}
}
