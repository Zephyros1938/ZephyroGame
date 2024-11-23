package game.Display;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4x3f;

public class TileGenerator {
    private float scale = 0.1f; // Default scale

    public final Matrix3f TOP_LEFT; // Dont initialize these, they are initialized when a new TileGenerator is made.
    public final Matrix3f BOTTOM_RIGHT; // Dont initialize these, they are initialized when a new TileGenerator is made.

    private Shader SHADER;

    private Matrix4x3f TC = new Matrix4x3f( // TexCoords stored in 4x3 matrix
            0f, 1f,
            1f, 1f,
            0f, 0f,
            
            1f, 1f,
            1f, 0f,
            0f, 0f);

    public TileGenerator(float scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("Scale for TileGenerator must be above 0.");
        }
        this.scale = scale;
        this.TOP_LEFT = new Matrix3f(
                -scale, -scale, 0f,
                scale, -scale, 0f,
                -scale, scale, 0f);
        this.BOTTOM_RIGHT = new Matrix3f(
                scale, -scale, 0f,
                scale, scale, 0f,
                -scale, scale, 0f);
    }

    /**
     * Generates a tile at the specified position with texture coordinates.
     * 
     * @param x The x-coordinate of the tile position
     * @param y The y-coordinate of the tile position
     * @param z The z-coordinate of the tile position
     * @param u Texture coordinate u
     * @param v Texture coordinate v
     */
    public void generateTile(float x, float y, float z, float u, float v) {
        if(this.SHADER == null) throw new NullPointerException("Shader has yet to be bound to the tile generator.");
        float scaledX = x * scale;
        float scaledY = y * scale;
        float scaledZ = z * scale;

        // Move the top-left and bottom-right matrices based on the tile's position
        SHADER.addVertCoord(translateMatrix(TOP_LEFT, scaledX, scaledY, scaledZ));
        SHADER.addVertCoord(translateMatrix(BOTTOM_RIGHT, scaledX, scaledY, scaledZ));

        // Add the texture coordinates
        SHADER.addTexCoord(TC);
    }

    public void bindShader(Shader s) {
        this.SHADER = s;
    }

    /**
     * Translates the given matrix by the specified x and y amounts.
     * 
     * @param m The matrix to be translated
     * @param x The amount to translate along the x-axis
     * @param y The amount to translate along the y-axis
     * @param z The amount to translate along the z-axis
     * @return A new translated matrix
     */
    private Matrix3f translateMatrix(Matrix3f m, float x, float y, float z) {
        // Directly translate the matrix instead of creating a new one
        return m.set(m.m00 + x, m.m01 + y, m.m02 + z,
                m.m10 + x, m.m11 + y, m.m12 + z,
                m.m20 + x, m.m21 + y, m.m22 + z);
    }
}
