package listing11;

import static com.jogamp.opengl.GL3.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.nio.FloatBuffer;

import javax.swing.SwingUtilities;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.DebugGL3;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.identitiy.SingleColorPipeline;
import tools.view.JOGLFrame;
import tools.wireframe.GLTools;

public class Listing11 {
	
	static final String TITLE = "Color Blending (OpenGL 3, GLSL 1.30)";

	static class GLEventListenerImpl implements GLEventListener {

		GL3 gl;
		SingleColorPipeline pipeline;

		int[] vertexCount = new int[3];
		int[] VBO = new int[3];
		int[] VAO = new int[3];
		
		float[][] colors = {
				{ 1.0F, 0.0F, 0.0F, 1.0F }, // red
				{ 0.0F, 1.0F, 0.0F, 1.0F }, // green
				{ 0.0F, 0.0F, 1.0F, 1.0F }  // blue
		};
		

		@Override
		public void display(GLAutoDrawable drawable) {
			gl.glClear(GL_COLOR_BUFFER_BIT);
			gl.glBlendEquation(GL_FUNC_REVERSE_SUBTRACT);
			gl.glBlendFunc(GL_ONE, GL_ONE);
			gl.glEnable(GL_BLEND);
			for (int i = 0; i < 3; i++) {
				pipeline.setColor(colors[i]);
				pipeline.apply();
				gl.glBindVertexArray(VAO[i]);
				gl.glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount[i]);
			}
			gl.glDisable(GL_BLEND);
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			gl.glUseProgram(0);
			pipeline.cleanUp();
			gl.glDeleteBuffers(VBO.length, VBO, 0);
			gl.glDeleteVertexArrays(VAO.length, VAO, 0);
		}

		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL3()) {
				drawable.setGL(new DebugGL3(drawable.getGL().getGL3()));
				gl = drawable.getGL().getGL3();
				gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
				
				pipeline = new SingleColorPipeline(gl, new PMVMatrix(), colors[0]);
				pipeline.init();
				if (!pipeline.isReady())
					throw new Error("Pipeline not ready!");
				
				pipeline.getMatrixStack().glMatrixMode(GL_MODELVIEW);
				pipeline.getMatrixStack().glLoadIdentity();

				// Preparing VBO, VAO, Shaders and data
				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				// prepare circles
				FloatBuffer vertices = Buffers.newDirectFloatBuffer(GLTools.
						calculateCircleAsTriangleFan(-25.0F, 25.0F, 50.0F));
				vertexCount[0] = vertices.capacity() / 2;
				gl.glBindVertexArray(VAO[0]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[0]);
				gl.glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(pipeline.getLocVertex());
				gl.glVertexAttribPointer(pipeline.getLocVertex(), 2, GL_FLOAT, false, 0, 0L);
				
				vertices = Buffers.newDirectFloatBuffer(GLTools.
						calculateCircleAsTriangleFan(25.0F, 25.0F, 50.0F));
				vertexCount[1] = vertices.capacity() / 2;
				gl.glBindVertexArray(VAO[1]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[1]);
				gl.glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(pipeline.getLocVertex());
				gl.glVertexAttribPointer(pipeline.getLocVertex(), 2, GL_FLOAT, false, 0, 0L);
				
				vertices = Buffers.newDirectFloatBuffer(GLTools.
						calculateCircleAsTriangleFan(0.0F, -25.0F, 50.0F));
				vertexCount[2] = vertices.capacity() / 2;
				gl.glBindVertexArray(VAO[2]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[2]);
				gl.glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(pipeline.getLocVertex());
				gl.glVertexAttribPointer(pipeline.getLocVertex(), 2, GL_FLOAT, false, 0, 0L);

			}

		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width,
				int height) {
			if (height <= 0)
				height = 1;
			double aspect = (double) width / (double) height;
			gl.glViewport(0, 0, width, (int) (height * aspect));
			
			pipeline.getMatrixStack().glMatrixMode(GL_PROJECTION);
			pipeline.getMatrixStack().glLoadIdentity();
			pipeline.getMatrixStack().glOrthof(-300.0F, 300.0F, -300.0F, 300.0F, -1.0F,
					1.0F);
			pipeline.getMatrixStack().glMatrixMode(GL_MODELVIEW);
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GLProfile.initSingleton();
				if (GLProfile.isAvailable(GLProfile.GL3)) {
					GLProfile profile = GLProfile.get(GLProfile.GL3);
					GLCapabilities caps = new GLCapabilities(profile);
					GLEventListenerImpl listener = new GLEventListenerImpl();
					JOGLFrame.show(TITLE, caps, listener, null, 600, 600);
					System.out.println("JOGL Version: " + JoglVersion.getInstance().getImplementationVersion() + " Build: " + JoglVersion.getInstance().getImplementationBuild());
				}				
			}
		});

	}
	
}
