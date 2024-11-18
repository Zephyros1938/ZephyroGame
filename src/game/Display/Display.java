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

    private static Window window;
    static private int SCREEN_WIDTH;
    static private int SCREEN_HEIGHT;

    static private final int Xdim = 4, Ydim = 4;

    static public int Screen = Xdim * Ydim;

    static final float incrX = (1f / Xdim), incrY = (1f / Ydim);

    static final int SHADER_VERT_COUNT = 3;
    static final int SHADER_COORD_LEN = 2;
    static final int SHADER_SIDE_LEN = 1;
    static final int SHADER_ATTRIBUTE_LEN = SHADER_COORD_LEN + SHADER_SIDE_LEN;
    static final int SHADER_TOTAL_LEN = SHADER_ATTRIBUTE_LEN * SHADER_VERT_COUNT;

    static private final int VERTICE_LENGTH = Xdim * Ydim * SHADER_TOTAL_LEN * 2;
    static private float[] vertices = new float[VERTICE_LENGTH];
    static final private FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length * 2);

    static int index = 0;

    static void DrawTriangle(float x, float y, int ID) {

        // Transform to NDC coordinates
        // x = x * 2f / Xdim - 1f;
        // y = y * 2f / Ydim - 1f;

        // vert = new float[] {
        //         x, y, // Bottom left
        //         x, y + incrY * 2, // Top left
        //         x + incrX * 2, y + incrY * 2 // Top right
        // };

        float[] side = new float[] { 0f, 1f, 2f };
        if (ID % 2 == 1) {
            side = new float[] { 3f, 4f, 5f };
        }

        for (int i = 0; i < SHADER_VERT_COUNT; i++) {
            vertices[index++] = x; // X
            vertices[index++] = y; // Y
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
            for (int i = 0; i < VERTICE_LENGTH / SHADER_TOTAL_LEN; i++) {
                int base = i * SHADER_ATTRIBUTE_LEN;
                System.out.printf("Triangle %d:\n", i);
                for (int v = 0; v < SHADER_VERT_COUNT; v++) {
                    int offset = base + v * SHADER_ATTRIBUTE_LEN;
                    System.out.printf("\tVertex %d: X=%.2f, Y=%.2f, Side=%.2f\n",
                            v, vertices[offset], vertices[offset + 1], vertices[offset + 2]);
                }
            }
        }

        vertices = new float[0];
    }

    public void Initialize() throws IOException {

        window = new Window(SCREEN_WIDTH, SCREEN_HEIGHT, "Window", 0, 0);
        window.Init();

        Shader shader1 = new Shader(SHADER_ATTRIBUTE_LEN);
        shader1.Init(vertexBuffer);
        shader1.AddVertexAttrib(SHADER_COORD_LEN); // Triangle vertex positions
        shader1.AddVertexAttrib(SHADER_SIDE_LEN); // Triangle side values
        shader1.AddUniformAttrib(2f / Xdim - 1f, "xMod");
        shader1.AddUniformAttrib(2f / Ydim - 1f, "yMod");

        window.addShader(shader1);

        if (DEBUG) {
            for (int i = 0; i < vertexBuffer.limit(); i += SHADER_ATTRIBUTE_LEN) {
                System.out.printf("Vertex: X=%.2f, Y=%.2f, Side=%.2f\n",
                        vertexBuffer.get(i), vertexBuffer.get(i + 1), vertexBuffer.get(i + 2));
            }
        }

        // * Start the render loop
        while (!GLFW.glfwWindowShouldClose(window.WINDOW)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            // Draw triangles
            GL30.glBindVertexArray(shader1.VAO);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, VERTICE_LENGTH / SHADER_ATTRIBUTE_LEN);

            GLFW.glfwSwapBuffers(window.WINDOW);
            GLFW.glfwPollEvents();
        }

        // Cleanup the shaders & buffers on kill
        window.Terminate();
    }
}

class ShaderUtils {
    public static int loadShader(String filepath, int type) throws IOException {
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

    public static int createShaderProgram() throws IOException {
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

class Window {
    private int SCREEN_HEIGHT = 720;
    private int SCREEN_WIDTH = 720;
    private CharSequence TITLE = "Default Window Title";
    private long MONITOR = 0;
    private long SHARE = 0;

    public long WINDOW;

    private Shader[] SHADER_LIST = new Shader[0];

    public Window(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public Window(int SCREEN_WIDTH, int SCREEN_HEIGHT, CharSequence TITLE) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.TITLE = TITLE;
    }

    public Window(int SCREEN_WIDTH, int SCREEN_HEIGHT, CharSequence TITLE, long MONITOR, long SHARE) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.TITLE = TITLE;
        this.MONITOR = MONITOR;
        this.SHARE = SHARE;
    }

    public void Init() {
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        WINDOW = GLFW.glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE, MONITOR, SHARE);
        if (WINDOW == 0)
            throw new RuntimeException("Failed to create the GLFW window.");

        GLFW.glfwMakeContextCurrent(WINDOW);
        GL.createCapabilities();
    }

    public void addShader(Shader s) {
        int SHADER_LIST_LEN = SHADER_LIST.length;
        SHADER_LIST = new Shader[SHADER_LIST_LEN + 1];
        SHADER_LIST[SHADER_LIST_LEN] = s;
    }

    public void Terminate() {
        for (Shader s : SHADER_LIST) {
            GL20.glDeleteProgram(s.SHADER_PROGRAM);
            GL15.glDeleteBuffers(s.VBO);
            GL30.glDeleteVertexArrays(s.VAO);
        }
        GLFW.glfwDestroyWindow(WINDOW);
        GLFW.glfwTerminate();
    }
}

class Shader {
    public int VAO;
    public int VBO;
    public int SHADER_PROGRAM;

    private int SHADER_ATTRIBUTE_LEN;

    private int SHADER_CURRENT_INDEX = 0;
    private int SHADER_CURRENT_SIZE = 0;

    public Shader(int SHADER_ATTRIBUTE_LEN) throws IOException {
        this.SHADER_ATTRIBUTE_LEN = SHADER_ATTRIBUTE_LEN;
        this.SHADER_PROGRAM = ShaderUtils.createShaderProgram();
        GL20.glUseProgram(SHADER_PROGRAM);
    }

    public void Init(FloatBuffer VertexData) {
        Init_VAO();
        Init_VBO();
        GL30.glBindVertexArray(VAO);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexData, GL15.GL_STATIC_DRAW);
    }

    public void AddVertexAttrib(int SIZE) {
        GL20.glVertexAttribPointer(SHADER_CURRENT_INDEX,
                SIZE,
                GL11.GL_FLOAT, false,
                SHADER_ATTRIBUTE_LEN * Float.BYTES,
                SHADER_CURRENT_SIZE * Float.BYTES);
        GL20.glEnableVertexAttribArray(SHADER_CURRENT_INDEX++);
        SHADER_CURRENT_SIZE += SIZE;
    }

    public void AddUniformAttrib(float VALUE, CharSequence NAME) {
        int uniformLocation = GL20.glGetUniformLocation(SHADER_PROGRAM, NAME);
        GL20.glUniform1f(uniformLocation, VALUE);
    }

    private void Init_VAO() {
        this.VAO = GL30.glGenVertexArrays();
    }

    private void Init_VBO() {
        this.VBO = GL15.glGenBuffers();
    }
}