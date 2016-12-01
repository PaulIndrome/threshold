#version 130

smooth in vec3 vEyePosition;
smooth in vec3 vEyeNormal;

uniform vec4 materialEmission;
uniform vec4 materialAmbient;
uniform vec4 materialDiffuse;
uniform vec4 materialSpecular;
uniform float materialShininess;

uniform vec4 lightPosition;
uniform vec4 lightAmbient;
uniform vec4 lightDiffuse;
uniform vec4 lightSpecular;

out vec4 vFragColor;

const vec3 vEyeDir = vec3(0.0, 0.0, 1.0);

void main(void) {
	vec3 N = normalize(vEyeNormal);
	vec4 emissive = materialEmission;
	vec4 ambient = materialAmbient * lightAmbient;
		
	// get direction to light source
	vec3 vLightDir;
	if (lightPosition.w == 0.0) {
		vLightDir = normalize(lightPosition.xyz);
	} else {
		vLightDir = normalize((lightPosition.xyz / lightPosition.w) - vEyePosition);
	}
	
	// calculate diffuse intensity by dot product
	float diff = max(0.0, dot(N, vLightDir));
	
	vec4 diffuse = vec4(0.0, 0.0, 0.0, 1.0);
	vec4 specular = vec4(0.0, 0.0, 0.0, 1.0);
	
	if (diff > 0.0) {
		diffuse = (materialDiffuse * lightDiffuse) * diff;
		
		// specular intensity
		if (materialShininess > 0.0) {
			vec3 vReflection = normalize(reflect(-vLightDir, N));
			float spec = pow(max(0.0, dot(vEyeDir, vReflection)), materialShininess);
			specular = (materialSpecular * lightSpecular) * spec;
		}
	}
	
	vFragColor = emissive + ambient + diffuse + specular;
}