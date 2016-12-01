package tools.shader;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

public abstract class AbstractMultiColorShaderPipeline extends AbstractShaderPipeline {

	// attribute locations
	protected int locVertex = -1;
	protected int locColor = -1;
			
	// uniform locations
	protected int locMvpMatrix = -1;

	public AbstractMultiColorShaderPipeline(GL3 gl, PMVMatrix matrixStack) {
		super(gl, matrixStack);
		// TODO Auto-generated constructor stub
	}

	public int getLocVertex() {
		return locVertex;
	}

	public int getLocColor() {
		return locColor;
	}
	
	@Override
	protected void getLocations() {
		locVertex = gl.glGetAttribLocation(shaderProgram, "vVertex");
		locColor = gl.glGetAttribLocation(shaderProgram, "vColor");
		
		locMvpMatrix = gl.glGetUniformLocation(shaderProgram, "mvpMatrix");
	}
	
	@Override
	protected void setUniforms() {
		gl.glUniformMatrix4fv(locMvpMatrix, 1, false, mvpMatrix);
	}
}
