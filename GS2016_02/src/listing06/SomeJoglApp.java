package listing06;

import static com.jogamp.opengl.GL2.*;
import static com.jogamp.opengl.math.FloatUtil.PI;
import static com.jogamp.opengl.math.FloatUtil.TWO_PI;
import static com.jogamp.opengl.math.FloatUtil.cos;
import static com.jogamp.opengl.math.FloatUtil.sin;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

public class SomeJoglApp extends Frame {

	static class GLEventListenerImpl implements GLEventListener {

		static float THREE_HUNDRED_SIXTY_DEGREES = TWO_PI;
		static float ONE_DEGREE = PI / 180.0F;

		GL2 gl;

		@Override
		public void display(GLAutoDrawable drawable) {
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT);
				gl.glColor3f(1.0F, 1.0F, 1.0F);
				float x, y, angle;
				float radius = 150.0F;
				gl.glBegin(GL_LINE_LOOP);
				for (angle = 0.0F; angle < THREE_HUNDRED_SIXTY_DEGREES; angle += ONE_DEGREE) {
					x = radius * cos(angle);
					y = radius * sin(angle);
					gl.glVertex2f(x, y);
				}
				gl.glEnd();
			}

		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL2()) {
				gl = drawable.getGL().getGL2();
				gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
			}

		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
			if (gl != null) {
				if (height == 0)
					height = 1;
				double aspect = (double) width / (double) height;
				gl.glViewport(0, 0, width, (int) (height * aspect));
				gl.glMatrixMode(GL_PROJECTION);
				gl.glLoadIdentity();
				gl.glOrtho(-300, 300, -300, 300, -1, 1);
				gl.glMatrixMode(GL_MODELVIEW);
			}
		}

	}

	private static final long serialVersionUID = 1L;
	private GLWindow _window;
	private NewtCanvasAWT _canvas;

	public SomeJoglApp() {
		super();
		setTitle("Circle in Immediate Mode...");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				event.getWindow().setVisible(false);
				event.getWindow().dispose();
				System.exit(0);
			}
		});
		GLProfile.initSingleton();
		if (GLProfile.isAvailable(GLProfile.GL2)) {
			GLProfile profile = GLProfile.get(GLProfile.GL2);
			GLCapabilities caps = new GLCapabilities(profile);
			_window = GLWindow.create(caps);
			_window.addGLEventListener(new GLEventListenerImpl());
			_canvas = new NewtCanvasAWT(_window);
			_canvas.setPreferredSize(new Dimension(600, 600));
			add(_canvas);
			pack();
			setLocationRelativeTo(null);
		} else {
			System.err.println("OpenGL 2 context not available!");
			System.exit(1);
		}
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
