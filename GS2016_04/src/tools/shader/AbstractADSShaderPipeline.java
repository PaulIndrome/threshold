package tools.shader;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.PMVMatrix;

public abstract class AbstractADSShaderPipeline extends AbstractShaderPipeline {
	public AbstractADSShaderPipeline(GL3 gl, PMVMatrix matrixStack, Material material, Light light) {
		super(gl, matrixStack);
		this.material = material;
		this.light = light;
	}

	// pipeline parameters
	protected Material material;
	protected Light light;
		
		// locations
	protected int locVertex = -1;
	protected int locNormal = -1;
	protected int locMvpMatrix = -1;
	protected int locMvMatrix = -1;
	protected int locNormalMatrix = -1;
	protected int locMaterialEmission = -1;
	protected int locMaterialAmbient = -1;
	protected int locMaterialDiffuse = -1;
	protected int locMaterialSpecular = -1;
	protected int locMaterialShininess = -1;
	protected int locLightPosition = -1;
	protected int locLightAmbient = -1;
	protected int locLightDiffuse = -1;
	protected int locLightSpecular = -1;
	
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
	

	@Override
	protected void getLocations() {
		locVertex = gl.glGetAttribLocation(shaderProgram, "vVertex");
		locNormal = gl.glGetAttribLocation(shaderProgram, "vNormal");
		locMvpMatrix = gl.glGetUniformLocation(shaderProgram, "mvpMatrix");
		locMvMatrix = gl.glGetUniformLocation(shaderProgram, "mvMatrix");
		locNormalMatrix = gl.glGetUniformLocation(shaderProgram, "normalMatrix");
		locMaterialEmission = gl.glGetUniformLocation(shaderProgram, "materialEmission");
		locMaterialAmbient = gl.glGetUniformLocation(shaderProgram, "materialAmbient");
		locMaterialDiffuse = gl.glGetUniformLocation(shaderProgram, "materialDiffuse");
		locMaterialSpecular = gl.glGetUniformLocation(shaderProgram, "materialSpecular");
		locMaterialShininess = gl.glGetUniformLocation(shaderProgram, "materialShininess");
		locLightPosition = gl.glGetUniformLocation(shaderProgram, "lightPosition");
		locLightAmbient = gl.glGetUniformLocation(shaderProgram, "lightAmbient");
		locLightDiffuse = gl.glGetUniformLocation(shaderProgram, "lightDiffuse");
		locLightSpecular = gl.glGetUniformLocation(shaderProgram, "lightSpecular");
	}

	@Override
	protected void setUniforms() {
		gl.glUniformMatrix4fv(locMvpMatrix, 1, false, mvpMatrix);
		gl.glUniformMatrix4fv(locMvMatrix, 1, false, matrixStack.glGetMvMatrixf());
		gl.glUniformMatrix4fv(locNormalMatrix, 1, false, matrixStack.glGetMvitMatrixf());
		gl.glUniform4fv(locMaterialEmission, 1, material.getEmission(), 0);
		gl.glUniform4fv(locMaterialAmbient, 1, material.getAmbient(), 0);
		gl.glUniform4fv(locMaterialDiffuse, 1, material.getDiffuse(), 0);
		gl.glUniform4fv(locMaterialSpecular, 1, material.getSpecular(), 0);
		gl.glUniform1f(locMaterialShininess, material.getShininess());
		gl.glUniform4fv(locLightPosition, 1, light.getPosition(), 0);
		gl.glUniform4fv(locLightAmbient, 1, light.getAmbient(), 0);
		gl.glUniform4fv(locLightDiffuse, 1, light.getDiffuse(), 0);
		gl.glUniform4fv(locLightSpecular, 1, light.getSpecular(), 0);
	}

}
