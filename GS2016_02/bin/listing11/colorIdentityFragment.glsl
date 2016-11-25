#version 130

in vec4 vInterpolatedColor;
out vec4 vFragColor;

void main(void) {
	vFragColor = vInterpolatedColor;
}