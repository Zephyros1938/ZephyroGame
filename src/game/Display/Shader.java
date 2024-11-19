package game.Display;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Shader {
    public int VAO;
    public int VBO;
    public int SHADER_PROGRAM;

    private int SHADER_ATTRIBUTE_LEN;

    private int SHADER_CURRENT_INDEX = 0;
    private int SHADER_CURRENT_SIZE = 0;

    public Shader(int SHADER_ATTRIBUTE_LEN) throws IOException {
        this.SHADER_ATTRIBUTE_LEN = SHADER_ATTRIBUTE_LEN;
        this.SHADER_PROGRAM = ShaderUtils.createShaderProgram("default_vert.glsl", "default_frag.glsl");
        GL20.glUseProgram(SHADER_PROGRAM);
    }

    public void Init(FloatBuffer vertices) {
        FloatBuffer verticesBuffer = vertices;

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