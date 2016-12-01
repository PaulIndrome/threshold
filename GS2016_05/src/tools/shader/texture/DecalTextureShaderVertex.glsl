#version 130

in vec4 vVertex;
in vec2 vTexCoord0;

uniform mat4 mvpMatrix;

smooth out vec2 vVaryingTexCoord;

void main(void) {
	vVaryingTexCoord = vTexCoord0;
	gl_Position = mvpMatrix * vVertex;
}