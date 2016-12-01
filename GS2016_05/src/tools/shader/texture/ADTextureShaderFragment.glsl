#version 130

uniform sampler2D tex0;

smooth in vec4 vVaryingColor;
smooth in vec2 vVaryingTexCoord;

out vec4 vFragColor;

void main(void) {
	vFragColor = vVaryingColor * texture2D(tex0, vVaryingTexCoord);
}