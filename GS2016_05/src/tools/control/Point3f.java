package tools.control;

public class Point3f {
	public Point3f() {
		super();
		x = 0.0F;
		y = 0.0F;
		z = 0.0F;
	}

	public Point3f(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float x;
	public float y;
	public float z;
	
	@Override
	public String toString() {
		return "Point3f(" + x + ", " + y + ", " + z + ")";
	}
}
