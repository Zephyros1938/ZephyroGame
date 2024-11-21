#version 330 core

in vec4 vColor; // Interpolated color from vertex shader
in vec2 tTexCoord;

out vec4 FragColor;

uniform sampler2D Default;
uniform sampler2D SliceTesting;

void main() {
    FragColor = texture(SliceTesting, tTexCoord); // Use the interpolated color
}
