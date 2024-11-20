#version 330 core

in vec4 vColor; // Interpolated color from vertex shader
in vec2 tTexCoord;

out vec4 FragColor;

uniform sampler2D Default;

void main() {
    FragColor = texture(Default, tTexCoord); // Use the interpolated color
}
