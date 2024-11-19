package game.Display;

import java.io.IOException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import org.joml.*;

public class Display {

    private static Window window;
    static private int SCREEN_WIDTH;
    static private int SCREEN_HEIGHT;

    static private final int Xdim = 4, Ydim = 4;

    static public int Screen = Xdim * Ydim;

    static final float incrX = (1f / Xdim), incrY = (1f / Ydim);

    static final int SHADER_VERT_COUNT = 3;
    static final int SHADER_COORD_LEN = 3;
    static final int SHADER_ATTRIBUTE_LEN = SHADER_COORD_LEN;
    static final int SHADER_TOTAL_LEN = SHADER_ATTRIBUTE_LEN * SHADER_VERT_COUNT;

    static private float[] vertices = new float[27];

    static int index = 0;

    static void DrawTriangle(float x, float y, float z) {
        if (index + SHADER_TOTAL_LEN > vertices.length) {
            System.err.println("Vertex buffer overflow at " + index + "!");
            return;
        }
        vertices[index++] = x; // X
        vertices[index++] = y; // Y
        vertices[index++] = z;
    }

    @SuppressWarnings("static-access")
    public Display(int SCREEN_X, int SCREEN_Y) {
        this.SCREEN_WIDTH = SCREEN_X;
        this.SCREEN_HEIGHT = SCREEN_Y;
    }

    Shader defaultShader;

    public void init() throws IOException {

        window = new Window(SCREEN_WIDTH, SCREEN_HEIGHT, "Window", 0, 0);
        window.Init();

        defaultShader = new Shader(SHADER_ATTRIBUTE_LEN);

        Mesh testMesh = new Mesh(new Matrix3f(
                -.5f, 0.5f, 0f,
                -.5f, -.5f, 0f,
                0.5f, -.5f, 0f));

        testMesh.addTriangle(new Matrix3f(
                -.5f, 0.5f, 0f,
                0.5f, 0.5f, 0f,
                0.5f, -.5f, 0f));

        defaultShader.Init(testMesh.getMesh());
        defaultShader.AddVertexAttrib(SHADER_COORD_LEN); // Triangle vertex positions

        window.addShader(defaultShader);

        Texture t = TextureUtils.loadTexture("Default.png");
    }

    public void gameLoop() {
        Timer timer = new Timer();

        float delta;
        float accumulator = 0f;
        float interval = 1f / 60;

        timer.Init(); // May be useful for gameloop:
                      // https://github.com/SilverTiger/lwjgl3-tutorial/wiki/Timing
        while (!GLFW.glfwWindowShouldClose(window.WINDOW)) {
            delta = timer.getDelta();
            accumulator += delta;

            while (accumulator >= interval) {
                accumulator -= interval;
            }

            // RENDER
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT
                    | GL11.GL_ACCUM_BUFFER_BIT);

            GL30.glBindVertexArray(defaultShader.VAO);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices.length);
            GL30.glBindVertexArray(0);

            // UPDATE
            GLFW.glfwSwapBuffers(window.WINDOW);
            GLFW.glfwPollEvents();
        }
    }

    public void render(Window window) {

    }

    public void dispose() { // Cleanup the shaders & buffers on kill
        window.Terminate();
    }

    public void run() throws IOException {
        init();
        gameLoop();
        dispose();
    }
}

class Keyboard extends GLFWKeyCallback {
    private static boolean[] keys = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key < 0) { // <- ❗️ IMPORTANT: This line is added to prevent
                       // `ArrayIndexOutOfBoundsException` in some cases.
            return;
        }
        keys[key] = action != GLFW.GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }
}
