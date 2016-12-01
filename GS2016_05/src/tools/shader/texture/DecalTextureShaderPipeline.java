package tools.shader.texture;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.AbstractShaderPipeline;

public final class DecalTextureShaderPipeline extends AbstractShaderPipeline {
	
	public DecalTextureShaderPipeline(GL3 gl, PMVMatrix matrixStack) {
		super(gl, matrixStack);
	}

	// attribute locations
	protected int locVertex = -1;
	protected int locTexCoord0 = -1;
	
	// uniform locations
	protected int locMvpMatrix = -1;
	protected int locTex0 = -1;

	public int getLocVertex() {
		return locVertex;
	}

	public int getLocTexCoord0() {
		return locTexCoord0;
	}

	@Override
	protected void getLocations() {
		locVertex = gl.glGetAttribLocation(shaderProgram, "vVertex");
		locTexCoord0 = gl.glGetAttribLocation(shaderProgram, "vTexCoord0");
		
		locMvpMatrix = gl.glGetUniformLocation(shaderProgram, "mvpMatrix");
		locTex0 = gl.glGetUniformLocation(shaderProgram, "tex0");
	}

	@Override
	protected void setUniforms() {
		gl.glUniformMatrix4fv(locMvpMatrix, 1, false, mvpMatrix);
		// set the uniform variable "sampler2D tex0" to texture unit 0
		gl.glUniform1i(locTex0, 0);
	}

	@Override
	protected String getFilenameVertexShader() {
		return "DecalTextureShaderVertex.glsl";
	}

	@Override
	protected String getFilenameFragmentShader() {
		return "DecalTextureShaderFragment.glsl";
	}

}
