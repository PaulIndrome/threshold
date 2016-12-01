package listing03;

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

import tools.control.RotatorThread;
import tools.shader.Light;
import tools.shader.Material;
import tools.shader.gouraud.SmoothADShaderPipeline;
import tools.view.AnimatedJOGLFrame;
import tools.wireframe.VertexFormat;
import tools.wireframe.Wireframe;

public class Listing03 {

	static class GLEventListenerImpl implements GLEventListener, KeyListener {
		
		GL3 gl;
		SmoothADShaderPipeline pipeline;
		
		RotatorThread rotator;

		int mode;
		int count;

		int[] VBO = new int[1];
		int[] VAO = new int[1];
		
		float angle = 0.0F;
		
		float[] materialAmbient = { 0.0F, 1.0F, 0.0F, 1.0F};
		float[] materialDiffuse = { 0.0F, 1.0F, 0.0F, 1.0F};

		float[] lightPosition = { -5.0F, 0.0F, 0.0F, 1.0F};
		float[] lightAmbient = { 0.2F, 0.2F, 0.2F, 1.0F};
		float[] lightDiffuse = { 1.0F, 1.0F, 1.0F, 1.0F};

		@Override
		public void display(GLAutoDrawable drawable) {
			if (rotator != null) {
				angle = rotator.getAngle();
			}
			
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

				pipeline.getMatrixStack().glPushMatrix();
				pipeline.getMatrixStack().glRotatef(angle, 1.0F, 0.0F, 0.0F);
				pipeline.getMatrixStack().glRotatef(angle, 0.0F, 0.0F, 1.0F);
				
				pipeline.apply();
				gl.glBindVertexArray(VAO[0]);
				
				gl.glDrawArrays(mode, 0, count);
				
				pipeline.getMatrixStack().glPopMatrix();
			}
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			pipeline.cleanUp();
			gl.glDeleteBuffers(VBO.length, VBO, 0);
			gl.glDeleteVertexArrays(VAO.length, VAO, 0);
		}

		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL3()) {
				gl = drawable.getGL().getGL3();
				pipeline = new SmoothADShaderPipeline(gl, new PMVMatrix(), new Material(), new Light());
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
				
				pipeline.getMaterial().setAmbient(materialAmbient);
				pipeline.getMaterial().setDiffuse(materialDiffuse);
				pipeline.getLight().setPosition(lightPosition);
				pipeline.getLight().setAmbient(lightAmbient);
				pipeline.getLight().setDiffuse(lightDiffuse);
				
				// init wireframe modell	

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				Wireframe wf = generateCube(1.0F);

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
				
				rotator = new RotatorThread();
				rotator.setStepSize(0.5F);
				rotator.pauseRotation();
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
		
		private static Wireframe generateCube(float radius) {
			float[] vertices = calculateCubeMesh(radius);
			return new Wireframe(VertexFormat.T2F_N3F_V3F, vertices, GL_TRIANGLES, null);
		}
		
		private static float[] calculateCubeMesh(float radius) {
			// Format: GL_T2F_N3F_V3F
			// 8 components * 6 vertices/side * 6 sides
			float[] cubeMesh = new float[288];
			int i = 0;
			// calculating sidelength from radius of circumscribed sphere
			float sidelength = radius * 0.57735026F;
			
			// ///////////////////////////////////////////
			// Top of cube
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			// //////////////////////////////////////////
			// Bottom of cube
			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			// /////////////////////////////////////////
			// Left side of cube
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			// Right side of cube
			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			// Front and Back
			// Front
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength; // vertex data

			// Back
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 0.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = 0.0F; // texture coords
			cubeMesh[i++] = 1.0F;
			cubeMesh[i++] = -1.0F;
			cubeMesh[i++] = -1.0F; // normals
			cubeMesh[i++] = sidelength;
			cubeMesh[i++] = -sidelength;
			cubeMesh[i++] = -sidelength; // vertex data

			return cubeMesh;
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
					AnimatedJOGLFrame.show("Solid Cube (OpenGL 3 Core)...", caps, listener, listener, 600, 600);
					System.out.println("JOGL Version: " + JoglVersion.getInstance().getImplementationVersion() + " Build: " + JoglVersion.getInstance().getImplementationBuild());
					System.out.println("Press R to toggle Rotation");
				}				
			}
		});

	}
}
