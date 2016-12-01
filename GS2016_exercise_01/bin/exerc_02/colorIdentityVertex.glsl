#version 130

uniform mat4 mvpMatrix;
uniform float radius;

in float angle;

void main(void) {
float x = radius * cos(angle);
float y = radius * sin(angle);
	gl_Position = mvpMatrix * vec4(x,y,0,1);
}