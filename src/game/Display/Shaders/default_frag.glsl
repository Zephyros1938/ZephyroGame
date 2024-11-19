#version 330 core

in vec4 vColor; // Interpolated color from vertex shader
out vec4 FragColor;

void main() {
    FragColor = vColor; // Use the interpolated color
}
