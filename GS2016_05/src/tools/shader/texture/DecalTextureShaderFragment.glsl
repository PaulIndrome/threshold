#version 130

uniform sampler2D tex0;

smooth in vec2 vVaryingTexCoord;

out vec4 vFragColor;

void main(void) {
	vFragColor = texture2D(tex0, vVaryingTexCoord);
}