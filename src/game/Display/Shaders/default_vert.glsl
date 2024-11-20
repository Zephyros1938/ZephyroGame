#version 330 core

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec2 aTexCoord;

out vec4 vColor;
out vec2 tTexCoord;

void main() {
    vec3 position = aPos;

    // Assign a color based on vertex position
    vColor = vec4(1.0,1.0,1.0,0.1);
    tTexCoord = aTexCoord;

    gl_Position = vec4(position, 1.0);
}
