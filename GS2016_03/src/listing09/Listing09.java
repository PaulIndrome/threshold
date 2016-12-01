package listing09;

import static com.jogamp.opengl.GL3.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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

import tools.control.RotatorThread;
import tools.shader.identitiy.SingleColorPipeline;
import tools.view.AnimatedJOGLFrame;
import tools.wireframe.Wireframe;
import tools.wireframe.WireframeGenerator;
import tools.wireframe.WireframeGenerator.WireframeResource;

public class Listing09 {

	static class GLEventListenerImpl implements GLEventListener, KeyListener {
		
		GL3 gl;
		SingleColorPipeline pipeline;
		
		RotatorThread rotator;

		int mode;
		int count;

		int[] VBO = new int[2];
		int[] VAO = new int[1];
		
		float angle = 0.0F;
		float[] color = { 1.0F, 1.0F, 1.0F, 1.0F };

		@Override
		public void display(GLAutoDrawable drawable) {
			if (rotator != null) {
				angle = rotator.getAngle();
			}
			
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT);

				pipeline.getMatrixStack().glPushMatrix();
				pipeline.getMatrixStack().glRotatef(angle, 0.0F, 1.0F, 0.0F);
				
				gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

				pipeline.apply();
				gl.glBindVertexArray(VAO[0]);
				
				gl.glDrawElements(mode, count, GL_UNSIGNED_INT, 0L);
				
				pipeline.getMatrixStack().glPopMatrix();
			}
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			gl.glUseProgram(0);
			pipeline.cleanUp();
			gl.glDeleteBuffers(VBO.length, VBO, 0);
			gl.glDeleteVertexArrays(VAO.length, VAO, 0);
			rotator.halt();
		}

		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL3()) {
				gl = drawable.getGL().getGL3();
				pipeline = new SingleColorPipeline(gl, new PMVMatrix(), color);
				pipeline.init();
				if (!pipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}
				
				// preparing pipeline

				pipeline.getMatrixStack().glMatrixMode(GL_MODELVIEW);
				pipeline.getMatrixStack().glLoadIdentity();
				float eyeX = 0.0F; float eyeY = 0.0F; float eyeZ = 200.0F;
				float atX = 0.0F; float atY = 0.0F;	float atZ = 0.0F;
				float upX = 0.0F; float upY = 1.0F;	float upZ = 0.0F;
				pipeline.getMatrixStack().gluLookAt(
					eyeX, eyeY, eyeZ,
					atX, atY, atZ,
					upX, upY, upZ);
				
				// init wireframe modell

				gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				Wireframe teapot = WireframeGenerator.generateFromResource(WireframeResource.TEAPOT);

				FloatBuffer vertices = Buffers.newDirectFloatBuffer(teapot.getVertices());
				vertices.rewind();
				IntBuffer faces = Buffers.newDirectIntBuffer(teapot.getFaces());
				faces.rewind();

				gl.glBindVertexArray(VAO[0]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[0]);
				gl.glBufferData(GL_ARRAY_BUFFER, 
						vertices.capacity() * Buffers.SIZEOF_FLOAT, 
						vertices, 
						GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(pipeline.getLocVertex());
				gl.glVertexAttribPointer(
						pipeline.getLocVertex(), 
						3, 
						GL_FLOAT, 
						false, 
						teapot.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT, 
						(long)(teapot.getVertexFormat().getVertexOffset() * Buffers.SIZEOF_FLOAT));
				gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VBO[1]);
				gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, 
						faces.capacity() * Buffers.SIZEOF_INT, 
						faces, 
						GL_STATIC_DRAW);
				
				vertices.clear();
				count = faces.capacity();
				faces.clear();
				
				mode = teapot.getPrimitiveMode();
				
				rotator = new RotatorThread();
				rotator.start();
			}
		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
			if (gl != null) {
				gl.glViewport(0, 0, width, height);
				if (height <= 0)
					height = 1;
				pipeline.getMatrixStack().glMatrixMode(GL_PROJECTION);
				pipeline.getMatrixStack().glLoadIdentity();
				pipeline.getMatrixStack().gluPerspective(60.0F, (float) width / (float) height, 1.0F,
						1000.0F);
				pipeline.getMatrixStack().glMatrixMode(GL_MODELVIEW);
			}
		}

		@Override
		public void keyPressed(KeyEvent event) {
			switch (event.getKeyCode()) {
			case KeyEvent.VK_R:
				rotator.toggleRotationState();
				break;
			}		
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}

	protected static final String TITLE = "Rotating wireframe teapot (OpenGL 3 Core)";

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
					System.out.println("Press R to toggle Rotation");
				}				
			}
		});

	}

}
