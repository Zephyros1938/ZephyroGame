package game.Display;

import java.nio.CharBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import lib.math.Shapes.Shapes;

import lib.math.Shapes.LWJGL.ShapesLWJGL;
import lib.math.Shapes.LWJGL.ShapesLWJGL.TriangleData;
import lib.math.Shapes.Shapes.Pentatope;

public class Display {
    private static long window;
    static private Integer SCREEN_WIDTH;
    static private Integer SCREEN_HEIGHT;

    static private final Float Xdim = 16f, Ydim = 16f;

    static public Integer[] Screen = new Integer[(int) (Xdim * Ydim)];

    final static Pentatope TriDefaultColor = new Shapes().new Pentatope(
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f);

    static Shapes shapesInstance = new Shapes();
    static ShapesLWJGL shapesLWJGLInstance = new ShapesLWJGL();

    static private TriangleData[] Tris = new TriangleData[(int) (Xdim * Ydim)];

    static void DrawTriangle(float x, float y, int ID) {

        x *= 2f/Xdim;
        y *= 2f/Ydim;

        x-=.75f;
        y-=.75f;
        
        //x -= 1.f;
        //y -= 1.f;
        System.out.println(ID + " " + x + " " + y);

        //x-=1f;
        //y-=1f;

        final Shapes.Triangle triVec = shapesInstance.new Triangle(x-.25f,y-.25f,x+.25f,y-.25f,x,y+.25f);

        Tris[ID] = new TriangleData(triVec, TriDefaultColor);
    }

    public Display(int H, int W, int SCREEN_X, int SCREEN_Y) {
        for (Integer bit = 0; bit < Screen.length; bit++) {
            float XU = bit % (Xdim);
            float YU = (float) Math.floor((bit) / Xdim);
            System.out.println("YU: "+ YU);
            DrawTriangle(XU,YU,bit);
        }
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

                GL11.glColor4f(Obj.col[0], Obj.col[1], Obj.col[2], Obj.col[3]);
                GL11.glVertex2f(Obj.vert.BL.x, Obj.vert.BL.y);

                GL11.glColor4f(Obj.col[4], Obj.col[5], Obj.col[6], Obj.col[7]);
                GL11.glVertex2f(Obj.vert.TP.x, Obj.vert.TP.y);

                GL11.glColor4f(Obj.col[8], Obj.col[9], Obj.col[10], Obj.col[11]);
                GL11.glVertex2f(Obj.vert.BR.x, Obj.vert.BR.y);

                GL11.glEnd();
            }

            GLFW.glfwSwapBuffers(window);
        }

        GLFW.glfwDestroyWindow(window);
    }
}