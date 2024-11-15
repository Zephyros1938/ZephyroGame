#version 330 core

in vec3 vColor; // Interpolated color from vertex shader
out vec4 FragColor;

void main() {
    FragColor = vec4(vColor, 1.0); // Use the interpolated color
}
