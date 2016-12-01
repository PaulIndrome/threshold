package listing04;

import static com.jogamp.opengl.GL3.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
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
import tools.shader.texture.DecalTextureShaderPipeline;
import tools.textures.TextureStore;
import tools.textures.TextureStore.TextureImage;
import tools.view.AnimatedJOGLFrame;

public class Listing04 {

	static class GLEventListenerImpl implements GLEventListener, KeyListener {
		
		GL3 gl;
		DecalTextureShaderPipeline pipeline;
		
		RotatorThread rotator;

		int mode;
		int count;

		int[] VBO = new int[2];
		int[] VAO = new int[1];
		int[] textures = new int[1];
		
		private float[] largestSupportedAnisotropy = new float[1];
		BufferedImage[] images = new BufferedImage[3];
		
		int textureID = 0;
		boolean linearFiltering = false;
		boolean mipMapping = false;
		boolean anisotropicFiltering = false;
		
		float angle = 0.0F;
		
		private void specifyAndBindTexture() {
			// create pixel buffer
			int imgType = images[textureID].getType();
			int imgWidth = images[textureID].getWidth();
			int imgHeigth = images[textureID].getHeight();
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
			DataBuffer dBuffer = images[textureID].getRaster().getDataBuffer();
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
			// initialize texture
			gl.glBindTexture(GL_TEXTURE_2D, textures[0]);
			
			// Textur-Filter festlegen
			if (mipMapping) {
				if (linearFiltering) {
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
							GL_LINEAR_MIPMAP_LINEAR);
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,
							GL_LINEAR);
				} else {
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
							GL_NEAREST_MIPMAP_NEAREST);
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,
							GL_NEAREST);
				}
			} else {
				if (linearFiltering) {
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
							GL_LINEAR);
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,
							GL_LINEAR);
				} else {
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
							GL_NEAREST);
					gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,
							GL_NEAREST);
				}
			}
			
			if (anisotropicFiltering) {
				gl.glTexParameterf(GL_TEXTURE_2D,
						GL_TEXTURE_MAX_ANISOTROPY_EXT,
						largestSupportedAnisotropy[0]);
			} else {
				gl.glTexParameterf(GL_TEXTURE_2D,
						GL_TEXTURE_MAX_ANISOTROPY_EXT, 1.0F);
			}
			
			// Texturdaten hochladen
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
			if (mipMapping) {
				gl.glGenerateMipmap(GL_TEXTURE_2D);
			}
		}

		@Override
		public void display(GLAutoDrawable drawable) {
			if (rotator != null) {
				angle = rotator.getAngle();
			}
			
			if (gl != null) {
				gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

				pipeline.getMatrixStack().glPushMatrix();
				pipeline.getMatrixStack().glRotatef(angle, 0.0F, 1.0F, 0.0F);
				
				pipeline.apply();
				
				// select texture unit 0
				gl.glActiveTexture(GL_TEXTURE0);
				// bind texture to it
				synchronized (this) {
					specifyAndBindTexture();
				}
				
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
		
		private static float[] createVertices() {
			// 13 * 13 * 4 * 5
			float[] vertices = new float[3380]; 
			int pos_x, pos_y;
			int level = -2;
			int sideLength = 4;
			int k = 0;
			for (int i = -6; i <= 6; i++) {
				for (int j = -6; j <= 6; j++) {
					pos_x = i * sideLength;
					pos_y = j * sideLength;
					vertices[k++] = 0.0F;
					vertices[k++] = 0.0F;
					vertices[k++] = pos_x; 
					vertices[k++] = level; 
					vertices[k++] = pos_y + sideLength;
					vertices[k++] = 0.0F;
					vertices[k++] = 1.0F;
					vertices[k++] = pos_x; 
					vertices[k++] = level;
					vertices[k++] = pos_y;
					vertices[k++] = 1.0F; 
					vertices[k++] = 1.0F;
					vertices[k++] = pos_x + sideLength; 
					vertices[k++] = level; 
					vertices[k++] = pos_y;
					vertices[k++] = 1.0F; 
					vertices[k++] = 0.0F;
					vertices[k++] = pos_x + sideLength;
					vertices[k++] = level;
					vertices[k++] = pos_y + sideLength;
				}
			}
			return vertices;
		}
		
		private static int[] createFaces() {
			/*
			  ------> i
			 | a--d
			 | | /|
			 | |/ |
			 | b--c
			 V
			 j
			 */
			
			int k = 0;
			// 13 * 13 * 6
			int[] faces = new int[1014];
			// 13 * 13 * 4 vertices
			for (int i = 0; i < 676; i+=4) {
				int a = i;
				int b = i + 1;
				int c = i + 2;
				int d = i + 3;
				faces[k++] = a;
				faces[k++] = b;
				faces[k++] = d;
				faces[k++] = b;
				faces[k++] = c;
				faces[k++] = d;
			}
			return faces;
		}
		
		private void loadTextureImages() throws IOException {
			images[0] = TextureStore.createChessboardImage(128, 8);
			images[1] = TextureStore.readImage(TextureImage.MARBLE);
			images[2] = TextureStore.readImage(TextureImage.WOOD);
		}
		
		@Override
		public void init(GLAutoDrawable drawable) {
			if (drawable.getGL().isGL3()) {
				gl = drawable.getGL().getGL3();
				pipeline = new DecalTextureShaderPipeline(gl, new PMVMatrix());
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
				
				float eyeX = 0.0F; float eyeY = 0.0F; float eyeZ = 30.0F;
				float atX = 0.0F; float atY = 0.0F;	float atZ = 0.0F;
				float upX = 0.0F; float upY = 1.0F;	float upZ = 0.0F;
				pipeline.getMatrixStack().gluLookAt(
					eyeX, eyeY, eyeZ,
					atX, atY, atZ,
					upX, upY, upZ);
				
				// init wireframe modell	

				gl.glGenBuffers(VBO.length, VBO, 0);
				gl.glGenVertexArrays(VAO.length, VAO, 0);

				FloatBuffer vertices = Buffers.newDirectFloatBuffer(createVertices());
				vertices.rewind();
				IntBuffer faces = Buffers.newDirectIntBuffer(createFaces());
				faces.rewind();
				
				int texCoordOffset = 0;
				int vertexOffset = 2;
				int componentCount = 5;
				

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
						componentCount * Buffers.SIZEOF_FLOAT, 
						(long)(vertexOffset) * Buffers.SIZEOF_FLOAT);
				gl.glEnableVertexAttribArray(pipeline.getLocTexCoord0());
				gl.glVertexAttribPointer(
						pipeline.getLocTexCoord0(), 
						2, 
						GL_FLOAT, 
						false, 
						componentCount * Buffers.SIZEOF_FLOAT, 
						(long)(texCoordOffset * Buffers.SIZEOF_FLOAT));
				gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VBO[1]);
				gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, 
						faces.capacity() * Buffers.SIZEOF_INT, 
						faces, 
						GL_STATIC_DRAW);
				
				count = faces.capacity();
				vertices.clear();
				faces.clear();
				
				mode = GL_TRIANGLES;
				
				// load textures
				gl.glGetFloatv(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, largestSupportedAnisotropy, 0);
				
				gl.glGenTextures(textures.length, textures, 0);
				gl.glEnable(GL_TEXTURE_2D);
				try {
					loadTextureImages();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				rotator = new RotatorThread();
				rotator.setStepSize(0.2F);
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
			case KeyEvent.VK_1:
				textureID = 0;
				break;
			case KeyEvent.VK_2:
				textureID = 1;
				break;
			case KeyEvent.VK_3:
				textureID = 2;
				break;
			case KeyEvent.VK_L:
				linearFiltering = !linearFiltering;
				break;
			case KeyEvent.VK_M:
				mipMapping = !mipMapping;
				break;
			case KeyEvent.VK_A:
				anisotropicFiltering = !anisotropicFiltering;
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
					AnimatedJOGLFrame.show("Texture Filters (OpenGL 3 Core)...", caps, listener, listener, 600, 600);
					System.out.println("JOGL Version: " + JoglVersion.getInstance().getImplementationVersion() + " Build: " + JoglVersion.getInstance().getImplementationBuild());
					System.out.println("Press R to toggle Rotation");
					System.out.println("Press 1 to switch to chessboard texture");
					System.out.println("Press 2 to switch to marble texture");
					System.out.println("Press 3 to switch to wood texture");
					System.out.println("Press L to toggle linear filtering");
					System.out.println("Press M to toggle mip mapping");
					System.out.println("Press A to toggle anisotropic filtering");
				}				
			}
		});

	}
}
