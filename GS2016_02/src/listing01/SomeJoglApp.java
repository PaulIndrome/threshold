package listing01;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SomeJoglApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private GLJPanel _glPanel;

	public SomeJoglApp() {
		super();
		setTitle("First JOGL App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GLProfile.initSingleton();
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(profile);
		_glPanel = new GLJPanel(caps);
		_glPanel.addGLEventListener(new SomeGLEventListener());
		getContentPane().add(_glPanel);
		setSize(320, 240);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SomeJoglApp app = new SomeJoglApp();
				app.setVisible(true);
			}
		});

	}

}
