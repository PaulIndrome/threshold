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
import com.jogamp.opengl.util.Animator;


public class AnimatedJOGLFrame extends Frame {
	
	private static Animator animator;

	private static final long serialVersionUID = 1L;
	private GLWindow window;
	private NewtCanvasAWT canvas;

	public AnimatedJOGLFrame() {
		super();
	}
	
	public void init(String title, GLCapabilities caps, GLEventListener glListener, KeyListener keyListener, int width, int height) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				event.getWindow().setVisible(false);
				stopAnimation();
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
	
	public void startAnimation() {
		if (animator == null) {
			animator = new Animator();
			animator.add(window);
		}
		if (!animator.isAnimating()) {
			animator.start();
			System.out.println("Animation started.");
		}
	}

	public void stopAnimation() {
		if (animator != null) {
			animator.stop();
			System.out.println("Animation stopped.");
			animator = null;
		}
	}
	
	// static methods
	
	public static AnimatedJOGLFrame show(String title, GLCapabilities caps, GLEventListener glListener, KeyListener keyListener, int width, int height) {
		AnimatedJOGLFrame frame = new AnimatedJOGLFrame();
		frame.init(title, caps, glListener, keyListener, width, height);
		frame.startAnimation();
		frame.setVisible(true);
		return frame;
	}

	
}
