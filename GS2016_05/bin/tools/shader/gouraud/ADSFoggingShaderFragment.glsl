#version 130
uniform vec4 fogColor;
uniform float fogEnd;
uniform float fogScale;

in vec4 vVaryingColor;
in float fogFragCoord;

out vec4 vFragColor;

void main(void) {
	float fogDensity;
	fogDensity = clamp((fogEnd - fogFragCoord) * fogScale, 0.0, 1.0);
	vFragColor = mix(fogColor, vVaryingColor, fogDensity);
}