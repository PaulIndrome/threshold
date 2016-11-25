package exerc_01;

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
		// Dateinamen der Source-Files für Vertex- und Fragment-Shader
		static final String FILENAME_VERTEX_SHADER = "colorIdentityVertex.glsl";
		static final String FILENAME_FRAGMENT_SHADER = "colorIdentityFragment.glsl";

		GL3 gl; // eigentlicher Renderingkontext (OpenGL 3 Core)
		// Matrizenspeicher für Projektionsmatrix
		PMVMatrix matrixStack;
		// Thread, der einen Zähler von 0 bis 360 laufen lässt
		CounterThread counter = new CounterThread();
		// aktueller Zähler, gibt an, bis zu welchem Vertex der Kreis gezeichnet
		// werden soll
		int counterValue = 0;

		// Gesamtzahl der Vertices
		int vertexCount;

		// VBO-Namen
		int[] VBO = new int[2];
		// VAO-Namen
		int[] VAO = new int[2];

		// ID des Vertex-Shader-Container-Objektes
		int vertexShader;
		// ID des Fragment-Shader-Container-Objektes
		int fragmentShader;
		// ID des Container-Objektes der gesamten Shader-Pipeline
		int shaderProgram;

		// location des Vertex-Attributes "vVertex"
		int locVertex;
		// location der Uniform-Variablen "vColorValue"
		int locColor;
		// location der Uniform-Variablen "mvpMatrix"
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

		// lädt Shader-Source-Codes aus den TextDateien und gibt sie als String
		// zurück
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

		// Laden der Shader-Quellcodes, Kompilieren und Linken
		private boolean loadShaderProgram() {
			// LAden der Shader-Quellcodes
			String vertexShaderSource = loadGLSLSource(FILENAME_VERTEX_SHADER);
			String fragmentShaderSource = loadGLSLSource(FILENAME_FRAGMENT_SHADER);

			if (vertexShaderSource != null && fragmentShaderSource != null) {
				// Erzeugen von Container-Objekt für Vertex-Shader, ID des
				// Containers ist Rückgabewert
				vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);

				// Erzeugen von Container-Objekt für Fragment-Shader, ID des
				// Containers ist Rückgabewert
				fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

				// Übertragen des Quellcodes zu den Containern für Vertex- und
				// Fragment-Shader
				gl.glShaderSource(vertexShader, 1, new String[] { vertexShaderSource }, (int[]) null, 0);
				gl.glShaderSource(fragmentShader, 1, new String[] { fragmentShaderSource }, (int[]) null, 0);

				// Kompilieren der Shader
				gl.glCompileShader(vertexShader);
				gl.glCompileShader(fragmentShader);

				int[] statusInfo = { 0 };
				// Kompilieren erfolgreich?
				gl.glGetShaderiv(vertexShader, GL_COMPILE_STATUS, statusInfo, 0);
				if (statusInfo[0] == GL_FALSE) {
					byte[] infoLog = new byte[1024];
					// Fehlermeldung vom Compiler auslesen
					gl.glGetShaderInfoLog(vertexShader, 1024, (int[]) null, 0, infoLog, 0);
					String infoString = new String(infoLog);
					System.err.println("Failed to compile vertex shader: ");
					System.err.println(infoString);
					return false;
				}
				// Kompilieren erfolgreich?
				gl.glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, statusInfo, 0);
				if (statusInfo[0] == GL_FALSE) {
					byte[] infoLog = new byte[1024];
					// Fehlermeldung vom Compiler auslesen
					gl.glGetShaderInfoLog(fragmentShader, 1024, (int[]) null, 0, infoLog, 0);
					String infoString = new String(infoLog);
					System.err.println("Failed to compile fragment shader: ");
					System.err.println(infoString);
					return false;
				}

				// Erzeugen eines Container-Objektes für die gesamte Pipeline
				shaderProgram = gl.glCreateProgram();

				// Vertex- und Fragment-Shader werden der Pipeline zugeordnet
				gl.glAttachShader(shaderProgram, vertexShader);
				gl.glAttachShader(shaderProgram, fragmentShader);
				// Linken, Zusammenfügen der einzelnen Shader zu einem
				// Gesamtprogramm
				gl.glLinkProgram(shaderProgram);

				// Linken erfolgreich?
				gl.glGetProgramiv(shaderProgram, GL_LINK_STATUS, statusInfo, 0);
				if (statusInfo[0] == GL_FALSE) {
					byte[] infoLog = new byte[1024];
					// Fehlermeldung von Linken auslesen
					gl.glGetProgramInfoLog(shaderProgram, 1024, (int[]) null, 0, infoLog, 0);
					String infoString = new String(infoLog);
					System.err.println("Failed to link shader program: ");
					System.err.println(infoString);
					return false;
				}

				// Locations auslesen
				locVertex = gl.glGetAttribLocation(shaderProgram, "vVertex");

				// Locations der Uniform-Variablen auslesen
				locColor = gl.glGetUniformLocation(shaderProgram, "vColorValue");
				locMvpMatrix = gl.glGetUniformLocation(shaderProgram, "mvpMatrix");

				// Container-Objekte der Vertex- und Fragment-Shader können nach
				// dem Linken wieder gelöscht werden
				gl.glDeleteShader(vertexShader);
				gl.glDeleteShader(fragmentShader);

				// alles ok
				return true;
			}
			return false;
		}

		@Override
		public void display(GLAutoDrawable drawable) {
			// aktuellen Zählerstand auslesen
			if (counter != null) {
				counterValue = counter.getCounterValue();
			}
			if (gl != null) {
				// FrameBuffer loeschen
				gl.glClear(GL_COLOR_BUFFER_BIT);
				// Das Shader-Programm an den Rendering Context binden
				gl.glUseProgram(shaderProgram);

				float[] color = { 1.0F, 1.0F, 1.0F, 1.0F };

				// Uniform-Variablen setzen
				gl.glUniform4fv(locColor, 1, color, 0);
				gl.glUniformMatrix4fv(locMvpMatrix, 1, false, matrixStack.glGetPMvMatrixf());

				// VAO binden
				gl.glBindVertexArray(VAO[0]);

				// Strichstaerke
				gl.glLineWidth(3.0F);
				// Draw Call (eigentlicher Zeichenaufruf) mit Modus
				// GL_LINE_STRIP
				gl.glDrawArrays(GL_LINE_STRIP, 0, counterValue);

				gl.glBindVertexArray(VAO[1]);
				gl.glLineWidth(3.0F);
				float[] color2 = { 0.0F, 1.0F, 0.0F, 1.0F };
				gl.glUniform4fv(locColor, 1, color2, 0);
				gl.glDrawArrays(GL_LINE_STRIP, 0, counterValue);

			}

		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// VBO freigeben
			gl.glDeleteBuffers(VBO.length, VBO, 0);
			// VAO freigeben
			gl.glDeleteVertexArrays(VAO.length, VAO, 0);
			// Shader-Programm loeschen
			gl.glDeleteProgram(shaderProgram);

			// Zaehler-Thread anhalten
			counter.halt();
		}

		@Override
		public void init(GLAutoDrawable drawable) {
			// "drawable" ist die Zeichenfläche
			if (drawable.getGL().isGL3()) { // OpenGL 3 Core verfügbar?
				// Verbindung zum Rendering-Kontext herstellen
				gl = drawable.getGL().getGL3();

				// MAtrizenspeicher erzeugen
				matrixStack = new PMVMatrix();
				// Matrix für Modelltransformation ist Einheitsmatrix - keine
				// Änderung an der Geometrie (keine Verschiebung, Drehung etc.)
				matrixStack.glMatrixMode(PMVMatrix.GL_MODELVIEW);
				matrixStack.glLoadIdentity();

				// Shader laden, kompillieren, linken
				boolean shadersLoaded = loadShaderProgram();
				if (!shadersLoaded)
					System.exit(1);

				// Löschfarbe für den Framebuffer ist schwarz
				gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);

				// Erzeuge VBO (Container-Objekt für Vertices)
				gl.glGenBuffers(VBO.length, VBO, 0);
				// Erzeuge VAO (Container-Objekt Vertex-Attrib-Pointer)
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				// Berechne Position aller Vertices auf der Kreisbahn
				// {x1,y1,x2,y2 ...}
				float[] vertexData = new float[360 * 2];

				float x, y, angle;
				float radius = 150.0F; // Variable b

				// for (int i = 0; i < 360; i++) {
				// angle = i * ONE_DEGREE;
				// if(i%15==0){
				// x = (radius*0.2F)+sin(angle) * cos(angle);
				// y = (radius*0.2F)+cos(angle) * sin(angle);
				// } else {
				// x = radius * cos(angle);
				// y = radius * sin(angle);
				// }
				//
				// vertexData[2 * i] = x;
				// vertexData[2 * i + 1] = y;
				// }

				// Ab hier Aufgabenbearbeitung der Epizyklen
				float[] vertexData2 = new float[360 * 2];
				float m, a;

				for (int i = 0; i < 360; i++) {
					angle = i * ONE_DEGREE;
					if (i % 15 == 0) {
						x = (radius * 0.2F) + sin(angle) * cos(angle);
						y = (radius * 0.2F) + cos(angle) * sin(angle);
					} else {
						x = radius * cos(angle);
						y = radius * sin(angle);
					}

					vertexData[2 * i] = x;
					vertexData[2 * i + 1] = y;

						if (i % 5 == 0) {
							a = 5.0F;
						} else {
							a = 25.0F;
						}
						m = 1.0F + radius / a;
						x = m * a * cos(angle) - a * cos(m * angle);
						y = m * a * sin(angle) - a * sin(m * angle);

						vertexData2[2 * i] = x;
						vertexData2[2 * i + 1] = y;
				}

				// Lege Geometriedaten in einem "DirectFloatBuffer" ab
				// (Speicherbereich ausserhalb des Java-Heap-Space, also nicht
				// von Java verwaltet)
				FloatBuffer vertices = Buffers.newDirectFloatBuffer(vertexData);
				// Zurücksetzen des Zeigers (Speicheradresse) auf das erste
				// Element
				vertices.rewind();

				// binde das VAO an den RenderingKontext
				// das VAO speicher u.A.
				// -welches VBO enthält Geometriedaten?
				// -welche Vertex-Attrib-Pointer sind gesetzt?
				gl.glBindVertexArray(VAO[0]);
				// Binde VBO an den Rendering-Kontext; jetzt kann es gelesen
				// oder beschrieben werden
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[0]);
				// Überträgt Geometriedaten in das VBO
				gl.glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL_STATIC_DRAW);
				// Schalte Location für das Vertex-Attribut "vVertex"
				gl.glEnableVertexAttribArray(locVertex);
				// Setze Pointer für "vVertex" in das VBO
				gl.glVertexAttribPointer(locVertex, 2, GL_FLOAT, false, 2 * Buffers.SIZEOF_FLOAT, 0L);
				// Anzahl der Vertices ermitteln (360)
				vertexCount = vertices.capacity() / 2;
				// Speicherbereich des "DirectFloatBuffer" wieder freigeben
				// TODO ist das nötig?
				vertices.clear();

				vertices = Buffers.newDirectFloatBuffer(vertexData2);
				vertices.rewind();

				gl.glBindVertexArray(VAO[1]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[1]);
				gl.glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(locVertex);
				gl.glVertexAttribPointer(locVertex, 2, GL_FLOAT, false, 2 * Buffers.SIZEOF_FLOAT, 0L);
				vertexCount = vertices.capacity() / 2;
				vertices.clear();

				// Zählerthread, bis wohin wird gezählt? (360)
				counter.setMaxValue(vertexCount);
				// Immer wieder neu beginnen, wenn 360 erreicht
				counter.setRepeat(true);
				// Feuer!
				counter.start();
			}
		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
			if (gl != null) {
				if (height == 0)
					height = 1;
				double aspect = (double) width / (double) height;
				// Maße des Viewports
				gl.glViewport(0, 0, width, (int) (height * aspect));

				// Koordinatensystem definieren, orthografische Projektion
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
