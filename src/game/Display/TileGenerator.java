package game.Display;

import org.joml.Matrix3x2f;

public class TileGenerator {
    private final static float scale = 0.1f;
    private final static Matrix3x2f TOP_LEFT = new Matrix3x2f(
            -1f, -1f,
            -1f, 1f,
            1f, 1f);
    private final static Matrix3x2f BOTTOM_RIGHT = new Matrix3x2f(
            -1f, -1f,
            1f, -1f,
            1f, 1f);

    public TileGenerator(Mesh m, Shader s, int x, int y, int u, int v) {
        m.addTriangle(TOP_LEFT.translate(x * scale, y * scale));
        m.addTriangle(BOTTOM_RIGHT.translate(x * scale, y * scale));
    }
}
