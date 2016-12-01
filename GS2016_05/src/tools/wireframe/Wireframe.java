package tools.wireframe;

import java.io.Serializable;

public class Wireframe implements Serializable {

	private static final long serialVersionUID = 1L;

	private VertexFormat vertexFormat;
	private float[] vertices;
	private int primitiveMode;
	private int[] faces;

	public Wireframe(VertexFormat vertexFormat, float[] vertices, int primitiveMode, int[] faces) {
		super();
		this.vertexFormat = vertexFormat;
		this.vertices = vertices;
		this.primitiveMode = primitiveMode;
		this.faces = faces;
	}

	public VertexFormat getVertexFormat() {
		return vertexFormat;
	}

	public float[] getVertices() {
		return vertices;
	}

	public int getPrimitiveMode() {
		return primitiveMode;
	}

	public int[] getFaces() {
		return faces;
	}
}
