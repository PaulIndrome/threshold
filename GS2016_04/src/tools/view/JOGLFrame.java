package tools.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;


public class JOGLFrame extends Frame {
	
	private static final long serialVersionUID = 1L;
	private GLWindow window;
	private NewtCanvasAWT canvas;

	public JOGLFrame() {
		super();
	}
	
	public void init(String title, GLCapabilities caps, GLEventListener glListener, KeyListener keyListener, int width, int height) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				event.getWindow().setVisible(false);
				event.getWindow().dispose();
			}
		});
		setTitle((title == null)? "<NO TITLE>" : title);
		window = GLWindow.create(caps);
		window.addGLEventListener(glListener);
		if (keyListener != null) {
			window.addKeyListener(keyListener);
		}
		canvas = new NewtCanvasAWT(window);
		canvas.setPreferredSize(new Dimension(width, height));		
		add(canvas);			
		pack();
		setLocationRelativeTo(null);
	}
	
	// static methods
	
	public static JOGLFrame show(String title, GLCapabilities caps, GLEventListener glListener, KeyListener keyListener, int width, int height) {
		JOGLFrame frame = new JOGLFrame();
		frame.init(title, caps, glListener, keyListener, width, height);
		frame.setVisible(true);
		return frame;
	}

	
}
