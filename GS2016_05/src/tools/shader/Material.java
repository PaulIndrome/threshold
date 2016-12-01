package tools.shader;

public class Material {
	public static final float[] DEFAULT_AMBIENT = { 0.2F, 0.2F, 0.2F, 1.0F };
	public static final float[] DEFAULT_DIFFUSE = { 0.8F, 0.8F, 0.8F, 1.0F };
	public static final float[] DEFAULT_SPECULAR = { 0.0F, 0.0F, 0.0F, 0.0F };
	public static final float[] DEFAULT_EMISSION = { 0.0F, 0.0F, 0.0F, 0.0F };
	public static final float DEFAULT_SHININESS = 0.0F;
	
	private float[] ambient;
	private float[] diffuse;
	private float[] specular;
	private float[] emission;
	private float shininess;
	
	public Material() {
		super();
		ambient = DEFAULT_AMBIENT.clone();
		diffuse = DEFAULT_DIFFUSE.clone();
		specular = DEFAULT_SPECULAR.clone();
		emission = DEFAULT_EMISSION.clone();
		shininess = DEFAULT_SHININESS;
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
	
	public void setAmbientAndDiffuse(float[] ambientAndDiffuse) {
		if (ambientAndDiffuse.length != 4) {
			throw new IllegalArgumentException("Array must be of length 4");
		}
		this.diffuse = ambientAndDiffuse.clone();
		this.ambient = ambientAndDiffuse.clone();
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

	public float[] getEmission() {
		return emission;
	}

	public void setEmission(float[] emission) {
		if (emission.length != 4) {
			throw new IllegalArgumentException("Array must be of length 4");
		}
		this.emission = emission.clone();
	}

	public float getShininess() {
		return shininess;
	}

	public void setShininess(float shininess) {
		if (shininess < 0.0F) {
			throw new IllegalArgumentException("Argument must be positive");
		}
		this.shininess = shininess;
	}
	
	
}
