package tools.shader.gouraud;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.AbstractADShaderPipeline;
import tools.shader.Light;
import tools.shader.Material;

public final class SmoothADShaderPipeline extends AbstractADShaderPipeline {

	public SmoothADShaderPipeline(GL3 gl, PMVMatrix matrixStack, Material material, Light light) {
		super(gl, matrixStack, material, light);
	}

	@Override
	protected String getFilenameVertexShader() {
		return "smoothADShaderVertex.glsl";
	}

	@Override
	protected String getFilenameFragmentShader() {
		return "smoothADShaderFragment.glsl";
	}

}
