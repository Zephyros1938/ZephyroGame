#version 330 core

layout(location = 0) in vec2 aPos;
layout(location = 1) in float aSide;
uniform float xMod;
uniform float yMod;

out vec3 vColor;

void main() {
    vec2 position = aPos;

    position.xy *= vec2(xMod, yMod);

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

    // Triangle coordinate calculation 
    if (aSide == 5.0 || aSide == 2.0) {
        position.x += 0.5;
        position.y += 0.5;
    } else if (aSide == 1.0) {
        position.y += 0.5;
    } else if (aSide == 4.0) {
        position.x += 0.5;
    }

    position.xy += vec2(0.5,0.5);

    gl_Position = vec4(position, 0.0, 1.0);
}
