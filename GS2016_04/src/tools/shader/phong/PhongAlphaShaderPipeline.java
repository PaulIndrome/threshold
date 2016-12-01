package tools.shader.phong;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.AbstractADSShaderPipeline;
import tools.shader.Light;
import tools.shader.Material;

public final class PhongAlphaShaderPipeline extends AbstractADSShaderPipeline {

	public PhongAlphaShaderPipeline(GL3 gl, PMVMatrix matrixStack, Material material, Light light) {
		super(gl, matrixStack, material, light);
	}

	@Override
	protected String getFilenameVertexShader() {
		return "PhongAlphaShaderVertex.glsl";
	}

	@Override
	protected String getFilenameFragmentShader() {
		return "PhongAlphaShaderFragment.glsl";
	}
}
