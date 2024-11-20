package game.Display;

import java.io.IOException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL30;

import org.joml.*;

public class Display {

    private static Window window;
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

    @SuppressWarnings("static-access")
    public Display(int SCREEN_X, int SCREEN_Y) {
        this.SCREEN_WIDTH = SCREEN_X;
        this.SCREEN_HEIGHT = SCREEN_Y;
    }

    public void init() throws IOException {

        window = new Window(SCREEN_WIDTH, SCREEN_HEIGHT, "Window", 0, 0);
        window.init();

        Shader defaultShader = new Shader(Shader.SHADER_COORD_LEN_3);

        Mesh testMesh = new Mesh(new Matrix3f(
                -.5f, 0.5f, 0f,
                -.5f, -.5f, 0f,
                0.5f, -.5f, 0f));

        testMesh.addTriangle(new Matrix3f(
                -.5f, 0.5f, 0f,
                0.5f, 0.5f, 0f,
                0.5f, -.5f, 0f));

        defaultShader.init(testMesh.getMesh());
        defaultShader.addVertexAttrib(Shader.SHADER_COORD_LEN_3); // Triangle vertex positions

        defaultShader.use();
        ShaderObject defaultShaderObject = new ShaderObject(defaultShader, testMesh);

        window.addShaderObject(defaultShaderObject);
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
            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT
                    | GL30.GL_ACCUM_BUFFER_BIT);

            window.drawShaderObjects();

            // UPDATE
            GLFW.glfwSwapBuffers(window.WINDOW);
            GLFW.glfwPollEvents();
        }
    }

    public void render(Window window) {

    }

    public void dispose() { // Cleanup the shaders & buffers on kill
        window.terminate();
    }

    public void run() throws IOException {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
            dispose();
        } finally {
            gameLoop();
        }
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
