package tools.wireframe;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ObjLoader {
	
	static class Vertex3f {
		float x;
		float y;
		float z;
		
		protected Vertex3f(String[] fields, int x, int y, int z) {
			this.x = Float.parseFloat(fields[x]);
			this.y = Float.parseFloat(fields[y]);
			this.z = Float.parseFloat(fields[z]);
		}
		
		public Vertex3f(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		@Override
		public String toString() {
			return "(" + x + "; " + y + "; " + z + ")";
		}
	}
	
	private InputStream objStream;
	private File file;
	
	public ObjLoader(InputStream objStream) {
		super();
		setObjStream(objStream);
	}

	public ObjLoader(File file) throws FileNotFoundException {
		super();
		setFile(file);
	}

	public InputStream getObjStream() {
		return objStream;
	}

	public void setObjStream(InputStream objStream) {
		this.objStream = objStream;
		this.file = null;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) throws FileNotFoundException {
		this.file = file;
		this.objStream = new FileInputStream(file);
	}

	public Wireframe readWireframe() throws IOException {
		Wireframe wire = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(objStream));
		List<Vertex3f> vertexList = new LinkedList<Vertex3f>();
		List<Vertex3f> normalList = new LinkedList<Vertex3f>();
		List<String> faceEntryList = new LinkedList<String>();
		String line;
		while ((line = reader.readLine()) != null) {
			String[] fields = line.trim().split("\\s+");
			if (fields.length >= 4) {
				if (fields[0].equals("f")) {
					faceEntryList.add(fields[1]);
					faceEntryList.add(fields[2]);
					faceEntryList.add(fields[3]);
				} else if (fields[0].equals("v")) {
					vertexList.add(new Vertex3f(fields, 1, 2, 3));
				} else if (fields[0].equals("vn")) {
					normalList.add(new Vertex3f(fields, 1, 2, 3));
				}
			}
		}
		reader.close();
		
		List<Integer> faceList = new LinkedList<Integer>();
				
		if (faceEntryList.size() > 0 && faceEntryList.get(0).matches("\\d+\\/\\/\\d+")) {
			Map<Integer, Integer> v2n = new HashMap<Integer, Integer>();
			// reorder normals
			for (int i = 0; i < faceEntryList.size(); i++) {
				String[] fields = faceEntryList.get(i).split("\\/\\/");
				int v = Integer.parseInt(fields[0]);
				int n = Integer.parseInt(fields[1]);
				faceList.add(v);
				if (v2n.containsKey(v) && v2n.get(v).intValue() != n) {
					throw new Error("Normal index collision. Vertex index: " + v + " Existing normal index: " + v2n.get(v).intValue() + " new normal index: " + n);
				} else {
					v2n.put(v, n);
				}
			}
			List<Vertex3f> newNormalList = new LinkedList<Vertex3f>();
			Integer[] keys = new Integer[0]; 
			keys = v2n.keySet().toArray(keys);
			for (int i = 0; i < keys.length; i++) {
				newNormalList.add(null);
			}
			for (Integer key : keys) {
				newNormalList.set(key - 1, normalList.get(v2n.get(key)-1 ));
			}
			normalList = newNormalList;
		} else if (faceEntryList.size() > 0 && faceEntryList.get(0).matches("\\d+")) {
			for (int i = 0; i < faceEntryList.size(); i++) {
				faceList.add(Integer.parseInt(faceEntryList.get(i)));
			}
		} else if (faceEntryList.size() > 0) {
			throw new Error("Unknow face format: " + faceEntryList.get(0));
		} else {
			throw new Error("No face list found");
		}
		
		float[] vertices = new float[6 * vertexList.size()];
		for (int i = 0; i < vertexList.size(); i++) {
			vertices[i * 6    ] = normalList.get(i).x;
			vertices[i * 6 + 1] = normalList.get(i).y;
			vertices[i * 6 + 2] = normalList.get(i).z;
			vertices[i * 6 + 3] = vertexList.get(i).x;
			vertices[i * 6 + 4] = vertexList.get(i).y;
			vertices[i * 6 + 5] = vertexList.get(i).z;
		}
		
		int[] faces = new int[faceList.size()];
		for (int i = 0; i < faceList.size(); i++) {
			faces[i] = faceList.get(i) - 1;
		}
		
		wire = new Wireframe(VertexFormat.N3F_V3F, vertices, GL_TRIANGLES, faces);
		
		return wire;
	}

}
