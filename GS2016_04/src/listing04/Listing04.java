package listing04;

import static com.jogamp.opengl.GL3.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.nio.FloatBuffer;

import javax.swing.SwingUtilities;

import com.jogamp.common.nio.Buffers;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.Light;
import tools.shader.Material;
import tools.shader.flat.FlatADShaderPipeline;
import tools.shader.flat.FlatMultiColorPipeline;
import tools.shader.gouraud.SmoothADShaderPipeline;
import tools.shader.gouraud.SmoothMultiColorPipeline;
import tools.view.AnimatedJOGLFrame;
import tools.wireframe.Wireframe;
import tools.wireframe.WireframeGenerator;

public class Listing04  {

	static class GLEventListenerImpl implements GLEventListener, KeyListener {

		static final int SPHERE = 0;
		static final int TRIANGLE = 1;
		static final int SPHERE_SMOOTH = 0;
		static final int TRIANGLE_SMOOTH = 1;
		static final int SPHERE_FLAT = 0;
		static final int TRIANGLE_FLAT = 1;

		GL3 gl;
		SmoothADShaderPipeline smoothADShaderPipeline;
		SmoothMultiColorPipeline smoothMultiColorPipeline;
		FlatADShaderPipeline flatADShaderPipeline;
		FlatMultiColorPipeline flatMultiColorPipeline;

		int modeSphere;
		int countSphere;

		int modeTriangle;
		int countTriangle;

		int[] VBO = new int[2];
		int[] VAO = new int[4];

		Light light;
		Material material;
		PMVMatrix matrix;

		boolean smooth = true;

		@Override
		public void display(GLAutoDrawable drawable) {
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

				if (smooth) {
					matrix.glPushMatrix();
					matrix.glTranslatef(-1.4F, 0.0F, 0.0F);

					smoothADShaderPipeline.apply();
					gl.glBindVertexArray(VAO[SPHERE_SMOOTH]);
					gl.glDrawArrays(modeSphere, 0, countSphere);

					matrix.glPopMatrix();

					smoothMultiColorPipeline.apply();
					gl.glBindVertexArray(VAO[TRIANGLE_SMOOTH]);
					gl.glDrawArrays(modeTriangle, 0, countTriangle);
				} else {
					matrix.glPushMatrix();
					matrix.glTranslatef(-1.4F, 0.0F, 0.0F);

					flatADShaderPipeline.apply();
					gl.glBindVertexArray(VAO[SPHERE_FLAT]);
					gl.glDrawArrays(modeSphere, 0, countSphere);

					matrix.glPopMatrix();

					flatMultiColorPipeline.apply();
					gl.glBindVertexArray(VAO[TRIANGLE_FLAT]);
					gl.glDrawArrays(modeTriangle, 0, countTriangle);
				}

			}
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			gl.glUseProgram(0);
			smoothADShaderPipeline.cleanUp();
			gl.glDeleteBuffers(VBO.length, VBO, 0);
			gl.glDeleteVertexArrays(VAO.length, VAO, 0);
		}

		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL3()) {
				gl = drawable.getGL().getGL3();

				gl.glEnable(GL_DEPTH_TEST);
				gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
				gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

				// pipeline parameters

				float eyeX = 0.0F;
				float eyeY = 0.0F;
				float eyeZ = 5.0F;
				float atX = 0.0F;
				float atY = 0.0F;
				float atZ = 0.0F;
				float upX = 0.0F;
				float upY = 1.0F;
				float upZ = 0.0F;

				float[] lightPosition = { -5.0F, 0.0F, 3.0F, 1.0F };
				float[] lightAmbient = { 0.2F, 0.2F, 0.2F, 1.0F };
				float[] lightDiffuse = { 1.0F, 1.0F, 1.0F, 1.0F };

				float[] materialAmbientAndDiffuse = { 0.0F, 1.0F, 0.0F, 1.0F };

				matrix = new PMVMatrix();
				light = new Light();
				material = new Material();

				matrix.glMatrixMode(GL_MODELVIEW);
				matrix.glLoadIdentity();
				matrix.gluLookAt(eyeX, eyeY, eyeZ, atX, atY, atZ, upX, upY, upZ);

				material.setAmbientAndDiffuse(materialAmbientAndDiffuse);

				light.setPosition(lightPosition);
				light.setAmbient(lightAmbient);
				light.setDiffuse(lightDiffuse);

				// preparing pipelines
				smoothADShaderPipeline = new SmoothADShaderPipeline(gl, matrix, material, light);
				smoothADShaderPipeline.init();
				if (!smoothADShaderPipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}

				smoothMultiColorPipeline = new SmoothMultiColorPipeline(gl, matrix);
				smoothMultiColorPipeline.init();
				if (!smoothMultiColorPipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}
				
				flatADShaderPipeline = new FlatADShaderPipeline(gl, matrix, material, light);
				flatADShaderPipeline.init();
				if (!flatADShaderPipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}
				
				flatMultiColorPipeline = new FlatMultiColorPipeline(gl, matrix);
				flatMultiColorPipeline.init();
				if (!flatMultiColorPipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}

				// init sphere

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				Wireframe wf = WireframeGenerator.generateSphere(1.2F, 24, 24);

				FloatBuffer vertices = Buffers.newDirectFloatBuffer(wf.getVertices());
				vertices.rewind();

				gl.glBindVertexArray(VAO[SPHERE_SMOOTH]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[SPHERE]);
				gl.glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(smoothADShaderPipeline.getLocVertex());
				gl.glVertexAttribPointer(smoothADShaderPipeline.getLocVertex(), 3, GL_FLOAT, false,
						wf.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT,
						(long) (wf.getVertexFormat().getVertexOffset() * Buffers.SIZEOF_FLOAT));
				gl.glEnableVertexAttribArray(smoothADShaderPipeline.getLocNormal());
				gl.glVertexAttribPointer(smoothADShaderPipeline.getLocNormal(), 3, GL_FLOAT, false,
						wf.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT,
						(long) (wf.getVertexFormat().getNormalOffset() * Buffers.SIZEOF_FLOAT));
				
				gl.glBindVertexArray(VAO[SPHERE_FLAT]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[SPHERE]);
				gl.glEnableVertexAttribArray(flatADShaderPipeline.getLocVertex());
				gl.glVertexAttribPointer(flatADShaderPipeline.getLocVertex(), 3, GL_FLOAT, false,
						wf.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT,
						(long) (wf.getVertexFormat().getVertexOffset() * Buffers.SIZEOF_FLOAT));
				gl.glEnableVertexAttribArray(flatADShaderPipeline.getLocNormal());
				gl.glVertexAttribPointer(flatADShaderPipeline.getLocNormal(), 3, GL_FLOAT, false,
						wf.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT,
						(long) (wf.getVertexFormat().getNormalOffset() * Buffers.SIZEOF_FLOAT));
				

				countSphere = vertices.capacity() / wf.getVertexFormat().getComponentCount();
				vertices.clear();

				modeSphere = wf.getPrimitiveMode();

				float[] vertexDataTriangle = { 1.0F, 0.0F, 0.0F, // red
						2.0F, -1.0F, 0.0F, // vertex 1
						0.0F, 1.0F, 0.0F, // lime
						0.0F, 1.0F, 0.0F, // vertex 2
						0.0F, 0.0F, 1.0F, // blue
						0.0F, -1.0F, 0.0F }; // vertex 3
				vertices = Buffers.newDirectFloatBuffer(vertexDataTriangle);
				vertices.rewind();

				gl.glBindVertexArray(VAO[TRIANGLE_SMOOTH]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[TRIANGLE]);
				gl.glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(smoothMultiColorPipeline.getLocVertex());
				gl.glVertexAttribPointer(smoothMultiColorPipeline.getLocVertex(), 3, GL_FLOAT, false,
						6 * Buffers.SIZEOF_FLOAT, (long) (3 * Buffers.SIZEOF_FLOAT));
				gl.glEnableVertexAttribArray(smoothMultiColorPipeline.getLocColor());
				gl.glVertexAttribPointer(smoothMultiColorPipeline.getLocColor(), 3, GL_FLOAT, false,
						6 * Buffers.SIZEOF_FLOAT, (long) (0 * Buffers.SIZEOF_FLOAT));
				
				gl.glBindVertexArray(VAO[TRIANGLE_FLAT]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[TRIANGLE]);
				gl.glEnableVertexAttribArray(flatMultiColorPipeline.getLocVertex());
				gl.glVertexAttribPointer(flatMultiColorPipeline.getLocVertex(), 3, GL_FLOAT, false,
						6 * Buffers.SIZEOF_FLOAT, (long) (3 * Buffers.SIZEOF_FLOAT));
				gl.glEnableVertexAttribArray(flatMultiColorPipeline.getLocColor());
				gl.glVertexAttribPointer(flatMultiColorPipeline.getLocColor(), 3, GL_FLOAT, false,
						6 * Buffers.SIZEOF_FLOAT, (long) (0 * Buffers.SIZEOF_FLOAT));


				countTriangle = vertices.capacity() / 6;
				vertices.clear();

				modeTriangle = GL_TRIANGLES;

			}
		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
			if (gl != null) {
				gl.glViewport(0, 0, width, height);
				if (height <= 0)
					height = 1;
				matrix.glMatrixMode(GL_PROJECTION);
				matrix.glLoadIdentity();
				matrix.gluPerspective(60.0F, (float) width / (float) height, 1.0F, 1000.0F);
				matrix.glMatrixMode(GL_MODELVIEW);
			}
		}

		@Override
		public void keyPressed(KeyEvent event) {
			switch (event.getKeyCode()) {
			case KeyEvent.VK_F:
				smooth = false;
				break;
			case KeyEvent.VK_S:
				smooth = true;
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	public static final String TITLE = "Flat/Smooth Shading (OpenGL 3 Core)...";
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GLProfile.initSingleton();
				if (GLProfile.isAvailable(GLProfile.GL3)) {
					GLProfile profile = GLProfile.get(GLProfile.GL3);
					GLCapabilities caps = new GLCapabilities(profile);
					GLEventListenerImpl listener = new GLEventListenerImpl();
					AnimatedJOGLFrame.show(TITLE, caps, listener, listener, 600, 600);
					System.out.println("JOGL Version: " + JoglVersion.getInstance().getImplementationVersion() + " Build: " + JoglVersion.getInstance().getImplementationBuild());
					System.out.println("Press F to enable flat shading, press S to enable smooth shading");
				}				
			}
		});

	}
	
}
