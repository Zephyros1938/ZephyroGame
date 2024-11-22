package game.Display;

import org.joml.Matrix2f;
import org.joml.Matrix3x2f;
import org.joml.Matrix4f;

public class TileGenerator {
    private float scale = 0.1f; // Default scale

    public final Matrix3x2f TOP_LEFT; // Dont initialize these, they are initialized when a new TileGenerator is made.
    public final Matrix3x2f BOTTOM_RIGHT; // Dont initialize these, they are initialized when a new TileGenerator is made.

    // Texture Coordinates (Matrix4f could simplify this if needed)
    private Matrix2f TC0A = new Matrix2f(0.f, 0.f, -1f, 0.f);
    private Matrix2f TC0B = new Matrix2f(-1f, -1f, 0.f, -1f);
    private Matrix2f TC1A = new Matrix2f(0.f, 0.f, 1.f, 0.f);
    private Matrix2f TC1B = new Matrix2f(1.f, -1f, 0.f, -1f);

    private Matrix4f TC = new Matrix4f( // TexCoords stored in 4x4 matrix
            0.f, 0.f, -1f, 0.f,
            -1f, -1f, 0.f, -1f,
            0.f, 0.f, 1.f, 0.f,
            1.f, -1f, 0.f, -1f);

    public TileGenerator(float scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("Scale for TileGenerator must be above 0.");
        }
        this.scale = scale;
        this.TOP_LEFT = new Matrix3x2f(
                -scale, -scale,
                -scale, scale,
                scale, scale);
        this.BOTTOM_RIGHT = new Matrix3x2f(
                -scale, -scale,
                scale, -scale,
                scale, scale);
    }

    /**
     * Generates a tile at the specified position with texture coordinates.
     * @param s The shader to add the vertex and texture coordinates to
     * @param x The x-coordinate of the tile position
     * @param y The y-coordinate of the tile position
     * @param u Texture coordinate u
     * @param v Texture coordinate v
     */
    public void generateTile(Shader s, int x, int y, int u, int v) {
        float scaledX = x * scale;
        float scaledY = y * scale;

        // Move the top-left and bottom-right matrices based on the tile's position
        s.addVertCoord(translateMatrix(TOP_LEFT, scaledX, scaledY));
        s.addVertCoord(translateMatrix(BOTTOM_RIGHT, scaledX, scaledY));

        // Add the texture coordinates
        s.addTexCoord(TC0A, TC0B);
        s.addTexCoord(TC1A, TC1B);
    }

    /**
     * Translates the given matrix by the specified x and y amounts.
     * @param m The matrix to be translated
     * @param x The amount to translate along the x-axis
     * @param y The amount to translate along the y-axis
     * @return A new translated matrix
     */
    private Matrix3x2f translateMatrix(Matrix3x2f m, float x, float y) {
        // Directly translate the matrix instead of creating a new one
        return m.set(m.m00 + x, m.m01 + y,
                     m.m10 + x, m.m11 + y,
                     m.m20 + x, m.m21 + y);
    }
}
