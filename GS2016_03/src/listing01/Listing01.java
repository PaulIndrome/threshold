package listing01;

import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.*;

import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.util.PMVMatrix;

import tools.wireframe.GLTools;

public class Listing01 {

	public static void main(String[] args) {
		// JOGL Version info
		System.out.println("JOGL build: " + JoglVersion.getInstance().getImplementationBuild());
		System.out.println();

		float[] matrix = new float[16];
		PMVMatrix matrixStack = new PMVMatrix();
		
		// orthographic projection
		{
			matrixStack.glMatrixMode(GL_PROJECTION);
			matrixStack.glLoadIdentity();
			float left = -100.0F;
			float right = 100.0F;
			float bottom = -100.0F;
			float top = 100.0F;
			float near = 2.0F;
			float far = 50.0F;
			matrixStack.glOrthof(left, right, bottom, top, near, far);
			matrixStack.glGetFloatv(GL_PROJECTION_MATRIX, matrix, 0);
			System.out.println(GLTools.matrixToString(matrix));
		}
		
		// perspective projection
		{
			matrixStack.glMatrixMode(GL_PROJECTION);
			matrixStack.glLoadIdentity();
			float left = -5.0F; float right = 5F;
			float bottom = -5.0F; float top = 5F;
			float near = 2.0F; float far = 50.0F;
			matrixStack.glFrustumf(left, right, bottom, top, near, far);
			matrixStack.glGetFloatv(GL_PROJECTION_MATRIX, matrix, 0);
			System.out.println(GLTools.matrixToString(matrix));
		}
		
		// perspective projection
		{
			matrixStack.glMatrixMode(GL_PROJECTION);
			matrixStack.glLoadIdentity();
			float fovy = 60.0F;
			float aspect = 16.0F / 9.0F;
			float near = 2.0F; float far = 50.0F;
			matrixStack.gluPerspective(fovy, aspect, near, far);
			matrixStack.glGetFloatv(GL_PROJECTION_MATRIX, matrix, 0);
			System.out.println(GLTools.matrixToString(matrix));
		}
		
		// change of observer position
		{
			matrixStack.glMatrixMode(GL_MODELVIEW);
			matrixStack.glLoadIdentity();
			float eyeX = 0.0F; float eyeY = 0.0F; float eyeZ = 20.0F;
			float atX = 0.0F; float atY = 0.0F; float atZ = 0.0F;
			float upX = 1.0F; float upY = 0.0F; float upZ = 0.0F;
			matrixStack.gluLookAt(eyeX, eyeY, eyeZ, atX, atY, atZ, upX, upY, upZ);
			matrixStack.glGetFloatv(GL_MODELVIEW_MATRIX, matrix, 0);
			System.out.println(GLTools.matrixToString(matrix));
		}
		

		matrixStack.glMatrixMode(GL_MODELVIEW);
		matrixStack.glLoadIdentity();

		matrixStack.glGetFloatv(GL_MODELVIEW_MATRIX, matrix, 0);
		System.out.println(GLTools.matrixToString(matrix));

		// translation
		{
			float x = 1.0F;
			float y = 2.0F;
			float z = 3.0F;
			matrixStack.glMatrixMode(GL_MODELVIEW);
			matrixStack.glLoadIdentity();
			matrixStack.glTranslatef(x, y, z);

			matrixStack.glGetFloatv(GL_MODELVIEW_MATRIX, matrix, 0);
			System.out.println(GLTools.matrixToString(matrix));
		}

		// rotation
		{
			matrixStack.glMatrixMode(GL_MODELVIEW);
			matrixStack.glLoadIdentity();
			
			float alpha = 45.0F;
			float x = 0.0F;
			float y = 0.0F;
			float z = 1.0F;
			matrixStack.glRotatef(alpha, x, y, z);
			
			matrixStack.glGetFloatv(GL_MODELVIEW_MATRIX, matrix, 0);
			System.out.println(GLTools.matrixToString(matrix));
		}

		

		// scaling
		{
			matrixStack.glMatrixMode(GL_MODELVIEW);
			matrixStack.glLoadIdentity();
			
			float x = 2.0F;
			float y = 2.0F;
			float z = 2.0F;
			matrixStack.glScalef(x, y, z);

			matrixStack.glGetFloatv(GL_MODELVIEW_MATRIX, matrix, 0);
			System.out.println(GLTools.matrixToString(matrix));
		}
		
		// Matrix multiplication
		{
			float[] matrixGL_PROJECTION = new float[16];
			float[] matrixGL_MODELVIEW = new float[16];
			
			matrixStack.glGetFloatv(GL_PROJECTION_MATRIX, matrixGL_PROJECTION, 0);
			matrixStack.glGetFloatv(GL_MODELVIEW_MATRIX, matrixGL_MODELVIEW, 0);
			
			matrix = FloatUtil.multMatrix(matrixGL_PROJECTION, matrixGL_MODELVIEW);
			System.out.println(GLTools.matrixToString(matrix));
		}
		
		// Matrix multiplication; use of FloatBuffers
		{
			
			float[] matrixGL_PROJECTION = new float[16];
			float[] matrixGL_MODELVIEW = new float[16];
			
			FloatBuffer mvpMatrix = Buffers.newDirectFloatBuffer(16);
			
			int positionMatrixGL_PROJECTION = matrixStack.glGetPMatrixf()
					.position();
			
			matrixStack.glGetPMatrixf().get(matrixGL_PROJECTION);
			matrixStack.glGetPMatrixf().position(positionMatrixGL_PROJECTION);
			int positionMatrixGL_MODELVIEW = matrixStack.glGetMvMatrixf()
					.position();
			matrixStack.glGetMvMatrixf().get(matrixGL_MODELVIEW);
			matrixStack.glGetMvMatrixf().position(positionMatrixGL_MODELVIEW);
			mvpMatrix.put(FloatUtil.multMatrix(matrixGL_PROJECTION,
					matrixGL_MODELVIEW));
			
			mvpMatrix.rewind();
			
			mvpMatrix.get(matrix);
			
			System.out.println(GLTools.matrixToString(matrix));
		}

	}

}
