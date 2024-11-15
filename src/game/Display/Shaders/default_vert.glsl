#version 330 core

layout(location = 0) in vec2 aPos;
layout(location = 1) in float aSide;

out vec3 vColor;

void main() {
    gl_Position = vec4(aPos, 0.0, 1.0);

    // Assign a color based on vertex position
    if (aSide == 0.0) {
        vColor = vec3(1.0, 0.0, 0.0); // Red
    } else if (aSide == 1.0) {
        vColor = vec3(0.0, 1.0, 0.0); // Green
    } else if (aSide == 2.0) {
        vColor = vec3(0.0, 0.0, 1.0); // Blue
    } else if (aSide == 3.0) {
        vColor = vec3(1.0, 1.0, 0.0); // Green
    } else if (aSide == 4.0) {
        vColor = vec3(0.0, 1.0, 1.0); // Blue
    } else if (aSide == 5.0) {
        vColor = vec3(1.0, 0.0, 1.0); // Blue
    }
}
