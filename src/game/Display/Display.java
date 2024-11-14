package src.game.Display;

import java.nio.CharBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import src.lib.math.Shapes.Shapes;

import src.lib.math.Shapes.LWJGL.ShapesLWJGL;
import src.lib.math.Shapes.LWJGL.ShapesLWJGL.TriangleData;

public class Display {
    private static long window;
    static private Integer SCREEN_WIDTH;
    static private Integer SCREEN_HEIGHT;

    static public CharBuffer Screen;

    static Shapes shapesInstance = new Shapes();
    static ShapesLWJGL shapesLWJGLInstance = new ShapesLWJGL();

    static private TriangleData[] Tris = {
            new TriangleData(
                    shapesInstance.new Triangle(-0.5f, -0.5f, 0.5f, -0.5f, 0.0f, 0.5f),
                    shapesInstance.new Pyramid(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f)) };

    public Display(int H, int W, int SCREEN_X, int SCREEN_Y) {
        System.out.println("Display Created");
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

            for (TriangleData Obj : Tris) {
                GL11.glBegin(GL11.GL_TRIANGLES);

                GL11.glColor3f(Obj.col.BL.x, Obj.col.BL.y, Obj.col.BL.z);
                GL11.glVertex2f(Obj.vert.BL.x, Obj.vert.BL.y);

                GL11.glColor3f(Obj.col.TP.x, Obj.col.TP.y, Obj.col.TP.z);
                GL11.glVertex2f(Obj.vert.TP.x, Obj.vert.TP.y);

                GL11.glColor3f(Obj.col.BR.x, Obj.col.BR.y, Obj.col.BR.z);
                GL11.glVertex2f(Obj.vert.BR.x, Obj.vert.BR.y);

                GL11.glEnd();
            }

            GLFW.glfwSwapBuffers(window);
        }

        GLFW.glfwDestroyWindow(window);
    }
}