package game.Display;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Shader {

    public static final int SHADER_COORD_LEN = 2;
    public static final int SHADER_TEX_COORD_LEN = 2;

    public int VAO = GL30.glGenVertexArrays();
    public int VBO = GL30.glGenBuffers();
    public int TBO = GL30.glGenBuffers();
    public int SHADER_PROGRAM;

    private int SHADER_ATTRIBUTE_LEN;

    private int SHADER_CURRENT_INDEX = 0;
    private int SHADER_CURRENT_SIZE = 0;
    private int SHADER_CURRENT_TEXTURE_UNIT = 0;

    public Shader(int SHADER_ATTRIBUTE_LEN) throws IOException {
        this.SHADER_ATTRIBUTE_LEN = SHADER_ATTRIBUTE_LEN;
        this.SHADER_PROGRAM = ShaderUtils.createShaderProgram("default_vert.glsl", "default_frag.glsl");
    }

    public void use(){
        GL30.glUseProgram(SHADER_PROGRAM);
    }

    public void init() {
        GL30.glBindVertexArray(VAO);
    }

    public void addVertexCoords(FloatBuffer vertices) {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, VBO);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(SHADER_CURRENT_INDEX,
                SHADER_COORD_LEN,
                GL30.GL_FLOAT, true,
                SHADER_COORD_LEN * Float.BYTES,
                SHADER_CURRENT_SIZE * Float.BYTES);
        GL30.glEnableVertexAttribArray(SHADER_CURRENT_INDEX++);
        SHADER_CURRENT_SIZE += SHADER_COORD_LEN;
    }

    public void addTexCoords(float[] TEX_COORDS) {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, TBO);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, TEX_COORDS, GL30.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(SHADER_CURRENT_INDEX,
                SHADER_TEX_COORD_LEN,
                GL30.GL_FLOAT, true,
                SHADER_TEX_COORD_LEN * Float.BYTES,
                SHADER_CURRENT_SIZE * Float.BYTES);
        GL30.glEnableVertexAttribArray(SHADER_CURRENT_INDEX++);
        SHADER_CURRENT_SIZE += SHADER_TEX_COORD_LEN;
    }

    public void addVertexAttribPointer(int SIZE) {
        GL30.glVertexAttribPointer(SHADER_CURRENT_INDEX,
                SIZE,
                GL30.GL_FLOAT, true,
                SHADER_ATTRIBUTE_LEN * Float.BYTES,
                SHADER_CURRENT_SIZE * Float.BYTES);
        GL30.glEnableVertexAttribArray(SHADER_CURRENT_INDEX++);
        SHADER_CURRENT_SIZE += SIZE;
    }

    public void addUniformAttrib1f(float V0, CharSequence NAME) {
        int uniformLocation = GL30.glGetUniformLocation(SHADER_PROGRAM, NAME);
        GL30.glUniform1f(uniformLocation, V0);
    }

    public void addUniformAttrib2f(float V0, float V1, CharSequence NAME) {
        int uniformLocation = GL30.glGetUniformLocation(SHADER_PROGRAM, NAME);
        GL30.glUniform2f(uniformLocation, V0, V1);
    }

    public void addUniformAttrib3f(float V0, float V1, float V2, CharSequence NAME) {
        int uniformLocation = GL30.glGetUniformLocation(SHADER_PROGRAM, NAME);
        GL30.glUniform3f(uniformLocation, V0, V1, V2);
    }

    public void addUniformAttrib4f(float V0, float V1, float V2, float V3, CharSequence NAME) {
        int uniformLocation = GL30.glGetUniformLocation(SHADER_PROGRAM, NAME);
        GL30.glUniform4f(uniformLocation, V0, V1, V2, V3);
    }

    public void bindTexture(Texture tex, int TEX_WIDTH, int TEX_HEIGHT) {
        int TEX = GL30.glGetUniformLocation(SHADER_PROGRAM, tex.getName());
        GL30.glUniform1i(TEX, SHADER_CURRENT_TEXTURE_UNIT++); // Useful : http://www.lighthouse3d.com/tutorials/glsl-tutorial/attribute-variables/
    }
}