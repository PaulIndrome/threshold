#version 130

in vec4 vVertex;
in vec4 vNormal;

uniform mat4 mvpMatrix;
uniform mat4 mvMatrix;
uniform mat4 normalMatrix;

smooth out vec3 vEyePosition;
smooth out vec3 vEyeNormal;

void main(void) {
	gl_Position = mvpMatrix * vVertex;
	
	vec4 v4Pos = mvMatrix * vVertex;
	vEyePosition = v4Pos.xyz / v4Pos.w;
	vec4 v4Normal = normalMatrix * vNormal;
	vEyeNormal = v4Normal.xyz;
}