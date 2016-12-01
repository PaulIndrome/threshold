package tools.shader.flat;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.AbstractMultiColorShaderPipeline;

public final class FlatMultiColorPipeline extends AbstractMultiColorShaderPipeline {

	public FlatMultiColorPipeline(GL3 gl, PMVMatrix matrixStack) {
		super(gl, matrixStack);
	}

	@Override
	protected String getFilenameVertexShader() {
		return "FlatMultiColorVertex.glsl";
	}

	@Override
	protected String getFilenameFragmentShader() {
		return "FlatMultiColorFragment.glsl";
	}

}
