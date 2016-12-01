package listing05;

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
import tools.shader.gouraud.ADSShaderPipeline;
import tools.view.JOGLFrame;
import tools.wireframe.Wireframe;
import tools.wireframe.WireframeGenerator;

public class Listing05 {

	static class GLEventListenerImpl implements GLEventListener, KeyListener {

		GL3 gl;
		ADSShaderPipeline pipeline;		

		int mode;
		int count;

		int[] VBO = new int[1];
		int[] VAO = new int[1];
		
		float[] materialColor = { 0.0F, 1.0F, 0.0F, 1.0F };
		float[] materialSpecular = { 1.0F, 1.0F, 1.0F, 1.0F };
		float materialShininess = 128.0F;
		float[] lightPosition = { -5.0F, 0.0F, 5.0F, 1.0F };
		float[] lightAmbient = { 0.2F, 0.2F, 0.2F, 1.0F };
		float[] lightDiffuse = { 0.7F, 0.7F, 0.7F, 1.0F };
		float[] lightSpecular = { 1.0F, 1.0F, 1.0F, 1.0F };

		@Override
		public void display(GLAutoDrawable drawable) {
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				
				pipeline.apply();
				gl.glBindVertexArray(VAO[0]);
				
				gl.glDrawArrays(mode, 0, count);
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
				gl = drawable.getGL().getGL3();
				pipeline = new ADSShaderPipeline(gl, new PMVMatrix(), new Material(), new Light());
				pipeline.init();
				if (!pipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}
				
				gl.glEnable(GL_DEPTH_TEST);
				gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
				gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
				
				// preparing pipeline

				pipeline.getMatrixStack().glMatrixMode(GL_MODELVIEW);
				pipeline.getMatrixStack().glLoadIdentity();
				
				float eyeX = 0.0F; float eyeY = 0.0F; float eyeZ = 3.0F;
				float atX = 0.0F; float atY = 0.0F;	float atZ = 0.0F;
				float upX = 0.0F; float upY = 1.0F;	float upZ = 0.0F;
				pipeline.getMatrixStack().gluLookAt(
					eyeX, eyeY, eyeZ,
					atX, atY, atZ,
					upX, upY, upZ);
				
				pipeline.getMaterial().setAmbientAndDiffuse(materialColor);
				pipeline.getMaterial().setSpecular(materialSpecular);
				pipeline.getMaterial().setShininess(materialShininess);
				pipeline.getLight().setPosition(lightPosition);
				pipeline.getLight().setAmbient(lightAmbient);
				pipeline.getLight().setDiffuse(lightDiffuse);
				pipeline.getLight().setSpecular(lightSpecular);
				
				// init wireframe modell	

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				Wireframe wf = WireframeGenerator.generateSphere(1.0F, 36, 36);

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
				
				count = vertices.capacity() / wf.getVertexFormat().getComponentCount();
				vertices.clear();
				
				mode = wf.getPrimitiveMode();
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
					
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	public static final String TITLE = "ADS Shader using Halfway Vector (OpenGL 3 Core)...";

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GLProfile.initSingleton();
				if (GLProfile.isAvailable(GLProfile.GL3)) {
					GLProfile profile = GLProfile.get(GLProfile.GL3);
					GLCapabilities caps = new GLCapabilities(profile);
					GLEventListenerImpl listener = new GLEventListenerImpl();
					JOGLFrame.show(TITLE, caps, listener, listener, 600, 600);
					System.out.println("JOGL Version: " + JoglVersion.getInstance().getImplementationVersion() + " Build: " + JoglVersion.getInstance().getImplementationBuild());
				}				
			}
		});

	}
}
