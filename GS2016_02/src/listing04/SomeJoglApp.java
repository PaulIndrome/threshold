package listing04;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import javax.swing.SwingUtilities;

import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.opengl.GLWindow;

public class SomeJoglApp extends Frame {

	private static final long serialVersionUID = 1L;
	private GLWindow _window;
	private NewtCanvasAWT _canvas;

	public SomeJoglApp() {
		super();
		setTitle("Embedding NEWT in AWT...");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				event.getWindow().setVisible(false);
				event.getWindow().dispose();
				System.exit(0);
			}
		});
		GLProfile.initSingleton();
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(profile);
		_window = GLWindow.create(caps);
		_window.addGLEventListener(new SomeGLEventListener());
		_canvas = new NewtCanvasAWT(_window);
		add(_canvas);
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
