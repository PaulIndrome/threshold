package exerc_02;

import static com.jogamp.opengl.GL3.*;
import static com.jogamp.opengl.math.FloatUtil.PI;
import static com.jogamp.opengl.math.FloatUtil.TWO_PI;
import static com.jogamp.opengl.math.FloatUtil.cos;
import static com.jogamp.opengl.math.FloatUtil.sin;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import javax.swing.SwingUtilities;

import com.jogamp.common.nio.Buffers;
import com.jogamp.newt.awt.NewtCanvasAWT;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.PMVMatrix;

public class SomeJoglApp extends Frame {

	static class GLEventListenerImpl implements GLEventListener {

		static float THREE_HUNDRED_SIXTY_DEGREES = TWO_PI;
		static float ONE_DEGREE = PI / 180.0F;

		static final String FILENAME_VERTEX_SHADER = "colorIdentityVertex.glsl";
		static final String FILENAME_FRAGMENT_SHADER = "colorIdentityFragment.glsl";

		GL3 gl;
		PMVMatrix matrixStack;
		CounterThread counter = new CounterThread();
		int counterValue = 0;

		int vertexCount;

		int[] VBO = new int[1];
		int[] VAO = new int[1];

		int vertexShader;
		int fragmentShader;
		int shaderProgram;
		int locVertex;
		int locRadius;
		int locColor;
		int locMvpMatrix;

		static class CounterThread extends Thread {
			private boolean running = false;

			private int maxValue = 0;
			private int counterValue = 0;
			private boolean repeat = false;
			private int sleepTime = 20;

			public int getCounterValue() {
				return counterValue;
			}

			public void setMaxValue(int maxValue) {
				synchronized (this) {
					this.maxValue = maxValue;
				}
			}

			public void setRepeat(boolean repeat) {
				synchronized (this) {
					this.repeat = repeat;
				}
			}

			public void setSleepTime(int sleepTime) {
				synchronized (this) {
					this.sleepTime = sleepTime;
				}
			}

			@Override
			public void run() {
				running = true;
				while (running) {
					synchronized (this) {
						counterValue = (counterValue < maxValue) ? counterValue + 1 : (repeat ? 0 : maxValue);
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

		private String loadGLSLSource(String filename) {
			String source = null;
			try {

				InputStream stream = getClass().getResourceAsStream(
						"/" + getClass().getPackage().getName().replace('.', '/') + "/" + filename);
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				StringBuilder sourceBuilder = new StringBuilder();
				String line = reader.readLine();
				if (line != null) {
					sourceBuilder.append(line);
					while ((line = reader.readLine()) != null) {
						sourceBuilder.append('\n');
						sourceBuilder.append(line);
					}
				}
				source = sourceBuilder.toString();
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
			return source;
		}

		private boolean loadShaderProgram() {
			String vertexShaderSource = loadGLSLSource(FILENAME_VERTEX_SHADER);
			String fragmentShaderSource = loadGLSLSource(FILENAME_FRAGMENT_SHADER);

			if (vertexShaderSource != null && fragmentShaderSource != null) {
				vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
				fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

				gl.glShaderSource(vertexShader, 1, new String[] { vertexShaderSource }, (int[]) null, 0);
				gl.glShaderSource(fragmentShader, 1, new String[] { fragmentShaderSource }, (int[]) null, 0);

				gl.glCompileShader(vertexShader);
				gl.glCompileShader(fragmentShader);

				int[] statusInfo = { 0 };
				gl.glGetShaderiv(vertexShader, GL_COMPILE_STATUS, statusInfo, 0);
				if (statusInfo[0] == GL_FALSE) {
					byte[] infoLog = new byte[1024];
					gl.glGetShaderInfoLog(vertexShader, 1024, (int[]) null, 0, infoLog, 0);
					String infoString = new String(infoLog);
					System.err.println("Failed to compile vertex shader: ");
					System.err.println(infoString);
					return false;
				}
				gl.glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, statusInfo, 0);
				if (statusInfo[0] == GL_FALSE) {
					byte[] infoLog = new byte[1024];
					gl.glGetShaderInfoLog(fragmentShader, 1024, (int[]) null, 0, infoLog, 0);
					String infoString = new String(infoLog);
					System.err.println("Failed to compile fragment shader: ");
					System.err.println(infoString);
					return false;
				}

				shaderProgram = gl.glCreateProgram();
				gl.glAttachShader(shaderProgram, vertexShader);
				gl.glAttachShader(shaderProgram, fragmentShader);
				gl.glLinkProgram(shaderProgram);

				gl.glGetProgramiv(shaderProgram, GL_LINK_STATUS, statusInfo, 0);
				if (statusInfo[0] == GL_FALSE) {
					byte[] infoLog = new byte[1024];
					gl.glGetProgramInfoLog(shaderProgram, 1024, (int[]) null, 0, infoLog, 0);
					String infoString = new String(infoLog);
					System.err.println("Failed to link shader program: ");
					System.err.println(infoString);
					return false;
				}

				locVertex = gl.glGetAttribLocation(shaderProgram, "angle");

				locRadius = gl.glGetUniformLocation(shaderProgram, "radius");
				locColor = gl.glGetUniformLocation(shaderProgram, "vColorValue");
				locMvpMatrix = gl.glGetUniformLocation(shaderProgram, "mvpMatrix");

				gl.glDeleteShader(vertexShader);
				gl.glDeleteShader(fragmentShader);

				return true;
			}
			return false;
		}

		@Override
		public void display(GLAutoDrawable drawable) {
			if (counter != null) {
				counterValue = counter.getCounterValue();
			}
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT);

				gl.glUseProgram(shaderProgram);

				float[] color = { 1.0F, 1.0F, 1.0F, 1.0F };
				
				gl.glUniform1f(locRadius, 150.0F);
				gl.glUniform4fv(locColor, 1, color, 0);
				gl.glUniformMatrix4fv(locMvpMatrix, 1, false, matrixStack.glGetPMvMatrixf());

				gl.glBindVertexArray(VAO[0]);
				gl.glLineWidth(3.0F);
				gl.glDrawArrays(GL_LINE_STRIP, 0, counterValue);

			}

		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			gl.glDeleteBuffers(VBO.length, VBO, 0);
			gl.glDeleteVertexArrays(VAO.length, VAO, 0);
			gl.glDeleteProgram(shaderProgram);

			counter.halt();
		}

		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL3()) {
				gl = drawable.getGL().getGL3();
				matrixStack = new PMVMatrix();
				matrixStack.glMatrixMode(PMVMatrix.GL_MODELVIEW);
				matrixStack.glLoadIdentity();

				boolean shadersLoaded = loadShaderProgram();
				if (!shadersLoaded)
					System.exit(1);

				gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				
				float[] vertexData = new float[360];


				for (int i = 0; i < 360; i++) {
					vertexData[i] = i * ONE_DEGREE;
				}

				FloatBuffer vertices = Buffers.newDirectFloatBuffer(vertexData);
				vertices.rewind();

				gl.glBindVertexArray(VAO[0]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[0]);
				gl.glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(locVertex);
				gl.glVertexAttribPointer(locVertex, 1, GL_FLOAT, false, Buffers.SIZEOF_FLOAT, 0L);
				vertexCount = vertices.capacity();
				vertices.clear();

				counter.setMaxValue(vertexCount);
				counter.setRepeat(true);
				counter.start();
			}
		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
			if (gl != null) {
				if (height == 0)
					height = 1;
				double aspect = (double) width / (double) height;
				gl.glViewport(0, 0, width, (int) (height * aspect));

				matrixStack.glMatrixMode(PMVMatrix.GL_PROJECTION);
				matrixStack.glLoadIdentity();
				matrixStack.glOrthof(-300.0F, 300.0F, -300.0F, 300.0F, -1.0F, 1.0F);
				matrixStack.glMatrixMode(PMVMatrix.GL_MODELVIEW);
			}
		}

	}

	private static Animator animator;

	private static final long serialVersionUID = 1L;

	private GLWindow _window;
	private NewtCanvasAWT _canvas;

	public SomeJoglApp() {
		super();
		setTitle("");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				event.getWindow().setVisible(false);
				stopAnimation();
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
			_canvas.setPreferredSize(new Dimension(600, 600));
			add(_canvas);
			pack();
			setLocationRelativeTo(null);
		} else {
			System.err.println("OpenGL 3 context not available!");
			System.exit(1);
		}
	}

	public void startAnimation() {
		if (animator == null) {
			animator = new Animator();
			animator.add(_window);
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

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SomeJoglApp app = new SomeJoglApp();
				app.startAnimation();
				app.setVisible(true);
			}
		});

	}

}
