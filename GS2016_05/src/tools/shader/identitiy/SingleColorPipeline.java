package tools.shader.identitiy;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.AbstractShaderPipeline;

public final class SingleColorPipeline extends AbstractShaderPipeline {
	
	// pipeline parameters
	private float[] color;
	
	// locations
	int locVertex = -1;
	int locMvpMatrix = -1;
	int locColorValue = -1;
	
	public SingleColorPipeline(GL3 gl, PMVMatrix matrixStack, float[] color) {
		super(gl, matrixStack);
		setColor(color);
	}
	
	public float[] getColor() {
		return color;
	}
	
	public void setColor(float[] color) {
		if (color.length != 4) {
			throw new IllegalArgumentException("Array length must be equal to 4");
		}
		this.color = color;
	}
	
	public int getLocVertex() {
		return locVertex;
	}

	@Override
	protected void getLocations() {
		locVertex = gl.glGetAttribLocation(shaderProgram, "vVertex");
		locMvpMatrix = gl.glGetUniformLocation(shaderProgram, "mvpMatrix");
		locColorValue = gl.glGetUniformLocation(shaderProgram, "vColorValue");
	}

	@Override
	protected void setUniforms() {
		gl.glUniformMatrix4fv(locMvpMatrix, 1, false, mvpMatrix);
		gl.glUniform4fv(locColorValue, 1, color, 0);
	}

	@Override
	protected String getFilenameVertexShader() {
		return "SingleColorVertex.glsl";
	}

	@Override
	protected String getFilenameFragmentShader() {
		return "SingleColorFragment.glsl";
	}

}
