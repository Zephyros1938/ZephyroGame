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
    private static long window;
    static private Integer SCREEN_WIDTH;
    static private Integer SCREEN_HEIGHT;

    static private final Float Xdim = 4f, Ydim = 4f;

    static public Integer Screen = (int) (Xdim * Ydim);

    static final Float incrX = (1f / Xdim);
    static final Float incrY = (1f / Ydim);

    static final Integer SHADER_VERT_COUNT = 3;
    static final Integer SHADER_COORD_LEN = 2;
    static final Integer SHADER_SIDE_LEN = 1;
    static final Integer SHADER_ATTRIBUTE_LEN = SHADER_COORD_LEN + SHADER_SIDE_LEN;
    static final Integer SHADER_TOTAL_LEN = SHADER_ATTRIBUTE_LEN * SHADER_VERT_COUNT;

    static private float[] vertices = new float[(int) (Xdim * Ydim) * SHADER_TOTAL_LEN];
    static final private FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);

    static int index = 0;

    static void DrawTriangle(float x, float y, int ID) {

        // Transform to NDC coordinates
        x = x * 2f / Xdim - 1f;
        y = y * 2f / Ydim - 1f;

        float[] vert = {
                x, y,
                x, y + incrY * 2,
                x + incrX * 2, y + incrY * 2
        };

        float[] side = new float[]{0f,1f,2f};
        if(ID%2==1){
            side = new float[]{3f,4f,5f};
        }

        for (int i = 0; i < SHADER_VERT_COUNT; i++) {
            vertices[index++] = vert[i * 2];       // X
            vertices[index++] = vert[i * 2 + 1];   // Y
            vertices[index++] = side[i];           // Side (color flag)
        }
    }

    public Display(int H, int W, int SCREEN_X, int SCREEN_Y) {
        for (Integer bit = 0; bit < Screen; bit++) {
            float XU = bit % (Xdim);
            float YU = (float) Math.floor((bit) / Xdim);
            DrawTriangle(XU, YU, bit);
        }
        System.out.println("Display Created");
        SCREEN_WIDTH = SCREEN_X;
        SCREEN_HEIGHT = SCREEN_Y;

        vertexBuffer.put(vertices).flip();

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

        // data -> VBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

        // positions
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, SHADER_ATTRIBUTE_LEN * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        // side
        GL20.glVertexAttribPointer(1, SHADER_SIDE_LEN, GL11.GL_FLOAT, false, SHADER_ATTRIBUTE_LEN * Float.BYTES, 2 * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        for (int i = 0; i < vertexBuffer.limit(); i += SHADER_ATTRIBUTE_LEN) {
            System.out.printf("Vertex: X=%.2f, Y=%.2f, Side=%.2f\n",
                vertexBuffer.get(i), vertexBuffer.get(i + 1), vertexBuffer.get(i + 2));
        }        

        // render loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // draw triangles
            GL30.glBindVertexArray(vao);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices.length / SHADER_ATTRIBUTE_LEN);

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
