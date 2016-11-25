#version 130

uniform mat4 mvpMatrix;
in vec4 vVertex;
in vec4 vColor;

out vec4 vInterpolatedColor;

void main(void) {
	gl_Position = mvpMatrix * vVertex;
	vInterpolatedColor = vColor;
}