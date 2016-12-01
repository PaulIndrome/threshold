package listing03;

import static com.jogamp.opengl.GL3.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
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
import tools.shader.texture.ADTextureShaderPipeline;
import tools.view.AnimatedJOGLFrame;
import tools.wireframe.Wireframe;
import tools.wireframe.WireframeGenerator;

public class Listing03 {

	static class GLEventListenerImpl implements GLEventListener, KeyListener {
		
		GL3 gl;
		ADTextureShaderPipeline pipeline;
		
		RotatorThread rotator;

		int mode;
		int count;

		int[] VBO = new int[2];
		int[] VAO = new int[1];
		int[] textures = new int[1];
		
		float angle = 0.0F;
		
		float[] materialColor = { 0.9F, 0.9F, 0.9F, 1.0F }; //{ 0.8F, 0.8F, 0.8F, 1.0F };

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

				pipeline.getMatrixStack().glPushMatrix();
				pipeline.getMatrixStack().glRotatef(angle, 1.0F, 0.0F, 0.0F);
				pipeline.getMatrixStack().glRotatef(angle, 0.0F, 0.0F, 1.0F);
				
				pipeline.apply();
				
				// select texture unit 0
				gl.glActiveTexture(GL_TEXTURE0);
				// bind texture to it
				gl.glBindTexture(GL_TEXTURE_2D, textures[0]);
				
				gl.glBindVertexArray(VAO[0]);
				
				gl.glDrawElements(mode, count, GL_UNSIGNED_INT, 0L);
				
				pipeline.getMatrixStack().glPopMatrix();
			}
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			if (gl != null) {
				gl.glUseProgram(0);
				pipeline.cleanUp();
				gl.glDeleteBuffers(VBO.length, VBO, 0);
				gl.glDeleteVertexArrays(VAO.length, VAO, 0);
				gl.glDeleteTextures(textures.length, textures, 0);
				rotator.halt();
				gl = null;
			}
		}
		
		private void printGLInfo(){
			int[] i = new int[1];
			float[] f = new float[1];
			
			System.out.println("OpenGL Version: " + gl.glGetString(GL_VERSION));
			System.out.println("OpenGL Renderer: " + gl.glGetString(GL_RENDERER));
			System.out.println("OpenGL Vendor: " + gl.glGetString(GL_VENDOR));
			gl.glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, i, 0);
			System.out.println("Number of available texture units: " + i[0]);
			gl.glGetIntegerv(GL_MAX_TEXTURE_SIZE, i, 0);
			System.out.println("Max texture size: " + i[0]);
			gl.glGetFloatv(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, f, 0);
			System.out.println("Max anisotropy level: " + f[0]);
		}

		public void createAndBindTextureObject(int textureName,
				String imageFileName) {
			BufferedImage img = null;
			try {
				// read BufferedImage from Stream
				BufferedInputStream bufferedStream = new BufferedInputStream(
						getClass().getResourceAsStream(
								"/"
										+ getClass().getPackage().getName()
												.replace('.', '/') + "/"
										+ imageFileName));
				img = ImageIO.read(bufferedStream);
				bufferedStream.close();
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
			// flip BufferedImage vertically
			AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
			tx.translate(0, -img.getHeight());
			AffineTransformOp op = new AffineTransformOp(tx,
					AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			img = op.filter(img, null);
			// create pixel buffer
			int imgType = img.getType();
			int imgWidth = img.getWidth();
			int imgHeigth = img.getHeight();
			int internalFormat;
			int pixelFormat;
			int pixelType;
			switch (imgType) {
			case BufferedImage.TYPE_INT_RGB:
				internalFormat = GL_RGB;
				pixelFormat = GL_BGRA;
				pixelType = GL_UNSIGNED_INT_8_8_8_8_REV;
				break;
			case BufferedImage.TYPE_3BYTE_BGR:
				internalFormat = GL_RGB;
				pixelFormat = GL_BGR;
				pixelType = GL_UNSIGNED_BYTE;
				break;
			case BufferedImage.TYPE_BYTE_GRAY:
				internalFormat = GL_RGB;
				pixelFormat = GL_LUMINANCE;
				pixelType = GL_UNSIGNED_BYTE;
				break;
			default:
				internalFormat = GL_RGB;
				pixelFormat = GL_BGR;
				pixelType = GL_UNSIGNED_BYTE;
				break;
			}
			DataBuffer dBuffer = img.getRaster().getDataBuffer();
			int bufferType = dBuffer.getDataType();
			Buffer pixelBuffer = null;
			switch (bufferType) {
			case DataBuffer.TYPE_BYTE:
				pixelBuffer = ByteBuffer.wrap(((DataBufferByte) dBuffer)
						.getData());
				break;
			case DataBuffer.TYPE_INT:
				pixelBuffer = IntBuffer.wrap(((DataBufferInt) dBuffer)
						.getData());
				break;
			}
			
			gl.glBindTexture(GL_TEXTURE_2D, textureName);
			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
					GL_LINEAR_MIPMAP_LINEAR);
			gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,
					GL_LINEAR_MIPMAP_LINEAR);
			gl.glTexImage2D(GL_TEXTURE_2D, // target
					0, // mip-map level
					internalFormat, // format of image data
					imgWidth, // width
					imgHeigth, // height
					0, // no border
					pixelFormat, // color arrangement for each pixel
					pixelType, // pixel data type
					pixelBuffer // image data
			);
			gl.glGenerateMipmap(GL_TEXTURE_2D);
		}
		
		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL3()) {
				gl = drawable.getGL().getGL3();
				pipeline = new ADTextureShaderPipeline(gl, new PMVMatrix(), new Material(), new Light());
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
				pipeline.getLight().setPosition(lightPosition);
				pipeline.getLight().setAmbient(lightAmbient);
				pipeline.getLight().setDiffuse(lightDiffuse);
				
				// init wireframe modell	

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				Wireframe wf = WireframeGenerator.generateCube2(1.0F);

				FloatBuffer vertices = Buffers.newDirectFloatBuffer(wf.getVertices());
				vertices.rewind();
				IntBuffer faces = Buffers.newDirectIntBuffer(wf.getFaces());
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
				gl.glEnableVertexAttribArray(pipeline.getLocTexCoord0());
				gl.glVertexAttribPointer(
						pipeline.getLocTexCoord0(), 
						2, 
						GL_FLOAT, 
						false, 
						wf.getVertexFormat().getComponentCount() * Buffers.SIZEOF_FLOAT, 
						(long)(wf.getVertexFormat().getTexCoordOffset() * Buffers.SIZEOF_FLOAT));
				
				gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VBO[1]);
				gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, faces.capacity() * Buffers.SIZEOF_INT, faces, GL_STATIC_DRAW);
				
				count = faces.capacity();
				vertices.clear();
				faces.clear();
				
				mode = wf.getPrimitiveMode();
				
				// load and specify texture
				gl.glGenTextures(textures.length, textures, 0);
				gl.glEnable(GL_TEXTURE_2D);
				createAndBindTextureObject(textures[0], "chest.PNG");
				
				rotator = new RotatorThread();
				rotator.setStepSize(0.5F);
				rotator.pauseRotation();
				rotator.start();
				
				printGLInfo();
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
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GLProfile.initSingleton();
				if (GLProfile.isAvailable(GLProfile.GL3)) {
					GLProfile profile = GLProfile.get(GLProfile.GL3);
					GLCapabilities caps = new GLCapabilities(profile);
					GLEventListenerImpl listener = new GLEventListenerImpl();
					AnimatedJOGLFrame.show("Textures & Lighting (OpenGL 3 Core)...", caps, listener, listener, 600, 600);
					System.out.println("JOGL Version: " + JoglVersion.getInstance().getImplementationVersion() + " Build: " + JoglVersion.getInstance().getImplementationBuild());
					System.out.println("Press R to toggle Rotation");
				}				
			}
		});

	}
}
