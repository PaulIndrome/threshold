package listing06;

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

import tools.control.Point3f;
import tools.control.RotatorThread;
import tools.shader.Light;
import tools.shader.Material;
import tools.shader.gouraud.SmoothADShaderPipeline;
import tools.shader.identitiy.SingleColorPipeline;
import tools.view.AnimatedJOGLFrame;
import tools.wireframe.Wireframe;
import tools.wireframe.WireframeGenerator;

public class Listing06 {

	static class GLEventListenerImpl implements GLEventListener, KeyListener {
		
		GL3 gl;
		SmoothADShaderPipeline smoothShadingPipeline;
		SingleColorPipeline singleColorPipeline;
		
		RotatorThread rotator;

		int modeSphere;
		int countSphere;
		
		int modeNormalsVisualization;
		int countNormalsVisualization;
		
		int modeTangentsVisualization;
		int countTangentsVisualization;

		int[] VBO = new int[3];
		int[] VAO = new int[3];
		
		float angle = 0.0F;
		Point3f eyePosition = new Point3f(0.0F, 0.0F, 2.5F);
		
		float[] materialColor = { 0.7F, 0.7F, 0.7F, 1.0F };
		float[] red = { 1.0F, 0.0F, 0.0F, 1.0F };
		float[] lime = { 0.0F, 1.0F, 0.0F, 1.0F };

		float[] lightPosition = { -5.0F, 0.0F, 0.0F, 1.0F };
		float[] lightAmbient = { 0.2F, 0.2F, 0.2F, 1.0F };
		float[] lightDiffuse = { 1.0F, 1.0F, 1.0F, 1.0F };

		@Override
		public void display(GLAutoDrawable drawable) {
			if (rotator != null) {
				angle = rotator.getAngle();
			}
			
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				
				smoothShadingPipeline.getMatrixStack().glLoadIdentity();
				smoothShadingPipeline.getMatrixStack().gluLookAt(
						eyePosition.x, eyePosition.y, eyePosition.z, 
						0.0F, 0.0F, 0.0F, 
						0.0F, 1.0F, 0.0F);

				smoothShadingPipeline.getMatrixStack().glRotatef(270.0F, 1.0F, 0.0F, 0.0F);
				smoothShadingPipeline.getMatrixStack().glRotatef(angle, 0.0F, 0.0F, 1.0F);
				
				smoothShadingPipeline.apply();
								
				gl.glBindVertexArray(VAO[0]);
				
				gl.glDrawArrays(modeSphere, 0, countSphere);
				
				singleColorPipeline.setColor(red);
				singleColorPipeline.apply();
				
				gl.glBindVertexArray(VAO[1]);
				
				gl.glDrawArrays(modeNormalsVisualization, 0, countNormalsVisualization);
				
				singleColorPipeline.setColor(lime);
				singleColorPipeline.apply();
				
				gl.glBindVertexArray(VAO[2]);
				
				gl.glDrawArrays(modeTangentsVisualization, 0, countTangentsVisualization);
				
			}
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			if (gl != null) {
				gl.glUseProgram(0);
				smoothShadingPipeline.cleanUp();
				gl.glDeleteBuffers(VBO.length, VBO, 0);
				gl.glDeleteVertexArrays(VAO.length, VAO, 0);
				rotator.halt();
				gl = null;
			}
		}
		
		private float[] calculateVerticesForTangentsVisualization(Wireframe wf) {
			float scale = 0.1F;
			int vertexCount = wf.getVertices().length / wf.getVertexFormat().getComponentCount();
			float[] vertices = new float[vertexCount * 6];
			int k = 0, offset = 0;
			for (int i = 0; i < vertexCount; i++) {
				offset = i * wf.getVertexFormat().getComponentCount();
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset()];
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset() + 1];
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset() + 2];
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset()] + (wf.getVertices()[offset + wf.getVertexFormat().getTangentOffset()] * scale);
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset() + 1] + (wf.getVertices()[offset + wf.getVertexFormat().getTangentOffset() + 1] * scale);
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset() + 2] + (wf.getVertices()[offset + wf.getVertexFormat().getTangentOffset() + 2] * scale);
			}
			return vertices;
		}
		
		private float[] calculateVerticesForNormalsVisualization(Wireframe wf) {
			float scale = 0.1F;
			int vertexCount = wf.getVertices().length / wf.getVertexFormat().getComponentCount();
			float[] vertices = new float[vertexCount * 6];
			int k = 0, offset = 0;
			for (int i = 0; i < vertexCount; i++) {
				offset = i * wf.getVertexFormat().getComponentCount();
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset()];
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset() + 1];
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset() + 2];
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset()] + (wf.getVertices()[offset + wf.getVertexFormat().getNormalOffset()] * scale);
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset() + 1] + (wf.getVertices()[offset + wf.getVertexFormat().getNormalOffset() + 1] * scale);
				vertices[k++] = wf.getVertices()[offset + wf.getVertexFormat().getVertexOffset() + 2] + (wf.getVertices()[offset + wf.getVertexFormat().getNormalOffset() + 2] * scale);
			}
			return vertices;
		}
		
		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL3()) {
				gl = drawable.getGL().getGL3();
				PMVMatrix sharedMatrix = new PMVMatrix();
				smoothShadingPipeline = new SmoothADShaderPipeline(gl, sharedMatrix, new Material(), new Light());
				smoothShadingPipeline.init();
				if (!smoothShadingPipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}
				
				singleColorPipeline = new SingleColorPipeline(gl, sharedMatrix, materialColor);
				singleColorPipeline.init();
				if (!singleColorPipeline.isReady()) {
					throw new Error("Pipeline not ready!");
				}
				
				gl.glEnable(GL_DEPTH_TEST);
				gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
				gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
				
				// preparing pipeline
				
				smoothShadingPipeline.getMaterial().setAmbientAndDiffuse(materialColor);
				smoothShadingPipeline.getLight().setPosition(lightPosition);
				smoothShadingPipeline.getLight().setAmbient(lightAmbient);
				smoothShadingPipeline.getLight().setDiffuse(lightDiffuse);
				
				// init wireframe modell	

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				Wireframe wf = WireframeGenerator.generateSphereWithTangents(1.0F, 36, 36);

				// preparing sphere
				
				FloatBuffer vertices = Buffers.newDirectFloatBuffer(wf.getVertices());
				vertices.rewind();
				gl.glBindVertexArray(VAO[0]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[0]);
				gl.glBufferData(GL_ARRAY_BUFFER, 
						vertices.capacity() * Buffers.SIZEOF_FLOAT, 
						vertices, 
						GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(smoothShadingPipeline.getLocVertex());
				gl.glVertexAttribPointer(
						smoothShadingPipeline.getLocVertex(), 
						3, 
						GL_FLOAT, 
						false, 
						wf.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT, 
						(long)(wf.getVertexFormat().getVertexOffset() * Buffers.SIZEOF_FLOAT));
				gl.glEnableVertexAttribArray(smoothShadingPipeline.getLocNormal());
				gl.glVertexAttribPointer(
						smoothShadingPipeline.getLocNormal(), 
						3, 
						GL_FLOAT, 
						false, 
						wf.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT, 
						(long)(wf.getVertexFormat().getNormalOffset() * Buffers.SIZEOF_FLOAT));
				
				
				countSphere = vertices.capacity() / wf.getVertexFormat().getComponentCount();
				vertices.clear();
				modeSphere = wf.getPrimitiveMode();
				
				// preparing visualization of normal vectors
				
				FloatBuffer normalsVisualization = Buffers.newDirectFloatBuffer(calculateVerticesForNormalsVisualization(wf));
				normalsVisualization.rewind();
				gl.glBindVertexArray(VAO[1]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[1]);
				gl.glBufferData(GL_ARRAY_BUFFER, 
						normalsVisualization.capacity() * Buffers.SIZEOF_FLOAT, 
						normalsVisualization, 
						GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(singleColorPipeline.getLocVertex());
				gl.glVertexAttribPointer(
						singleColorPipeline.getLocVertex(), 
						3, 
						GL_FLOAT, 
						false, 
						3 * Buffers.SIZEOF_FLOAT, 
						0L);
				countNormalsVisualization = normalsVisualization.capacity() / 3;
				normalsVisualization.clear();
				modeNormalsVisualization = GL_LINES;
				
				// preparing visualisation of tangent vectors
				
				FloatBuffer tangentsVisualization = Buffers.newDirectFloatBuffer(calculateVerticesForTangentsVisualization(wf));
				tangentsVisualization.rewind();
				gl.glBindVertexArray(VAO[2]);
				gl.glBindBuffer(GL_ARRAY_BUFFER, VBO[2]);
				gl.glBufferData(GL_ARRAY_BUFFER, 
						tangentsVisualization.capacity() * Buffers.SIZEOF_FLOAT, 
						tangentsVisualization, 
						GL_STATIC_DRAW);
				gl.glEnableVertexAttribArray(singleColorPipeline.getLocVertex());
				gl.glVertexAttribPointer(
						singleColorPipeline.getLocVertex(), 
						3, 
						GL_FLOAT, 
						false, 
						3 * Buffers.SIZEOF_FLOAT, 
						0L);
				countTangentsVisualization = tangentsVisualization.capacity() / 3;
				tangentsVisualization.clear();
				modeTangentsVisualization = GL_LINES;
				
				// preparing rotator
				
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
				smoothShadingPipeline.getMatrixStack().glMatrixMode(GL_PROJECTION);
				smoothShadingPipeline.getMatrixStack().glLoadIdentity();
				smoothShadingPipeline.getMatrixStack().gluPerspective(60.0F, (float) width / (float) height, 1.0F,
						1000.0F);
				smoothShadingPipeline.getMatrixStack().glMatrixMode(GL_MODELVIEW);
			}
		}

		@Override
		public void keyPressed(KeyEvent event) {
			switch (event.getKeyCode()) {
			case KeyEvent.VK_R:
				rotator.toggleRotationState();
				return;
			}
			
			short key = event.getKeySymbol();
			switch (key) {
			case 139: // +
			case 61:
				synchronized (this) {
					if (eyePosition.z > 2.1F) {
						eyePosition.z -= 0.1F;
					}
				}
				break;
			case 140: // -
			case 45:
				synchronized (this) {
					if (eyePosition.z < 15.0F) {
						eyePosition.z += 0.1F;
					}
				}
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
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
					AnimatedJOGLFrame.show("Surface normals (red) and tangents (green) (OpenGL 3 Core)...", caps, listener, listener, 600, 600);
					System.out.println("JOGL Version: " + JoglVersion.getInstance().getImplementationVersion() + " Build: " + JoglVersion.getInstance().getImplementationBuild());
					System.out.println("Press R to toggle Rotation");
					System.out.println("Press + to zoom in");
					System.out.println("Press - to zoom out");
				}				
			}
		});

	}
}
