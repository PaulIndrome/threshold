package listing05;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.JoglVersion;

import javax.swing.SwingUtilities;

import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.opengl.GLWindow;


import static com.jogamp.opengl.GL3.*;

public class SomeJoglApp extends Frame {

	static class GLEventListenerImpl implements GLEventListener {

		GL3 gl;
		
		private void printOpenGLInfo() {
			if (gl != null) {
				int[] iData = { 0 };
				float[] fData = { 0.0F };
				System.out.println("OpenGL Information");
				System.out.println("Vendor: " + gl.glGetString(GL_VENDOR));
				System.out.println("Renderer: " + gl.glGetString(GL_RENDERER));
				System.out.println("OpenGL Version: " + gl.glGetString(GL_VERSION));
				System.out.println("OpenGL Shading Language Version: " + gl.glGetString(GL_SHADING_LANGUAGE_VERSION));
				gl.glGetIntegerv(GL_NUM_EXTENSIONS, iData, 0);
				int numExtensions = iData[0];
				System.out.println("Number of Extensions: " + numExtensions);
				for (int i = 0; i < numExtensions; i++) {
					System.out.println(gl.glGetStringi(GL_EXTENSIONS, i));
				}
				gl.glGetIntegerv(GL_MAX_TEXTURE_SIZE, iData, 0);
				System.out.println("Max. texture size: " + iData[0] + " x " + iData[0] + " pixels");
				gl.glGetFloatv(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, fData, 0);
				System.out.println("Max. anisotropic filter level: " + fData[0]);
				System.out.println("GLSL Version: " + gl.glGetString(GL_SHADING_LANGUAGE_VERSION));
			}
		}

		@Override
		public void display(GLAutoDrawable drawable) {
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT);
			}

		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// nothing to do here

		}

		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL3()) {
				gl = drawable.getGL().getGL3();

				gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
				
				printOpenGLInfo();
			}

		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width,
				int height) {
			// nothing to do here
		}

	}

	private static final long serialVersionUID = 1L;
	private GLWindow _window;
	private NewtCanvasAWT _canvas;

	public SomeJoglApp() {
		super();
		setTitle("OpenGL Information...");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				event.getWindow().setVisible(false);
				event.getWindow().dispose();
				System.exit(0);
			}
		});
		GLProfile.initSingleton();
		if (GLProfile.isAvailable(GLProfile.GL3)) {
			GLProfile profile = GLProfile.get(GLProfile.GL3);
			GLCapabilities caps = new GLCapabilities(profile);
			_window = GLWindow.create(caps);
			_window.addGLEventListener(new GLEventListenerImpl());
			_canvas = new NewtCanvasAWT(_window);
			_canvas.setPreferredSize(new Dimension(320, 240));
			add(_canvas);
			pack();
			setLocationRelativeTo(null);

			System.out.println("JOGL build: " + JoglVersion.getInstance().getImplementationBuild());
			System.out.println("GL2 available: " + GLProfile.isAvailable(GLProfile.GL2));
			System.out.println("GL3 available: " + GLProfile.isAvailable(GLProfile.GL3));
			System.out.println("GL4 available: " + GLProfile.isAvailable(GLProfile.GL4));
			System.out.println("Is hardware accelerated: " + caps.getHardwareAccelerated());
			System.out.println("Is double buffered: " + caps.getDoubleBuffered());
			System.out.println("number of bits requested for the Z buffer: " + caps.getDepthBits());
			System.out.println("number of bits requested for the stencil buffer: " + caps.getStencilBits());
		} else {
			System.err.println("OpenGL 3 context not available!");
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
