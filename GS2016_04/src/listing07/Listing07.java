package listing07;

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
import tools.shader.Light;
import tools.shader.Material;
import tools.shader.Materials;
import tools.shader.Materials.MaterialType;
import tools.shader.gouraud.ADSShaderPipeline;
import tools.view.AnimatedJOGLFrame;
import tools.wireframe.Wireframe;
import tools.wireframe.WireframeGenerator;
import tools.wireframe.WireframeGenerator.WireframeResource;

public class Listing07 {

	static class GLEventListenerImpl implements GLEventListener, KeyListener {

		GL3 gl;
		ADSShaderPipeline pipeline;	
		
		RotatorThread rotator;
		float angle = 0.0F;

		int mode;
		int count;

		int[] VBO = new int[2];
		int[] VAO = new int[1];
		
		Material material = Materials.getPredefinedMaterial(MaterialType.BRASS);
		
		float[] lightPosition = { -5.0F, 0.0F, 5.0F, 1.0F };
		float[] lightAmbient = { 0.2F, 0.2F, 0.2F, 1.0F };
		float[] lightDiffuse = { 0.7F, 0.7F, 0.7F, 1.0F };
		float[] lightSpecular = { 1.0F, 1.0F, 1.0F, 1.0F };

		@Override
		public void display(GLAutoDrawable drawable) {
			if (rotator != null) {
				angle = rotator.getAngle();
			}
			
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				pipeline.setMaterial(material);
				pipeline.getMatrixStack().glPushMatrix();
				pipeline.getMatrixStack().glRotatef(angle, 0.0F, 1.0F, 0.0F);
				pipeline.apply();
				gl.glBindVertexArray(VAO[0]);
				gl.glDrawElements(mode, count, GL_UNSIGNED_INT, 0L);
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
				pipeline = new ADSShaderPipeline(gl, new PMVMatrix(), material, new Light());
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
				
				float eyeX = 0.0F; float eyeY = 5.0F; float eyeZ = 15.0F;
				float atX = 0.0F; float atY = 5.0F;	float atZ = 0.0F;
				float upX = 0.0F; float upY = 1.0F;	float upZ = 0.0F;
				pipeline.getMatrixStack().gluLookAt(
					eyeX, eyeY, eyeZ,
					atX, atY, atZ,
					upX, upY, upZ);
				
				pipeline.getLight().setPosition(lightPosition);
				pipeline.getLight().setAmbient(lightAmbient);
				pipeline.getLight().setDiffuse(lightDiffuse);
				pipeline.getLight().setSpecular(lightSpecular);
				
				// init wireframe modell	

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				Wireframe wf = WireframeGenerator.generateFromResource(WireframeResource.DRAGON);

				FloatBuffer vertices = Buffers.newDirectFloatBuffer(wf.getVertices());
				vertices.rewind();
				IntBuffer faces = Buffers.newDirectIntBuffer(wf.getFaces());

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
				gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VBO[1]);
				gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, faces.capacity() * Buffers.SIZEOF_INT, faces, GL_STATIC_DRAW);
				
				count = faces.capacity();
				vertices.clear();
				faces.clear();
				
				mode = wf.getPrimitiveMode();
				
				rotator = new RotatorThread();
				rotator.setStepSize(0.5F);
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
			case KeyEvent.VK_1:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.BRASS);
				}
				break;
			case KeyEvent.VK_2:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.BRONZE);
				}
				break;
			case KeyEvent.VK_3:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.POLISHED_BRONZE);
				}
				break;
			case KeyEvent.VK_4:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.CHROME);
				}
				break;
			case KeyEvent.VK_5:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.COPPER);
				}
				break;
			case KeyEvent.VK_6:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.POLISHED_COPPER);
				}
				break;
			case KeyEvent.VK_7:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.GOLD);
				}
				break;
			case KeyEvent.VK_8:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.POLISHED_GOLD);
				}
				break;
			case KeyEvent.VK_9:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.EMERALD);
				}
				break;
			case KeyEvent.VK_0:
				synchronized (material) {
					material = Materials.getPredefinedMaterial(MaterialType.RUBY);
				}
				break;
			}	
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}

	public static final String TITLE = "Materials Demo, Per-Vertex-Lighting (OpenGL 3 Core)...";
	
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
					System.out.println();
					System.out.println("Press R to toggle Rotation");
					System.out.println("Press number key for the following materials: ");
					System.out.println("1. Brass");
					System.out.println("2. Bronze");
					System.out.println("3. Polished Bronze");
					System.out.println("4. Chrome");
					System.out.println("5. Copper");
					System.out.println("6. Polished Copper");
					System.out.println("7. Gold");
					System.out.println("8. Polished Gold");
					System.out.println("9. Emerald");
					System.out.println("0. Ruby");
				}				
			}
		});

	}
}
