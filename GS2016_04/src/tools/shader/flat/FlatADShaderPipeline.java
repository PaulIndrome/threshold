package tools.shader.flat;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.AbstractADShaderPipeline;
import tools.shader.Light;
import tools.shader.Material;

public final class FlatADShaderPipeline extends AbstractADShaderPipeline {

	public FlatADShaderPipeline(GL3 gl, PMVMatrix matrixStack, Material material, Light light) {
		super(gl, matrixStack, material, light);
	}

	@Override
	protected String getFilenameVertexShader() {
		return "flatADShaderVertex.glsl";
	}

	@Override
	protected String getFilenameFragmentShader() {
		return "flatADShaderFragment.glsl";
	}

}
