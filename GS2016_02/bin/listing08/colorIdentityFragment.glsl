#version 400

uniform vec4 vColorValue;

layout (location = 0) out vec4 vFragColor;

void main(void) {
	vFragColor = vColorValue;
}