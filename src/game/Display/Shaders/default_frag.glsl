#version 330 core
in vec4 vertexColor;
in float side;

out vec4 FragColor;

const float RGB[12] = float[](
    1.0, 0.0, 0.0, 0.0,
    0.0, 1.0, 0.0, 0.0,
    0.0, 0.0, 1.0, 0.0
);
const float YMC[12] = float[](
    1.0, 1.0, 0.0, 0.0,
    0.0, 1.0, 1.0, 0.0,
    1.0, 0.0, 1.0, 0.0
);

void main() {
    // Assuming side is an integer in the range [0, 3]
    int sideInt = int(side);

    // Use sideInt to index the color arrays
    vec4 color;
    if (sideInt < 3) {
        color = vec4(RGB[sideInt * 4 + 0], RGB[sideInt * 4 + 1], RGB[sideInt * 4 + 2], 1.0);
    } else {
        color = vec4(YMC[sideInt * 4 + 0], YMC[sideInt * 4 + 1], YMC[sideInt * 4 + 2], 1.0);
    }

    FragColor = color;
}