#version 130

uniform vec4 materialEmission;
uniform vec4 materialAmbient;
uniform vec4 materialDiffuse;

uniform vec4 lightAmbient;
uniform vec4 lightDiffuse;

uniform sampler2D normalMap;

smooth in vec3 v3LightDir_tangentspace;
smooth in vec2 v2VaryingNormalMapCoord;

out vec4 vFragColor;

vec3 expand(vec3 v) {
	return (v - 0.5) * 2.0;
}

void main(void) {
	// get normal in tangent space from normal map  
	vec3 v3Normal_tangentspace = normalize(expand(texture2D(normalMap, v2VaryingNormalMapCoord).rgb));
	
	// calculate diffuse intensity by dot product
	float diff = max(0.0, dot(v3Normal_tangentspace, v3LightDir_tangentspace));
	
	// calculate color components
	vec4 ambient = materialAmbient * lightAmbient;
	vec4 diffuse = vec4(0.0, 0.0, 0.0, 1.0);
	if (diff > 0.0) {
		diffuse = (materialDiffuse * lightDiffuse) * diff;
	}
	
	// final fragment color
	vFragColor = materialEmission + ambient + diffuse;
	//vFragColor = vec4(v3LightDir_tangentspace, 1.0);
}