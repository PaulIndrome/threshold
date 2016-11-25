package listing02;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;

public class SomeJoglApp {

	public static void main(String[] args) {
		GLProfile.initSingleton();
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(profile);
		GLWindow window = GLWindow.create(caps);
		window.addGLEventListener(new SomeGLEventListener());
		window.addWindowListener(new WindowAdapter() {
			public void windowDestroyNotify(WindowEvent e) {
				System.exit(0);
			}
		});
		window.setTitle("First NEWT App");	
		window.setSize(320, 240);
		window.setVisible(true);
	}

}
