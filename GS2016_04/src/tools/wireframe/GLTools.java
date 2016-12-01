package tools.wireframe;

import static com.jogamp.opengl.math.FloatUtil.PI;
import static com.jogamp.opengl.math.FloatUtil.TWO_PI;
import static com.jogamp.opengl.math.FloatUtil.cos;
import static com.jogamp.opengl.math.FloatUtil.sin;

import com.jogamp.opengl.math.FloatUtil;

public class GLTools {
	
	public static final float THREE_HUNDRED_SIXTY_DEGREES = TWO_PI;
	public static final float ONE_DEGREE = PI / 180.0F;
	
	public static String matrixToString(float[] matrix) {
		if (matrix.length != 16) {
			return "not a 4x4 matrix";
		} else {
			return 	"/" + String.format("%f", matrix[0]) + "\t" + String.format("%f", matrix[4]) + "\t" + String.format("%f", matrix[8]) + "\t" + String.format("%f", matrix[12]) + "\\" + System.lineSeparator() +
					"|" + String.format("%f", matrix[1]) + "\t" + String.format("%f", matrix[5]) + "\t" + String.format("%f", matrix[9]) + "\t" + String.format("%f", matrix[13]) + "|" + System.lineSeparator() +
					"|" + String.format("%f", matrix[2]) + "\t" + String.format("%f", matrix[6]) + "\t" + String.format("%f", matrix[10]) + "\t" + String.format("%f", matrix[14]) + "|" + System.lineSeparator() +
					"\\" + String.format("%f", matrix[3]) + "\t" + String.format("%f", matrix[7]) + "\t" + String.format("%f", matrix[11]) + "\t" + String.format("%f", matrix[15]) + "/" + System.lineSeparator();
		}
	}
	
	/**
	 * Adopted from "gltools.cpp"
	 * 
	 * (c) by Richard Wright on 10/16/06. OpenGL SuperBible, 5th Edition
	 * 
	 */
	public static float[] calculateSphereMesh(float radius, int slices, int stacks) {
		// Format: GL_T2F_N3F_V3F
		// (slices * stacks * 6) vertices
		// 8 components / vertex
		float drho = PI / stacks;
		float dtheta = 2.0F * PI / slices;
		float ds = 1.0F / slices;
		float dt = 1.0F / stacks;
		float t = 1.0F;
		float s = 0.0F;
		int l = 0;

		float[] sphereMesh = new float[slices * stacks * 6 * 8];
		for (int i = 0; i < stacks; i++) {
			float rho = i * drho;
			float srho = sin(rho);
			float crho = cos(rho);
			float srhodrho = sin(rho + drho);
			float crhodrho = cos(rho + drho);
			s = 0.0F;

			float[][] vVertex = new float[4][3];
			float[][] vNormal = new float[4][3];
			float[][] vTexture = new float[4][2];

			for (int j = 0; j < slices; j++) {
				float theta = (j == slices) ? 0.0F : j * dtheta;
				float stheta = -sin(theta);
				float ctheta = cos(theta);
				float x = stheta * srho;
				float y = ctheta * srho;
				float z = crho;

				vTexture[0][0] = s;
				vTexture[0][1] = t;
				vNormal[0][0] = x;
				vNormal[0][1] = y;
				vNormal[0][2] = z;
				vVertex[0][0] = x * radius;
				vVertex[0][1] = y * radius;
				vVertex[0][2] = z * radius;

				x = stheta * srhodrho;
				y = ctheta * srhodrho;
				z = crhodrho;

				vTexture[1][0] = s;
				vTexture[1][1] = t - dt;
				vNormal[1][0] = x;
				vNormal[1][1] = y;
				vNormal[1][2] = z;
				vVertex[1][0] = x * radius;
				vVertex[1][1] = y * radius;
				vVertex[1][2] = z * radius;

				theta = ((j + 1) == slices) ? 0.0F : (j + 1) * dtheta;
				stheta = -sin(theta);
				ctheta = cos(theta);
				x = stheta * srho;
				y = ctheta * srho;
				z = crho;
				s += ds;

				vTexture[2][0] = s;
				vTexture[2][1] = t;
				vNormal[2][0] = x;
				vNormal[2][1] = y;
				vNormal[2][2] = z;
				vVertex[2][0] = x * radius;
				vVertex[2][1] = y * radius;
				vVertex[2][2] = z * radius;

				x = stheta * srhodrho;
				y = ctheta * srhodrho;
				z = crhodrho;

				vTexture[3][0] = s;
				vTexture[3][1] = t - dt;
				vNormal[3][0] = x;
				vNormal[3][1] = y;
				vNormal[3][2] = z;
				vVertex[3][0] = x * radius;
				vVertex[3][1] = y * radius;
				vVertex[3][2] = z * radius;

				// Format: GL_T2F_N3F_V3F

				// first triangle
				for (int k = 0; k < 3; k++) {
					sphereMesh[l++] = vTexture[k][0];
					sphereMesh[l++] = vTexture[k][1];
					sphereMesh[l++] = vNormal[k][0];
					sphereMesh[l++] = vNormal[k][1];
					sphereMesh[l++] = vNormal[k][2];
					sphereMesh[l++] = vVertex[k][0];
					sphereMesh[l++] = vVertex[k][1];
					sphereMesh[l++] = vVertex[k][2];
				}
				// second triangle
				for (int k = 1; k < 4; k++) {
					sphereMesh[l++] = vTexture[k][0];
					sphereMesh[l++] = vTexture[k][1];
					sphereMesh[l++] = vNormal[k][0];
					sphereMesh[l++] = vNormal[k][1];
					sphereMesh[l++] = vNormal[k][2];
					sphereMesh[l++] = vVertex[k][0];
					sphereMesh[l++] = vVertex[k][1];
					sphereMesh[l++] = vVertex[k][2];
				}
			}
			t -= dt;
		}

		return sphereMesh;
	}
	
	public static float[] calculateSphereMeshWTangents(float radius, int slices, int stacks) {
		// Format: GL_T2F_N3F_G3F_V3F
		// (slices * stacks * 6) vertices
		// 11 components / vertex
		float drho = PI / stacks;
		float dtheta = 2.0F * PI / slices;
		float ds = 1.0F / slices;
		float dt = 1.0F / stacks;
		float t = 1.0F;
		float s = 0.0F;
		int l = 0;
	
		float[] sphereMesh = new float[slices * stacks * 6 * 11];
		for (int i = 0; i < stacks; i++) {
			float rho = i * drho;
			float srho = sin(rho);
			float crho = cos(rho);
			float srhodrho = sin(rho + drho);
			float crhodrho = cos(rho + drho);
			s = 0.0F;
	
			float[][] vVertex = new float[4][3];
			float[][] vNormal = new float[4][3];
			float[][] vTangent = new float[4][3];
			float[][] vTexture = new float[4][2];
	
			for (int j = 0; j < slices; j++) {
				
				float theta = (j == slices) ? 0.0F : j * dtheta;
				float stheta = -sin(theta);
				float ctheta = cos(theta);
				float x = stheta * srho;
				float y = ctheta * srho;
				float z = crho;
	
				vTexture[0][0] = s;
				vTexture[0][1] = t;
				vNormal[0][0] = x;
				vNormal[0][1] = y;
				vNormal[0][2] = z;
				vVertex[0][0] = x * radius;
				vVertex[0][1] = y * radius;
				vVertex[0][2] = z * radius;
	
				x = stheta * srhodrho;
				y = ctheta * srhodrho;
				z = crhodrho;
	
				vTexture[1][0] = s;
				vTexture[1][1] = t - dt;
				vNormal[1][0] = x;
				vNormal[1][1] = y;
				vNormal[1][2] = z;
				vVertex[1][0] = x * radius;
				vVertex[1][1] = y * radius;
				vVertex[1][2] = z * radius;
	
				theta = ((j + 1) == slices) ? 0.0F : (j + 1) * dtheta;
				stheta = -sin(theta);
				ctheta = cos(theta);
				x = stheta * srho;
				y = ctheta * srho;
				z = crho;
				s += ds;
	
				vTexture[2][0] = s;
				vTexture[2][1] = t;
				vNormal[2][0] = x;
				vNormal[2][1] = y;
				vNormal[2][2] = z;
				vVertex[2][0] = x * radius;
				vVertex[2][1] = y * radius;
				vVertex[2][2] = z * radius;
	
				x = stheta * srhodrho;
				y = ctheta * srhodrho;
				z = crhodrho;
	
				vTexture[3][0] = s;
				vTexture[3][1] = t - dt;
				vNormal[3][0] = x;
				vNormal[3][1] = y;
				vNormal[3][2] = z;
				vVertex[3][0] = x * radius;
				vVertex[3][1] = y * radius;
				vVertex[3][2] = z * radius;
				
				// calculate tangents
				//  0  2
				//  | /|
				//  |/ |
				//  1  3
				
				vTangent[0] = normalize(crossProduct(getDirection(vVertex[1], vVertex[0]), vNormal[0]));
				vTangent[1] = normalize(crossProduct(getDirection(vVertex[1], vVertex[0]), vNormal[1]));
				vTangent[2] = normalize(crossProduct(getDirection(vVertex[3], vVertex[2]), vNormal[2]));
				vTangent[3] = normalize(crossProduct(getDirection(vVertex[3], vVertex[2]), vNormal[3]));
	
				// Format: GL_T2F_N3F_G3F_V3F
	
				// first triangle
				for (int k = 0; k < 3; k++) {
					sphereMesh[l++] = vTexture[k][0];
					sphereMesh[l++] = vTexture[k][1];
					sphereMesh[l++] = vNormal[k][0];
					sphereMesh[l++] = vNormal[k][1];
					sphereMesh[l++] = vNormal[k][2];
					sphereMesh[l++] = vTangent[k][0];
					sphereMesh[l++] = vTangent[k][1];
					sphereMesh[l++] = vTangent[k][2];
					sphereMesh[l++] = vVertex[k][0];
					sphereMesh[l++] = vVertex[k][1];
					sphereMesh[l++] = vVertex[k][2];
				}
				// second triangle
				for (int k = 1; k < 4; k++) {
					sphereMesh[l++] = vTexture[k][0];
					sphereMesh[l++] = vTexture[k][1];
					sphereMesh[l++] = vNormal[k][0];
					sphereMesh[l++] = vNormal[k][1];
					sphereMesh[l++] = vNormal[k][2];
					sphereMesh[l++] = vTangent[k][0];
					sphereMesh[l++] = vTangent[k][1];
					sphereMesh[l++] = vTangent[k][2];
					sphereMesh[l++] = vVertex[k][0];
					sphereMesh[l++] = vVertex[k][1];
					sphereMesh[l++] = vVertex[k][2];
				}
			}
			t -= dt;
		}
	
		return sphereMesh;
	}
	
	public static float[] getDirection(float[] v1, float[] v2) {
		if (v1.length == v2.length) {
			float[] d = new float[v1.length];
			for (int i = 0; i < d.length; i++) {
				d[i] = v2[i] - v1[i];
			}
			return d;
		}
		return null;
	}
	
	public static float[] crossProduct(float[] v1, float[] v2) {
		if (v1.length == 3 && v2.length == 3) {
			float[] c = new float[3];
			c[0] = v1[1] * v2[2] - v1[2] * v2[1];
			c[1] = v1[2] * v2[0] - v1[0] * v2[2];
			c[2] = v1[0] * v2[1] - v1[1] * v2[0];
			return c;
		}
		return null;
	}
	
	public static float[] normalize(float[] v) {
		if (v.length > 0) {
			float l = 0.0F;
			for (int i = 0; i < v.length; i++) {
				l += v[i] * v[i];
			}
			l = FloatUtil.sqrt(l);
			if (l > 0.0F) {
				for (int i = 0; i < v.length; i++) {
					v[i] /= l; 
				}
			}
			return v;
		}
		return null;
	}
	
	public static float[] calculateCubeMesh(float radius) {
		// Format: GL_T2F_N3F_V3F
		// 8 components * 6 vertices/side * 6 sides
		float[] cubeMesh = new float[288];
		int i = 0;
		// calculating sidelength from radius of circumscribed sphere
		float sidelength = radius * 0.57735026F;
		
		// ///////////////////////////////////////////
		// Top of cube
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		// //////////////////////////////////////////
		// Bottom of cube
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		// /////////////////////////////////////////
		// Left side of cube
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = -1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		// Right side of cube
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		// Front and Back
		// Front
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength; // vertex data

		// Back
		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F; // normals
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 1.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		cubeMesh[i++] = 1.0F;
		cubeMesh[i++] = 0.0F; // texture coords
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = 0.0F;
		cubeMesh[i++] = -1.0F; // normals
		cubeMesh[i++] = sidelength;
		cubeMesh[i++] = -sidelength;
		cubeMesh[i++] = -sidelength; // vertex data

		return cubeMesh;
	}
	
	@Deprecated
	public static float[] calculateCircleAsPolygon(float x, float y, float radius) {
		float[] circle = new float[360 * 2];
		float angle;
		for (int i = 0; i < 360; i++) {
			angle = i * ONE_DEGREE;
			circle[2 * i] = x + radius * cos(angle);
			circle[2 * i + 1] = y + radius * sin(angle);
		}
		return circle;
	}
	
	public static float[] calculateCircleAsTriangleFan(float x, float y, float radius) {
		float[] circle = new float[362 * 2];
		float angle;
		circle[0] = x;
		circle[1] = y;
		for (int i = 1; i < 362; i++) {
			angle = i * ONE_DEGREE;
			circle[2 * i] = x + radius * cos(angle);
			circle[2 * i + 1] = y + radius * sin(angle);
		}
		return circle;
	}
}
