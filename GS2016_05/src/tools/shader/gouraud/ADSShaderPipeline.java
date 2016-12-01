package tools.shader.gouraud;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.AbstractADSShaderPipeline;
import tools.shader.Light;
import tools.shader.Material;

public final class ADSShaderPipeline extends AbstractADSShaderPipeline {
	
	public ADSShaderPipeline(GL3 gl, PMVMatrix matrixStack, Material material, Light light) {
		super(gl, matrixStack, material, light);
	}

	@Override
	protected String getFilenameVertexShader() {
		return "ADSShaderVertex.glsl";
	}

	@Override
	protected String getFilenameFragmentShader() {
		return "ADSShaderFragment.glsl";
	}
}
