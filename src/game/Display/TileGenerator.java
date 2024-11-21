package game.Display;

import org.joml.Matrix2f;
import org.joml.Matrix3x2f;

public class TileGenerator {
    private float scale = 0.1f;
    private Matrix3x2f TOP_LEFT = new Matrix3x2f(
            -1 * scale, -1 * scale,
            -1 * scale, 1 * scale,
            1 * scale, 1 * scale);
    private Matrix3x2f BOTTOM_RIGHT = new Matrix3x2f(
            -1 * scale, -1 * scale,
            1 * scale, -1 * scale,
            1 * scale, 1 * scale);

    private Matrix2f TC0A = new Matrix2f(0f, 0f, -1f, 0f);
    private Matrix2f TC0B = new Matrix2f(-1f, -1f, 0f, -1f);
    private Matrix2f TC1A = new Matrix2f(0f, 0f, 1f, 0f);
    private Matrix2f TC1B = new Matrix2f(1f, -1f, 0f, -1f);

    public TileGenerator(float scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("Scale for TileGenerator must be above 0.");
        }
        this.scale = scale;
        this.TOP_LEFT = new Matrix3x2f(
                -1 * scale, -1 * scale,
                -1 * scale, 1 * scale,
                1 * scale, 1 * scale);
        this.BOTTOM_RIGHT = new Matrix3x2f(
                -1 * scale, -1 * scale,
                1 * scale, -1 * scale,
                1 * scale, 1 * scale);
    }

    public void GenerateTile(Shader s, int x, int y, int u, int v) {
        s.addVertCoord(move(TOP_LEFT, x * scale, y * scale));
        s.addVertCoord(move(BOTTOM_RIGHT, x * scale, y * scale));
        s.addTexCoord(TC0A,TC0B);
        s.addTexCoord(TC1A,TC1B);
    }

    private Matrix3x2f move(Matrix3x2f m, float x, float y) {
        return new Matrix3x2f(
                m.m00 + x, m.m01 + y,
                m.m10 + x, m.m11 + y,
                m.m20 + x, m.m21 + y);
    }
}
