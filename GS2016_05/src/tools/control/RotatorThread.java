package tools.control;

public class RotatorThread extends Thread {
	private boolean running = false;

	private float stepSize = 1.0F;
	private float angle = 0.0F;
	private int sleepTime = 20;
	private boolean paused = false;

	public float getStepSize() {
		return stepSize;
	}

	public void setStepSize(float stepSize) {
		synchronized (this) {
			this.stepSize = stepSize;
		}
	}

	public float getAngle() {
		return angle;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		synchronized (this) {
			this.sleepTime = sleepTime;
		}
	}
	
	public boolean rotationIsPaused() {
		return paused;
	}
	
	public void pauseRotation() {
		synchronized (this) {
			paused = true;
		}
	}
	
	public void resumeRotation() {
		synchronized (this) {
			paused = false;
		}
	}
	
	public void toggleRotationState() {
		synchronized (this) {
			paused = !paused;
		}
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			if (!paused) {
				synchronized (this) {
					angle = (angle >= 360.0F) ? 0.0F : angle + stepSize;
				}
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
				this.interrupt();
			}
		}
	}

	public void halt() {
		running = false;
	}

}