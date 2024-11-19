package game.Display;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

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

    public void Initialize() throws IOException {

        window = new Window(SCREEN_WIDTH, SCREEN_HEIGHT, "Window", 0, 0);
        window.Init();

        defaultShader = new Shader(SHADER_ATTRIBUTE_LEN);

        Mesh testMesh = new Mesh( new float[]{
            -.5f, 0.5f, 0.0f,
            0.5f, 0.0f, 0.0f,
            -.5f, -.5f, 0.0f
        });

        defaultShader.Init(testMesh.getMesh());
        defaultShader.AddVertexAttrib(SHADER_COORD_LEN); // Triangle vertex positions

        window.addShader(defaultShader);
    }

    public void GameLoop() {
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

    public void Dispose() { // Cleanup the shaders & buffers on kill
        window.Terminate();
    }

    public void Run() throws IOException {
        Initialize();
        GameLoop();
        Dispose();
    }
}

class Keyboard extends GLFWKeyCallback {
    private static boolean[] keys = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key < 0) { // <- ❗️ IMPORTANT: This line is added to prevent `ArrayIndexOutOfBoundsException` in some cases.
            return;
        }
        keys[key] = action != GLFW.GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
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
        GLFW.glfwSetKeyCallback(WINDOW, new Keyboard());
        if (WINDOW == 0)
            throw new RuntimeException("Failed to create the GLFW window.");

        GLFW.glfwMakeContextCurrent(WINDOW);
        GL.createCapabilities();
        GL20.glEnable(GL20.GL_DEPTH_TEST);
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

    public void Init(float[] vertices) {
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        Init_VAO();
        Init_VBO();
        GL30.glBindVertexArray(VAO);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        if (verticesBuffer != null) {
            MemoryUtil.memFree(verticesBuffer);
        }
    }

    public void AddVertexAttrib(int SIZE) {
        GL20.glVertexAttribPointer(SHADER_CURRENT_INDEX,
                SIZE,
                GL11.GL_FLOAT, true,
                SHADER_ATTRIBUTE_LEN * Float.BYTES,
                SHADER_CURRENT_SIZE * Float.BYTES);
        GL20.glEnableVertexAttribArray(SHADER_CURRENT_INDEX++);
        SHADER_CURRENT_SIZE += SIZE;
    }

    public void AddUniformAttrib1f(float V0, CharSequence NAME) {
        int uniformLocation = GL20.glGetUniformLocation(SHADER_PROGRAM, NAME);
        GL20.glUniform1f(uniformLocation, V0);
    }

    public void AddUniformAttrib2f(float V0, float V1, CharSequence NAME) {
        int uniformLocation = GL20.glGetUniformLocation(SHADER_PROGRAM, NAME);
        GL20.glUniform2f(uniformLocation, V0, V1);
    }

    public void AddUniformAttrib3f(float V0, float V1, float V2, CharSequence NAME) {
        int uniformLocation = GL20.glGetUniformLocation(SHADER_PROGRAM, NAME);
        GL20.glUniform3f(uniformLocation, V0, V1, V2);
    }

    public void AddUniformAttrib4f(float V0, float V1, float V2, float V3, CharSequence NAME) {
        int uniformLocation = GL20.glGetUniformLocation(SHADER_PROGRAM, NAME);
        GL20.glUniform4f(uniformLocation, V0, V1, V2, V3);
    }

    private void Init_VAO() {
        this.VAO = GL30.glGenVertexArrays();
    }

    private void Init_VBO() {
        this.VBO = GL15.glGenBuffers();
    }
}

class Timer {
    private double lastLoopTime;
    private float timeCount;
    private int fpsCount, upsCount, fps, ups;

    private double getTime() {
        return System.nanoTime() / 1000000000.0;
    }

    public void Init() {
        lastLoopTime = getTime();
    }

    public float getDelta() {
        double time = getTime();
        float delta = (float) (time - lastLoopTime);
        lastLoopTime = time;
        timeCount += delta;
        return delta;
    }

    public void updateFPS() {
        fpsCount++;
    }

    public void updateUPS() {
        upsCount++;
    }

    public void update() {
        if (timeCount > 1f) {
            fps = fpsCount;
            fpsCount = 0;

            ups = upsCount;
            upsCount = 0;

            timeCount -= 1f;
        }
    }

    public int getUPS() {
        return ups > 0 ? ups : upsCount;
    }

    public int getFPS() {
        return fps > 0 ? fps : fpsCount;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}

class Mesh {

    private float[] POINT_POSITIONS;

    public Mesh(float[] pointPositions) {
        this.POINT_POSITIONS = pointPositions;
    }

    public float[] getMesh() {
        return POINT_POSITIONS;
    }
}