package game.Display;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL20;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL15;

import lib.math.Shapes.Shapes;

import lib.math.Shapes.LWJGL.ShapesLWJGL;
import lib.math.Shapes.LWJGL.ShapesLWJGL.TriangleData;

public class Display {
    private static long window;
    static private Integer SCREEN_WIDTH;
    static private Integer SCREEN_HEIGHT;

    static private final Float Xdim = 4f, Ydim = 4f;

    static public Integer[] Screen = new Integer[(int) (Xdim * Ydim)];

    final static Float[] TriDefaultColor = new Float[]{
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f};

    static private TriangleData[] Tris = new TriangleData[(int) (Xdim * Ydim)];

    static final Float incrX = (1f / Xdim);
    static final Float incrY = (1f / Ydim);

    static void DrawTriangle(float x, float y, int ID) {

        x *= 2f / Xdim;
        y *= 2f / Ydim;

        x -= 1f - incrX;
        y -= 1f - incrY;

        Float[] vert = new Float[]{
                x - incrX, y - incrY,
                x + incrX, y - incrY,
                x, y + incrY};

        Tris[ID] = new TriangleData(vert, TriDefaultColor);
    }

    public Display(int H, int W, int SCREEN_X, int SCREEN_Y) {
        for (Integer bit = 0; bit < Screen.length; bit++) {
            float XU = bit % (Xdim);
            float YU = (float) Math.floor((bit) / Xdim);
            DrawTriangle(XU, YU, bit);
        }
        System.out.println("Display Created");
        SCREEN_WIDTH = SCREEN_X;
        SCREEN_HEIGHT = SCREEN_Y;
    }

    public void Initialize() throws IOException {
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        window = GLFW.glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Window", 0, 0);
        if (window == 0)
            throw new RuntimeException("Failed to create the GLFW window.");
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1);

        // init shaders
        int shaderProgram = createShaderProgram();
        GL20.glUseProgram(shaderProgram);

        // gen VAO & VBO
        int vao = GL30.glGenVertexArrays();
        int vbo = GL15.glGenBuffers();
        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        float[] vertices = new float[Tris.length * 18]; // each triangle has 3 vertices, each vertex has 6 floats (2 for
                                                        // position, 4 for color)
        int index = 0;
        for (TriangleData Obj : Tris) {
            vertices[index++] = Obj.vert[0];
            vertices[index++] = Obj.vert[1];
            vertices[index++] = Obj.col[0];
            vertices[index++] = Obj.col[1];
            vertices[index++] = Obj.col[2];
            vertices[index++] = Obj.col[3];

            vertices[index++] = Obj.vert[2];
            vertices[index++] = Obj.vert[3];
            vertices[index++] = Obj.col[4];
            vertices[index++] = Obj.col[5];
            vertices[index++] = Obj.col[6];
            vertices[index++] = Obj.col[7];

            vertices[index++] = Obj.vert[4];
            vertices[index++] = Obj.vert[5];
            vertices[index++] = Obj.col[8];
            vertices[index++] = Obj.col[9];
            vertices[index++] = Obj.col[10];
            vertices[index++] = Obj.col[11];
        }

        // data -> VBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
        vertices = new float[Tris.length * 18];

        // positions
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 6 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        // colors
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 6 * Float.BYTES, 2 * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        // render loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // draw triangles
            GL30.glBindVertexArray(vao);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Tris.length * 3);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        // cleanup on kill
        GL20.glDeleteProgram(shaderProgram);
        GL15.glDeleteBuffers(vbo);
        GL30.glDeleteVertexArrays(vao);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public int loadShader(String filepath, int type) throws IOException {
        StringBuilder shaderSource = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error creating shader: " + GL20.glGetShaderInfoLog(shaderID));
        }
        return shaderID;
    }

    public int createShaderProgram() throws IOException {
        int vertexShader = loadShader("src/game/Display/Shaders/default_vert.glsl", GL20.GL_VERTEX_SHADER);
        int fragmentShader = loadShader("src/game/Display/Shaders/default_frag.glsl", GL20.GL_FRAGMENT_SHADER);
        int shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);
        if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error linking program: " + GL20.glGetProgramInfoLog(shaderProgram));
        }
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        return shaderProgram;
    }
}
