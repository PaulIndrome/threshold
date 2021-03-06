#version 130

in vec4 vVertex;
in vec4 vNormal;

uniform mat4 mvpMatrix;
uniform mat4 mvMatrix;
uniform mat4 normalMatrix;

uniform vec4 materialEmission;
uniform vec4 materialAmbient;
uniform vec4 materialDiffuse;

uniform vec4 lightPosition;
uniform vec4 lightAmbient;
uniform vec4 lightDiffuse;

flat out vec4 vVaryingColor;

void main(void) {
	vec4 emissive = materialEmission;
	vec4 ambient = materialAmbient * lightAmbient;
	
	// get surface normal in eye coordinates
	vec3 vEyeNormal = normalize((normalMatrix * vNormal).xyz);
	
	// get vertex position in eye coordinates
	vec4 v4Position = mvMatrix * vVertex;
	vec3 v3Position = v4Position.xyz / v4Position.w;
	
	// get direction to light source
	vec3 vLightDir;
	if (lightPosition.w == 0.0) {
		vLightDir = normalize(lightPosition.xyz);
	} else {
		vLightDir = normalize((lightPosition.xyz / lightPosition.w) - v3Position);
	}
	
	// calculate diffuse intensity by dot product
	float diff = max(0.0, dot(vEyeNormal, vLightDir));
	
	vec4 diffuse = vec4(0.0, 0.0, 0.0, 1.0);
	
	if (diff > 0.0) {
		diffuse = (materialDiffuse * lightDiffuse) * diff;
	}
	
	vVaryingColor = emissive + ambient + diffuse;
	
	gl_Position = mvpMatrix * vVertex;
}