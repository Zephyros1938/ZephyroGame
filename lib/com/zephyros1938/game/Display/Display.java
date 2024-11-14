package lib.com.zephyros1938.game.Display;

import java.nio.CharBuffer;

import lib.com.zephyros1938.lib.math.PerlinNoise.PerlinNoise;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Display {
    private static long window;

    static private Integer WIDTH = 5;
    static private Integer HEIGHT = 5;
    static private Integer SCREEN_WIDTH;
    static private Integer SCREEN_HEIGHT;
    static private Integer SCREEN_SIZE = WIDTH * HEIGHT;

    static private PerlinNoise terrainPerlinNoise = new PerlinNoise(123141);
    static private float terrainAmplitude = 1.0f;
    static private float terrainFrequency = 0.2f;

    static public CharBuffer Screen;

    public static int clamp(int min, int max, int value) {
        return (value < min ? min : value) > max ? max : (value < min ? min : value);
    }

    public Display(int H, int W, int SCREEN_X, int SCREEN_Y) {
        System.out.println("Display Created");
        HEIGHT = H;
        WIDTH = W;
        SCREEN_WIDTH = SCREEN_X;
        SCREEN_HEIGHT = SCREEN_Y;

    }

    private static char getTerrainBlock(float height) {
        if (height >= 140.0) {
            return Objects.LAND.value;
        } else {
            return Objects.AIR.value;
        }
    }

    public void Initialize() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to intialize GLFW");
        }

        window = GLFW.glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Window", 0, 0);

        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window.");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1);

        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents();

            GLFW.glfwSwapBuffers(window);
        }
    }

    /* END INITIALIZATION */

    enum Objects {
        LAND('#'),
        AIR(' '),
        PLAYER('Y'),
        ORB('O'),
        ENEMY('Ё'),
        BIGENEMY('Ж');

        private final char value;

        Objects(char value) {
            this.value = value;
        }

        public char getObject() {
            return value;
        }
    }
}