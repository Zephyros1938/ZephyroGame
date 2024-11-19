#version 330 core

layout(location = 0) in vec3 aPos;

out vec4 vColor;

void main() {
    vec3 position = aPos;

    //position.xy *= vec2(xMod, yMod);

    // Assign a color based on vertex position
    vColor = vec4(1.0,1.0,1.0,0.1);

    gl_Position = vec4(position, 1.0);
}
