package tools.wireframe;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class WireframeGenerator {
	public enum WireframeResource {
		TEAPOT, DRAGON
	}

	private WireframeGenerator() {
		super();
	}

	private static Wireframe readWireframeFromResource(String resource) {
		InputStream stream = WireframeGenerator.class.getResourceAsStream(resource);
		try {
			ObjectInputStream os = new ObjectInputStream(stream);
			Object o = os.readObject();
			os.close();
			if (o instanceof Wireframe) {
				return (Wireframe) o;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Wireframe generateFromResource(WireframeResource type) {
		Wireframe wire = null;
		switch (type) {
		case TEAPOT:
			wire = readWireframeFromResource("teapot.wireframe");
			break;
		case DRAGON:
			wire = readWireframeFromResource("dragon.wireframe");
			break;
		}
		return wire;
	}
	
	public static Wireframe generateCube(float radius) {
		float[] vertices = GLTools.calculateCubeMesh(radius);
		return new Wireframe(VertexFormat.T2F_N3F_V3F, vertices, GL_TRIANGLES, null);
	}
	
	public static Wireframe generateSphere(float radius, int slices, int stacks) {
		float[] vertices = GLTools.calculateSphereMesh(radius, slices, stacks);
		return new Wireframe(VertexFormat.T2F_N3F_V3F, vertices, GL_TRIANGLES, null);
	}
	
	public static Wireframe generateSphereWithTangents(float radius, int slices, int stacks) {
		float[] vertices = GLTools.calculateSphereMeshWTangents(radius, slices, stacks);
		return new Wireframe(VertexFormat.T2F_N3F_G3F_V3F, vertices, GL_TRIANGLES, null);
	}
	
	public static Wireframe generateCube2(float radius) {
	//public static float[] calculateCubeMeshFor2x2Texture(float radius) {
		// calculating sidelength from radius of circumscribed sphere
		float sidelength = radius * 0.57735026F;
		
		// Format: GL_T2F_N3F_V3F
		// 8 components * 6 vertices/side * 6 sides
		float[] vertices = {
				// tex coords	normals 			vertex data
				// front side
				0.5F, 0.5F,		0.0F, 0.0F, 1.0F,	sidelength, sidelength, sidelength,   //
				0.0F, 0.5F,		0.0F, 0.0F, 1.0F,	-sidelength, sidelength, sidelength,   //
				0.0F, 0.0F,		0.0F, 0.0F, 1.0F,	-sidelength, -sidelength, sidelength,   //
				0.5F, 0.0F,		0.0F, 0.0F, 1.0F,	sidelength, -sidelength, sidelength,   //
				// back side
				0.5F, 0.5F,		0.0F, 0.0F, -1.0F,	-sidelength, sidelength, -sidelength,   //
				0.0F, 0.5F,		0.0F, 0.0F, -1.0F,	sidelength, sidelength, -sidelength,   //
				0.0F, 0.0F,		0.0F, 0.0F, -1.0F,	sidelength, -sidelength, -sidelength,   //
				0.5F, 0.0F,		0.0F, 0.0F, -1.0F,	-sidelength, -sidelength, -sidelength,   //
				// left side
				1.0F, 0.5F,		1.0F, 0.0F, 0.0F,	sidelength, sidelength, -sidelength,   //
				0.5F, 0.5F,		1.0F, 0.0F, 0.0F,	sidelength, sidelength, sidelength,   //
				0.5F, 0.0F,		1.0F, 0.0F, 0.0F,	sidelength, -sidelength, sidelength,   //
				1.0F, 0.0F,		1.0F, 0.0F, 0.0F,	sidelength, -sidelength, -sidelength,   //
				// right side
				1.0F, 0.5F,		-1.0F, 0.0F, 0.0F,	-sidelength, sidelength, sidelength,   //
				0.5F, 0.5F,		-1.0F, 0.0F, 0.0F,	-sidelength, sidelength, -sidelength,   //
				0.5F, 0.0F,		-1.0F, 0.0F, 0.0F,	-sidelength, -sidelength, -sidelength,   //
				1.0F, 0.0F,		-1.0F, 0.0F, 0.0F,	-sidelength, -sidelength, sidelength,   //
				// upper side
				1.0F, 1.0F,		0.0F, 1.0F, 0.0F,	-sidelength, sidelength, -sidelength,   //
				0.5F, 1.0F,		0.0F, 1.0F, 0.0F,	-sidelength, sidelength, sidelength,   //
				0.5F, 0.5F,		0.0F, 1.0F, 0.0F,	sidelength, sidelength, sidelength,   //
				1.0F, 0.5F,		0.0F, 1.0F, 0.0F,	sidelength, sidelength, -sidelength,   //
				// lower side
				0.5F, 1.0F,		0.0F, -1.0F, 0.0F,	sidelength, -sidelength, sidelength,   //
				0.0F, 1.0F,		0.0F, -1.0F, 0.0F,	sidelength, -sidelength, -sidelength,   //
				0.0F, 0.5F,		0.0F, -1.0F, 0.0F,	-sidelength, -sidelength, -sidelength,   //
				0.5F, 0.5F,		0.0F, -1.0F, 0.0F,	-sidelength, -sidelength, sidelength   //
		};
		
		int[] faces = { 0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4, 8, 9, 10,
				10, 11, 8, 12, 13, 14, 14, 15, 12, 16, 17, 18, 18, 19, 16,
				20, 21, 22, 22, 23, 20 };

		return new Wireframe(VertexFormat.T2F_N3F_V3F, vertices, GL_TRIANGLES, faces);
	}
	
}
