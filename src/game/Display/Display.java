package src.game.Display;

import java.nio.CharBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import src.lib.math.PerlinNoise.PerlinNoise;
import src.lib.math.Shapes.Shapes;
import src.lib.math.Shapes.Shapes.*;
import src.lib.math.Vector.Vector;
import src.lib.math.Vector.Vector.Vector2;

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

    static Shapes shapesInstance = new Shapes();

    static private Triangle[] Tris = {
            shapesInstance.new Triangle(new Vector2(-0.5f, -0.5f), new Vector2(0.5f, -0.5f), new Vector2(0.0f, 0.5f))
    };

    public Display(int H, int W, int SCREEN_X, int SCREEN_Y) {
        System.out.println("Display Created");
        HEIGHT = H;
        WIDTH = W;
        SCREEN_WIDTH = SCREEN_X;
        SCREEN_HEIGHT = SCREEN_Y;
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

        GL.createCapabilities();

        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1);

        while (!GLFW.glfwWindowShouldClose(window)) {
            GLFW.glfwPollEvents();

            for (Triangle triangle : Tris) {
                GL11.glBegin(GL11.GL_TRIANGLES);

                GL11.glColor3f(1.0f, 0.0f, 0.0f);
                GL11.glVertex2f(triangle.P1.x, triangle.P1.y);

                GL11.glColor3f(0.0f, 1.0f, 0.0f);
                GL11.glVertex2f(triangle.P2.x, triangle.P2.y);

                GL11.glColor3f(0.0f, 0.0f, 1.0f);
                GL11.glVertex2f(triangle.P3.x, triangle.P3.y);

                GL11.glEnd();
            }

            GLFW.glfwSwapBuffers(window);
        }

        GLFW.glfwDestroyWindow(window);
    }
}