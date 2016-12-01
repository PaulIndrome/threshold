package tools.shader.gouraud;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.AbstractMultiColorShaderPipeline;

public final class SmoothMultiColorPipeline extends AbstractMultiColorShaderPipeline {

	public SmoothMultiColorPipeline(GL3 gl, PMVMatrix matrixStack) {
		super(gl, matrixStack);
	}

	@Override
	protected String getFilenameVertexShader() {
		return "smoothMultiColorVertex.glsl";
	}

	@Override
	protected String getFilenameFragmentShader() {
		return "smoothMultiColorFragment.glsl";
	}

}
