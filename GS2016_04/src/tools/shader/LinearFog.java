package tools.shader;

public class LinearFog {
	private static final float[] DEFAULT_COLOR = { 0.0F, 0.0F, 0.0F, 0.0F };
	private static final float DEFAULT_START = 0.0F;
	private static final float DEFAULT_END = 1.0F;
	
	private float[] color;
	private float start;
	private float end;
	private float scale;
	
	public LinearFog() {
		super();
		color = DEFAULT_COLOR.clone();
		start = DEFAULT_START;
		end = DEFAULT_END;
		updateScale();
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		if (color.length != 4) {
			throw new IllegalArgumentException("Array must be of length 4");
		}
		this.color = color.clone();
	}

	public float getStart() {
		return start;
	}

	public void setStart(float start) {
		this.start = start;
		updateScale();
	}

	public float getEnd() {
		return end;
	}

	public void setEnd(float end) {
		this.end = end;
		updateScale();
	}

	public float getScale() {
		return scale;
	}
	
	private void updateScale() {
		scale = 1.0F / (end - start);
	}
}
