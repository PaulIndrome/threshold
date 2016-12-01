package listing14;

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

import tools.control.RotatorThread;
import tools.shader.Light;
import tools.shader.LinearFog;
import tools.shader.Material;
import tools.shader.gouraud.ADSFoggingShaderPipeline;
import tools.view.AnimatedJOGLFrame;
import tools.wireframe.Wireframe;
import tools.wireframe.WireframeGenerator;

public class Listing14 {

	static final String TITLE = "Fogging... (OpenGL 3 Core, GLSL 1.30)";

	static class GLEventListenerImpl implements GLEventListener {
		
		GL3 gl;
		ADSFoggingShaderPipeline pipeline;
		
		RotatorThread rotator;

		int countSphere;
		int modeSphere;

		int[] VBO = new int[1];
		int[] VAO = new int[1];
		
		float angle = 0.0F;
				
		@Override
		public void display(GLAutoDrawable drawable) {
			if (rotator != null) {
				angle = rotator.getAngle();
			}
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
				pipeline.getMatrixStack().glPushMatrix();
				pipeline.getMatrixStack().glRotatef(270.0F, 1.0F, 0.0F, 0.0F);
				pipeline.getMatrixStack().glRotatef(angle, 0.0F, 0.0F, 1.0F);
				pipeline.getMatrixStack().glTranslatef(5.0F, 0.0F, 0.0F);
			
				pipeline.apply();
				gl.glBindVertexArray(VAO[0]);
				gl.glDrawArrays(modeSphere, 0, countSphere);

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
				float[] materialLime = { 0.0F, 1.0F, 0.0F, 1.0F };
				float[] fogColor = { 0.7F, 0.7F, 0.7F, 1.0F };
				
				gl = drawable.getGL().getGL3();

				gl.glEnable(GL_DEPTH_TEST);
				gl.glClearColor(fogColor[0], fogColor[1], fogColor[2], fogColor[3]);
				//gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
				pipeline = new ADSFoggingShaderPipeline(gl, new PMVMatrix(), new Material(), new Light(), new LinearFog());
				pipeline.init();
				if (!pipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}

				System.out.println("OpenGL Version: "
						+ gl.glGetString(GL_VERSION));
				
				pipeline.getMaterial().setAmbientAndDiffuse(materialLime);

				pipeline.getFog().setColor(fogColor);
				pipeline.getFog().setStart(10.0F);
				pipeline.getFog().setEnd(17.0F);
				
				pipeline.getMatrixStack().glMatrixMode(GL_MODELVIEW);
				pipeline.getMatrixStack().glLoadIdentity();
				pipeline.getMatrixStack().gluLookAt(
						15.0F, 0.0F, 0.0F, 
						0.0F, 0.0F, 0.0F, 
						0.0F, 1.0F, 0.0F);

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);
				
				// sphere
				Wireframe wf = WireframeGenerator.generateSphere(0.75F, 36, 36);

				FloatBuffer vertices = Buffers.newDirectFloatBuffer(wf.getVertices());
				vertices.rewind();

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
						wf.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT, 
						(long)(wf.getVertexFormat().getVertexOffset() * Buffers.SIZEOF_FLOAT));
				gl.glEnableVertexAttribArray(pipeline.getLocNormal());
				gl.glVertexAttribPointer(
						pipeline.getLocNormal(), 
						3, 
						GL_FLOAT, 
						false, 
						wf.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT, 
						(long)(wf.getVertexFormat().getNormalOffset() * Buffers.SIZEOF_FLOAT));
				
				countSphere = vertices.capacity() / wf.getVertexFormat().getComponentCount();
				vertices.clear();
				
				modeSphere = wf.getPrimitiveMode();
			
				rotator = new RotatorThread();
				rotator.start();
			}
		}

		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width,
				int height) {
			if (gl != null) {
				if (height == 0)
					height = 1;
				pipeline.getMatrixStack().glMatrixMode(GL_PROJECTION);
				pipeline.getMatrixStack().glLoadIdentity();
				pipeline.getMatrixStack().gluPerspective(60.0F, (float) width / (float) height,
						1.0F, 100.0F);
				pipeline.getMatrixStack().glMatrixMode(GL_MODELVIEW);
			}
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
					AnimatedJOGLFrame.show(TITLE, caps, listener, null, 600, 600);
					System.out.println("JOGL Version: " + JoglVersion.getInstance().getImplementationVersion() + " Build: " + JoglVersion.getInstance().getImplementationBuild());
				}				
			}
		});

	}

}
