#version 130

in vec4 vVertex;
in vec4 vColor;

uniform mat4 mvpMatrix;

flat out vec4 vVaryingColor;

void main(void) {
	vVaryingColor = vColor;
	
	gl_Position = mvpMatrix * vVertex;
}