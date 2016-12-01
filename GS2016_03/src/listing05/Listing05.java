package listing05;

import static com.jogamp.opengl.GL3.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.nio.FloatBuffer;

import javax.swing.SwingUtilities;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.identitiy.SingleColorPipeline;
import tools.view.JOGLFrame;
import tools.wireframe.Wireframe;
import tools.wireframe.WireframeGenerator;

public class Listing05 {

	static class GLEventListenerImpl implements GLEventListener {
		
		GL3 gl;
		SingleColorPipeline pipeline;

		int mode;
		int vertexCount;

		int[] VBO = new int[1];
		int[] VAO = new int[1];

		@Override
		public void display(GLAutoDrawable drawable) {
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT);

				gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
				
				gl.glBindVertexArray(VAO[0]);
				
				pipeline.getMatrixStack().glPushMatrix();
				pipeline.apply();
				gl.glDrawArrays(mode, 0, vertexCount);
				pipeline.getMatrixStack().glTranslatef(4.0F, 0.0F, 0.0F);
				pipeline.getMatrixStack().glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
				pipeline.apply();
				gl.glDrawArrays(mode, 0, vertexCount);
				pipeline.getMatrixStack().glPopMatrix();
			}
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
				float[] color = { 1.0F, 1.0F, 1.0F, 1.0F };
				gl = drawable.getGL().getGL3();
				pipeline = new SingleColorPipeline(gl, new PMVMatrix(), color);
				pipeline.init();
				if (!pipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}
				
				// preparing pipeline
				
				pipeline.getMatrixStack().glMatrixMode(GL_PROJECTION);
				pipeline.getMatrixStack().glLoadIdentity();
				float left = -10.0F; float right = 10.0F;
				float bottom = -10.0F; float top = 10.0F;
				float near = 1.0F; float far = 10.0F;
				pipeline.getMatrixStack().glOrthof(left, right, bottom, top, near, far);

				pipeline.getMatrixStack().glMatrixMode(GL_MODELVIEW);
				pipeline.getMatrixStack().glLoadIdentity();
				float eyeX = 0.0F; float eyeY = 0.0F; float eyeZ = 3.0F;
				float atX = 0.0F; float atY = 0.0F; float atZ = 0.0F;
				float upX = 0.0F; float upY = 1.0F; float upZ = 0.0F;
				pipeline.getMatrixStack().gluLookAt(
					eyeX, eyeY, eyeZ,
					atX, atY, atZ,
					upX, upY, upZ);
				
				// init cube

				gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				Wireframe cube = WireframeGenerator.generateCube(3.0F);

				FloatBuffer vertices = Buffers.newDirectFloatBuffer(cube.getVertices());
				vertices.rewind();

				gl.glBindVertexArray(VAO[0]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[0]);
				gl.glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(pipeline.getLocVertex());
				gl.glVertexAttribPointer(
						pipeline.getLocVertex(), 
						3, 
						GL_FLOAT, 
						false, 
						cube.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT, 
						(long)(cube.getVertexFormat().getVertexOffset() * Buffers.SIZEOF_FLOAT));
				
				vertexCount = vertices.capacity() / cube.getVertexFormat().getComponentCount();
				vertices.clear();
				
				mode = cube.getPrimitiveMode();
			}
		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
			if (gl != null) {
				if (width > height) {
					gl.glViewport(0, 0, height, height);
				} else {
					gl.glViewport(0, 0, width, width);
				}
			}
		}

	}

	protected static final String TITLE = "Translation followed by Rotation (OpenGL 3 Core)";

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
