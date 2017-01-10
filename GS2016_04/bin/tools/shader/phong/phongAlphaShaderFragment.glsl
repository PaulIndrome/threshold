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
	vec3 emissive = materialEmission.rgb;
	vec3 ambient = materialAmbient.rgb * lightAmbient.rgb;
	float alpha = materialAmbient.a;
		
	// get direction to light source
	vec3 vLightDir;
	if (lightPosition.w == 0.0) {
		vLightDir = normalize(lightPosition.xyz);
	} else {
		vLightDir = normalize((lightPosition.xyz / lightPosition.w) - vEyePosition);
	}
	
	// calculate diffuse intensity by dot product
	float diff = max(0.0, dot(N, vLightDir));
	
	vec3 diffuse = vec3(0.0, 0.0, 0.0);
	vec3 specular = vec3(0.0, 0.0, 0.0);
	
	if (diff > 0.0) {
		diffuse = (materialDiffuse.rgb * lightDiffuse.rgb) * diff;
		
		// specular intensity
		if (materialShininess > 0.0) {
			vec3 vReflection = normalize(reflect(-vLightDir, N));
			float spec = pow(max(0.0, dot(vEyeDir, vReflection)), materialShininess);
			vec4 v4Specular = (materialSpecular * lightSpecular) * spec;
			specular = v4Specular.rgb;
			alpha = (alpha + materialDiffuse.a + v4Specular.a) / 3.0;
		} else {
			alpha = (alpha + materialDiffuse.a) / 2.0;
		}
	}
	
	vFragColor = vec4(emissive + ambient + diffuse + specular, alpha);
}