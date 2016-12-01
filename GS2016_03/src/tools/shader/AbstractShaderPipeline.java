package tools.shader;

import static com.jogamp.opengl.GL.GL_FALSE;
import static com.jogamp.opengl.GL2ES2.GL_COMPILE_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_LINK_STATUS;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.util.PMVMatrix;

public abstract class AbstractShaderPipeline {

	// Objekt der Klasse GL3 ist Schnittstelle zu OpenGL
	// OpenGL 3.0 Rendering Kontext
	protected GL3 gl;

	// Name (Nummer) des Container-Objektes für das Shader-Programm
	protected int shaderProgram;

	// matrix data
	// Matrizenspeicher
	protected PMVMatrix matrixStack = null;
	// Hilfsvariable für Gesamttransformationsmatrix
	// MVP = model view projection
	protected FloatBuffer mvpMatrix = null;

	// Hilfsvariable für Modelview- und Projection-matrix
	private float[] matrixGL_PROJECTION = new float[16];
	private float[] matrixGL_MODELVIEW = new float[16];

	// nur für Kontrolle, ob kompiliert und gelinkt wurde
	private boolean ready = false;

	public PMVMatrix getMatrixStack() {
		return this.matrixStack;
	}

	public void setMatrixStack(PMVMatrix matrixStack) {
		this.matrixStack = matrixStack;
	}

	public boolean isReady() {
		return ready;
	}

	public AbstractShaderPipeline(GL3 gl, PMVMatrix matrixStack) {
		super();
		this.gl = gl;
		this.matrixStack = matrixStack;
	}

	// wird nach dem Konstruktor aufgerufen
	public void init() {
		mvpMatrix = Buffers.newDirectFloatBuffer(16);
		ready = loadShaderProgram();
	}

	public void cleanUp() {
		// loescht Container-Objekt fuer Shader-Programm
		gl.glDeleteProgram(shaderProgram);
		gl = null;
		ready = false;
	}

	// wird vor dem Zeichnen (Drawcall) aufgerufen
	public void apply() {
		// bindet das Shader-Programm an den Rendering-Kontext
		gl.glUseProgram(shaderProgram);
		// MvP-Matrix aktualisieren
		updateMvpMatrix();
		// setzt die Uniform-Variable
		setUniforms();
	}

	private String loadGLSLSource(String filename) {
		String source = null;
		try {

			InputStream stream = getClass()
					.getResourceAsStream("/" + getClass().getPackage().getName().replace('.', '/') + "/" + filename);
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

	// Geroedel für Shader-Übersetzung
	private boolean loadShaderProgram() {
		String vertexShaderSource = loadGLSLSource(getFilenameVertexShader());
		String fragmentShaderSource = loadGLSLSource(getFilenameFragmentShader());

		if (vertexShaderSource != null && fragmentShaderSource != null) {
			int vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
			int fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

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

			gl.glDeleteShader(vertexShader);
			gl.glDeleteShader(fragmentShader);

			getLocations();

			return true;
		}

		return false;
	}

	// MvP-Matrix neu ausmultiplizieren
	private void updateMvpMatrix() {

		int positionMatrixGL_PROJECTION = matrixStack.glGetPMatrixf().position();

		// Abfrage der Projection-Matrix
		matrixStack.glGetPMatrixf().get(matrixGL_PROJECTION);
		matrixStack.glGetPMatrixf().position(positionMatrixGL_PROJECTION);
		int positionMatrixGL_MODELVIEW = matrixStack.glGetMvMatrixf().position();
		// Abfrage der Modelview-Matrix
		matrixStack.glGetMvMatrixf().get(matrixGL_MODELVIEW);
		matrixStack.glGetMvMatrixf().position(positionMatrixGL_MODELVIEW);

		// Multiplikation von Modelview und Projection Matrix und Ablage des
		// Ergebnisses in der MvP-Matrix
		mvpMatrix.put(FloatUtil.multMatrix(matrixGL_PROJECTION, matrixGL_MODELVIEW));

		mvpMatrix.rewind();
	}

	// abstract methods
	protected abstract void getLocations();

	protected abstract void setUniforms();

	protected abstract String getFilenameVertexShader();

	protected abstract String getFilenameFragmentShader();
}
