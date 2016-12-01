#version 130

in vec4 vVertex;
in vec4 vNormal;
in vec4 vTangent;
in vec2 vTexCoord0;
in vec2 vTexCoord1;

uniform mat4 mvpMatrix;
uniform mat4 mvMatrix;
uniform mat4 normalMatrix;

uniform vec4 lightPosition;

smooth out vec3 v3LightDir_tangentspace;
smooth out vec2 v2VaryingColorMapCoord;
smooth out vec2 v2VaryingNormalMapCoord;

void main(void) {
	
	// get vertex position in eye coordinates
	vec3 vEyePosition = (mvMatrix * vVertex).xyz;
	
	// get direction to light source
	vec3 vLightDir;
	if (lightPosition.w == 0.0) {
		vLightDir = normalize(lightPosition.xyz);
	} else {
		vLightDir = normalize((lightPosition.xyz / lightPosition.w) - vEyePosition);
	}
	
	// get surface normal in eye coordinates
	vec3 v3EyeNormal = normalize((normalMatrix * vNormal).xyz);
	
	// get surface tangent in eye coordinates
	vec3 v3EyeTangent = normalize((normalMatrix * vTangent).xyz);
	
	// calculate bitangent in eye coordinates
	vec3 v3EyeBitangent = normalize(cross(v3EyeNormal, v3EyeTangent));
	
	// construct TBN matrix
	mat3 tbnMatrix = transpose(mat3(
		v3EyeTangent,
		v3EyeBitangent,
		v3EyeNormal
	));
	
	// get light direction in tangent space
	v3LightDir_tangentspace = tbnMatrix * vLightDir;
		
	// output
	v2VaryingColorMapCoord = vTexCoord0;
	v2VaryingNormalMapCoord = vTexCoord1;	
	gl_Position = mvpMatrix * vVertex;
}