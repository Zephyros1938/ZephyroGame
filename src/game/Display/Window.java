package game.Display;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

public class Window {
    private int SCREEN_HEIGHT = 720;
    private int SCREEN_WIDTH = 720;
    private CharSequence TITLE = "Default Window Title";
    private long MONITOR = 0;
    private long SHARE = 0;

    private Shader[] SHADER_LIST = new Shader[0];

    public long WINDOW;

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

    public void init() {
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        WINDOW = GLFW.glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE, MONITOR, SHARE);
        GLFW.glfwSetKeyCallback(WINDOW, new Keyboard());
        if (WINDOW == 0)
            throw new RuntimeException("Failed to create the GLFW window.");

        GLFW.glfwMakeContextCurrent(WINDOW);
        GL.createCapabilities();
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        GL30.glEnable(GL30.GL_TEXTURE_2D);
    }

    public void addShader(Shader s) {
        int SHADER_LIST_LEN = SHADER_LIST.length;
        SHADER_LIST = new Shader[SHADER_LIST_LEN + 1];
        SHADER_LIST[SHADER_LIST_LEN] = s;
    }

    public void terminate() {
        for (Shader s : SHADER_LIST) {
            GL30.glDeleteProgram(s.SHADER_PROGRAM);
            GL30.glDeleteBuffers(s.VBO);
            GL30.glDeleteVertexArrays(s.VAO);
        }
        GLFW.glfwDestroyWindow(WINDOW);
        GLFW.glfwTerminate();
    }
}