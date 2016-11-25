#version 130

flat in vec4 vInterpolatedColor;
out vec4 vFragColor;

void main(void) {
	vFragColor = vInterpolatedColor;
}