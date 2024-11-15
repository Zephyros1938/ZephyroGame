#version 330 core

out vec4 FragColor;
in vec4 vertexColor;

void main() {
    vec4 color = vertexColor;

    FragColor = color;
}