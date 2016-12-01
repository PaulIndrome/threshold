package tools.shader;

public class Materials {
	private static final int R = 0;
	private static final int G = 1;
	private static final int B = 2;
	private static final int A = 3;
	
	//												0			1		2		3		4		5		6		7		8		9		10		11		12
	//												ambient								diffuse							specular						shininess
	//												r			g		b		a		r		g		b		a		r		g		b		a		s 
	private static final float[] VALUES_GOLD = 		{ 0.25F,	0.2F,	0.07F,	1.0F,	0.75F,	0.61F,	0.23F,	1.0F,	0.63F,	0.56F,	0.37F,	1.0F,	51.2F };
	private static final float[] VALUES_PGOLD =		{ 0.25F,	0.22F,	0.06F,	1.0F,	0.35F,	0.31F,	0.09F,	1.0F,	0.80F,	0.72F,	0.21F,	1.0F,	83.2F };
	private static final float[] VALUES_CHROME =	{ 0.25F,	0.25F,	0.25F,	1.0F,	0.4F,	0.4F,	0.4F,	1.0F,	0.77F,	0.77F,	0.77F,	1.0F,	76.8F };
	private static final float[] VALUES_BRONZE =	{ 0.21F,	0.13F,	0.05F,	1.0F,	0.71F,	0.43F,	0.18F,	1.0F,	0.39F,	0.27F,	0.17F,	1.0F,	25.6F };
	private static final float[] VALUES_PBRONZE =	{ 0.25F,	0.15F,	0.06F,	1.0F,	0.40F,	0.24F,	0.10F,	1.0F,	0.77F,	0.46F,	0.20F,	1.0F,	76.8F };
	private static final float[] VALUES_BRASS =		{ 0.33F,	0.22F,	0.03F,	1.0F,	0.78F,	0.57F,	0.11F,	1.0F,	0.99F,	0.94F,	0.81F,	1.0F,	27.9F };
	private static final float[] VALUES_COPPER =	{ 0.19F,	0.07F,	0.02F,	1.0F,	0.70F,	0.27F,	0.08F,	1.0F,	0.26F,	0.14F,	0.09F,	1.0F,	12.8F };
	private static final float[] VALUES_PCOPPER =	{ 0.23F,	0.09F,	0.03F,	1.0F,	0.55F,	0.21F,	0.07F,	1.0F,	0.58F,	0.22F,	0.07F,	1.0F,	51.2F };
	// transparent materials
	private static final float[] VALUES_EMERALD =	{ 0.02F,	0.17F,	0.02F,	0.5F,	0.08F,	0.61F,	0.08F,	0.5F,	0.63F,	0.73F,	0.63F,	0.5F,	76.8F };
	private static final float[] VALUES_JADE =		{ 0.14F,	0.22F,	0.16F,	0.9F,	0.54F,	0.89F,	0.63F,	0.9F,	0.32F,	0.32F,	0.32F,	0.9F,	12.8F };
	private static final float[] VALUES_RUBY =		{ 0.17F,	0.01F,	0.01F,	0.5F,	0.61F,	0.04F,	0.04F,	0.5F,	0.73F,	0.63F,	0.63F,	0.5F,	76.8F };
	
	public enum MaterialType {
		BRASS, BRONZE, POLISHED_BRONZE, CHROME, COPPER, POLISHED_COPPER, EMERALD, GOLD, POLISHED_GOLD, JADE, RUBY
	}
	
	private static Material createMaterialFromArray(float[] values) {
		Material m = new Material();
		
		m.getAmbient()[R] = values[0];
		m.getAmbient()[G] = values[1];
		m.getAmbient()[B] = values[2];
		m.getAmbient()[A] = values[3];
		
		m.getDiffuse()[R] = values[4];
		m.getDiffuse()[G] = values[5];
		m.getDiffuse()[B] = values[6];
		m.getDiffuse()[A] = values[7];
		
		m.getSpecular()[R] = values[8];
		m.getSpecular()[G] = values[9];
		m.getSpecular()[B] = values[10];
		m.getSpecular()[A] = values[11];
		
		m.setShininess(values[12]);
		
		return m;
	}
	
	public static Material getPredefinedMaterial(MaterialType type) {
		switch(type) {
		case CHROME:
			return createMaterialFromArray(VALUES_CHROME);
		case GOLD:
			return createMaterialFromArray(VALUES_GOLD);
		case POLISHED_GOLD:
			return createMaterialFromArray(VALUES_PGOLD);
		case EMERALD:
			return createMaterialFromArray(VALUES_EMERALD);
		case RUBY:
			return createMaterialFromArray(VALUES_RUBY);
		case JADE:
			return createMaterialFromArray(VALUES_JADE);
		case BRONZE:
			return createMaterialFromArray(VALUES_BRONZE);
		case POLISHED_BRONZE:
			return createMaterialFromArray(VALUES_PBRONZE);
		case BRASS:
			return createMaterialFromArray(VALUES_BRASS);
		case COPPER:
			return createMaterialFromArray(VALUES_COPPER);
		case POLISHED_COPPER:
			return createMaterialFromArray(VALUES_PCOPPER);
		default:
			return null;
		}
		
	}
}
