package tools.shader;

public class Light {
	
	private static final float[] DEFAULT_POSITION = { 0.0F, 0.0F, 1.0F, 0.0F };
	private static final float[] DEFAULT_AMBIENT = { 0.0F, 0.0F, 0.0F, 1.0F };
	private static final float[] DEFAULT_DIFFUSE = { 1.0F, 1.0F, 1.0F, 1.0F };
	private static final float[] DEFAULT_SPECULAR = { 0.0F, 0.0F, 0.0F, 1.0F };
	
	private float[] position;
	private float[] ambient;
	private float[] diffuse;
	private float[] specular;
	
	public Light() {
		super();
		position = DEFAULT_POSITION.clone();
		ambient = DEFAULT_AMBIENT.clone();
		diffuse = DEFAULT_DIFFUSE.clone();
		specular = DEFAULT_SPECULAR.clone();
	}

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		if (position.length != 4) {
			throw new IllegalArgumentException("Array must be of length 4");
		}
		this.position = position.clone();
	}

	public float[] getAmbient() {
		return ambient;
	}

	public void setAmbient(float[] ambient) {
		if (ambient.length != 4) {
			throw new IllegalArgumentException("Array must be of length 4");
		}
		this.ambient = ambient.clone();
	}

	public float[] getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(float[] diffuse) {
		if (diffuse.length != 4) {
			throw new IllegalArgumentException("Array must be of length 4");
		}
		this.diffuse = diffuse.clone();
	}

	public float[] getSpecular() {
		return specular;
	}

	public void setSpecular(float[] specular) {
		if (specular.length != 4) {
			throw new IllegalArgumentException("Array must be of length 4");
		}
		this.specular = specular.clone();
	}

	
}
