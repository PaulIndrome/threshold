package tools.wireframe;

public enum VertexFormat {
	N3F_V3F, T2F_N3F_V3F, T2F_N3F_G3F_V3F;
	
	public int getComponentCount() {
		switch (this) {
		case N3F_V3F:
			return 6;
		case T2F_N3F_V3F:
			return 8;
		case T2F_N3F_G3F_V3F:
			return 11;
		default:
			return 0;
		}
	}

	public int getTexCoordOffset() {
		switch (this) {
		case T2F_N3F_V3F:
			return 0;
		case T2F_N3F_G3F_V3F:
			return 0;
		default:
			return -1;
		}
	}
	
	public int getNormalOffset() {
		switch (this) {
		case N3F_V3F:
			return 0;
		case T2F_N3F_V3F:
			return 2;
		case T2F_N3F_G3F_V3F:
			return 2;
		default:
			return -1;
		}
	}
	
	public int getTangentOffset() {
		switch (this) {
		case T2F_N3F_G3F_V3F:
			return 5;
		default:
			return -1;
		}
	}
	
	public int getVertexOffset() {
		switch (this) {
		case N3F_V3F:
			return 3;
		case T2F_N3F_V3F:
			return 5;
		case T2F_N3F_G3F_V3F:
			return 8;
		default:
			return -1;
		}
	}
}
