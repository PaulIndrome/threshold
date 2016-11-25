package listing03;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;

public class SomeJoglApp {
	
	public static class DummyThread extends Thread {
		
		private boolean running = false;
		
		@Override
		public void run() {
			running = true;
			while (running) {
				try {
					Thread.sleep(200);
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
	
	static DummyThread dummy;

	public static void main(String[] args) {
		dummy = new DummyThread();
		GLProfile.initSingleton();
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(profile);
		GLWindow window = GLWindow.create(caps);
		window.addGLEventListener(new SomeGLEventListener());
		window.addWindowListener(new WindowAdapter() {
			public void windowDestroyNotify(WindowEvent e) {
				dummy.halt();
			}
		});
		window.setTitle("First NEWT App");	
		window.setSize(320, 240);
		window.setVisible(true);
		dummy.start();
	}

}
