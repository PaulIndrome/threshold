package tools.shader.texture;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

import tools.shader.AbstractShaderPipeline;
import tools.shader.Light;
import tools.shader.Material;

public final class ColorAndNormalMapShaderPipeline extends AbstractShaderPipeline {
	
	// attribute locations
	protected int locVertex = -1;
	protected int locNormal = -1;
	protected int locTangent = -1;
	protected int locTexCoord0 = -1;
	protected int locTexCoord1 = -1;

	// uniform locations
	protected int locMvMatrix = -1;
	protected int locMvpMatrix = -1;
	protected int locNormalMatrix = -1;
	protected int locMaterialEmission = -1;
	protected int locMaterialAmbient = -1;
	protected int locMaterialDiffuse = -1;
	protected int locLightPosition = -1;
	protected int locLightAmbient = -1;
	protected int locLightDiffuse = -1;
	protected int locColorMap = -1;
	protected int locNormalMap = -1;

	// pipeline parameters
	protected Material material;
	protected Light light;
	
	public ColorAndNormalMapShaderPipeline(GL3 gl, PMVMatrix matrixStack, Material material, Light light) {
		super(gl, matrixStack);
		this.material = material;
		this.light = light;
	}	
	
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}

	public int getLocVertex() {
		return locVertex;
	}

	public int getLocNormal() {
		return locNormal;
	}

	public int getLocTangent() {
		return locTangent;
	}

	public int getLocTexCoord0() {
		return locTexCoord0;
	}

	public int getLocTexCoord1() {
		return locTexCoord1;
	}

	@Override
	protected void getLocations() {
		locVertex = gl.glGetAttribLocation(shaderProgram, "vVertex");
		locNormal = gl.glGetAttribLocation(shaderProgram, "vNormal");
		locTangent = gl.glGetAttribLocation(shaderProgram, "vTangent");
		locTexCoord0 = gl.glGetAttribLocation(shaderProgram, "vTexCoord0");
		locTexCoord1 = gl.glGetAttribLocation(shaderProgram, "vTexCoord1");
		
		locMvpMatrix = gl.glGetUniformLocation(shaderProgram, "mvpMatrix");
		locMvMatrix = gl.glGetUniformLocation(shaderProgram, "mvMatrix");
		locNormalMatrix = gl.glGetUniformLocation(shaderProgram, "normalMatrix");
		locMaterialEmission = gl.glGetUniformLocation(shaderProgram, "materialEmission");
		locMaterialAmbient = gl.glGetUniformLocation(shaderProgram, "materialAmbient");
		locMaterialDiffuse = gl.glGetUniformLocation(shaderProgram, "materialDiffuse");
		locLightPosition = gl.glGetUniformLocation(shaderProgram, "lightPosition");
		locLightAmbient = gl.glGetUniformLocation(shaderProgram, "lightAmbient");
		locLightDiffuse = gl.glGetUniformLocation(shaderProgram, "lightDiffuse");
		locColorMap = gl.glGetUniformLocation(shaderProgram, "colorMap");
		locNormalMap = gl.glGetUniformLocation(shaderProgram, "normalMap");
	}

	@Override
	protected void setUniforms() {
		gl.glUniformMatrix4fv(locMvpMatrix, 1, false, mvpMatrix);
		gl.glUniformMatrix4fv(locMvMatrix, 1, false, matrixStack.glGetMvMatrixf());
		gl.glUniformMatrix4fv(locNormalMatrix, 1, false, matrixStack.glGetMvitMatrixf());
		gl.glUniform4fv(locMaterialEmission, 1, material.getEmission(), 0);
		gl.glUniform4fv(locMaterialAmbient, 1, material.getAmbient(), 0);
		gl.glUniform4fv(locMaterialDiffuse, 1, material.getDiffuse(), 0);
		gl.glUniform4fv(locLightPosition, 1, light.getPosition(), 0);
		gl.glUniform4fv(locLightAmbient, 1, light.getAmbient(), 0);
		gl.glUniform4fv(locLightDiffuse, 1, light.getDiffuse(), 0);
		// set the uniform variable "sampler2D colorMap" to texture unit 0
		gl.glUniform1i(locColorMap, 0);
		// set the uniform variable "sampler2D normalMap" to texture unit 1
		gl.glUniform1i(locNormalMap, 1);
	}

	@Override
	protected String getFilenameVertexShader() {
		return "ColorAndNormalMapShaderVertex.glsl";
	}

	@Override
	protected String getFilenameFragmentShader() {
		return "ColorAndNormalMapShaderFragment.glsl";
	}

}
