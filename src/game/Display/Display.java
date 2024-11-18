package game.Display;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Display {
    private static boolean DEBUG = false;

    private static long window;
    static private int SCREEN_WIDTH;
    static private int SCREEN_HEIGHT;

    static private final float Xdim = 4f, Ydim = 4f;

    static public int Screen = (int) (Xdim * Ydim);

    static final float incrX = (1f / Xdim), incrY = (1f / Ydim);

    static final int SHADER_VERT_COUNT = 3;
    static final int SHADER_COORD_LEN = 2;
    static final int SHADER_SIDE_LEN = 1;
    static final int SHADER_ATTRIBUTE_LEN = SHADER_COORD_LEN + SHADER_SIDE_LEN;
    static final int SHADER_TOTAL_LEN = SHADER_ATTRIBUTE_LEN * SHADER_VERT_COUNT;

    static double FRAMES_PER_SECOND = 1.0d / 60.0d;

    static private float[] vertices = new float[(int) ((Xdim * Ydim) * SHADER_TOTAL_LEN) * 2];
    static final private FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length * 2);

    static int index = 0;

    static void DrawTriangle(float x, float y, int ID) {

        // Transform to NDC coordinates
        x = x * 2f / Xdim - 1f;
        y = y * 2f / Ydim - 1f;

        float[] vert = new float[6];

        vert = new float[] {
                x, y, // Bottom left
                x, y + incrY * 2, // Top left
                x + incrX * 2, y + incrY * 2 // Top right
        };

        float[] side = new float[] { 0f, 1f, 2f };
        if (ID % 2 == 1) {
            vert = new float[] {
                    x, y, // Bottom left
                    x + incrX * 2, y, // Top left
                    x + incrX * 2, y + incrY * 2 // Top right
            };
            side = new float[] { 3f, 4f, 5f };
        }

        for (int i = 0; i < SHADER_VERT_COUNT; i++) {
            vertices[index++] = vert[i * 2]; // X
            vertices[index++] = vert[i * 2 + 1]; // Y
            vertices[index++] = side[i]; // Side (color flag; check frag & vert glsl files)
        }
    }

    @SuppressWarnings("static-access")
    public Display(int SCREEN_X, int SCREEN_Y, boolean DEBUG) {
        this.SCREEN_WIDTH = SCREEN_X;
        this.SCREEN_HEIGHT = SCREEN_Y;
        this.DEBUG = DEBUG;
        for (Integer bit = 0; bit < Screen; bit++) {
            float XU = bit % (Xdim);
            float YU = (float) Math.floor((bit) / Xdim);
            DrawTriangle(XU, YU, 0);
        }
        for (Integer bit = 0; bit < Screen; bit++) {
            float XU = bit % (Xdim);
            float YU = (float) Math.floor((bit) / Xdim);
            DrawTriangle(XU, YU, 1);
        }

        vertexBuffer.put(vertices).flip();

        if (this.DEBUG) {
            System.out.println("Display Created");
            for (int i = 0; i < vertices.length / SHADER_TOTAL_LEN; i++) {
                int base = i * SHADER_ATTRIBUTE_LEN;
                System.out.printf("Triangle %d:\n", i);
                for (int v = 0; v < SHADER_VERT_COUNT; v++) {
                    int offset = base + v * SHADER_ATTRIBUTE_LEN;
                    System.out.printf("\tVertex %d: X=%.2f, Y=%.2f, Side=%.2f\n",
                            v, vertices[offset], vertices[offset + 1], vertices[offset + 2]);
                }
            }
        }
    }

    public void Initialize() throws IOException {

        // Check if the window can and has been created
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        window = GLFW.glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Window", 0, 0);
        if (window == 0)
            throw new RuntimeException("Failed to create the GLFW window.");

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1);

        // Initialize shaders
        int shaderProgram = createShaderProgram();
        GL20.glUseProgram(shaderProgram);

        // Generate the VAO & VBO
        int vao = GL30.glGenVertexArrays();
        int vbo = GL15.glGenBuffers();
        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        // Insert data into the VBO (Vertex Buffer Object) (Specifically the triangle
        // vertexes)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

        // Triangle vertex positions
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, SHADER_ATTRIBUTE_LEN * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        // Triangle side values
        GL20.glVertexAttribPointer(1, SHADER_SIDE_LEN, GL11.GL_FLOAT, false, SHADER_ATTRIBUTE_LEN * Float.BYTES,
                2 * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        if (DEBUG) {
            for (int i = 0; i < vertexBuffer.limit(); i += SHADER_ATTRIBUTE_LEN) {
                System.out.printf("Vertex: X=%.2f, Y=%.2f, Side=%.2f\n",
                        vertexBuffer.get(i), vertexBuffer.get(i + 1), vertexBuffer.get(i + 2));
            }
        }

        // * Start the render loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Draw triangles
            GL30.glBindVertexArray(vao);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices.length / SHADER_ATTRIBUTE_LEN);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        // Cleanup the shaders & buffers on kill
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