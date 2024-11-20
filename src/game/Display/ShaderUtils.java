package game.Display;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL30;

public class ShaderUtils {
    public static int loadShader(String filepath, int type) throws IOException {
        StringBuilder shaderSource = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
        }
        int shaderID = GL30.glCreateShader(type);
        GL30.glShaderSource(shaderID, shaderSource);
        GL30.glCompileShader(shaderID);
        if (GL30.glGetShaderi(shaderID, GL30.GL_COMPILE_STATUS) == GL30.GL_FALSE) {
            throw new RuntimeException("Error creating shader: " + GL30.glGetShaderInfoLog(shaderID));
        }
        return shaderID;
    }

    public static int createShaderProgram(String vertexLocation, String fragmentLocation) throws IOException {
        int vertexShader = loadShader("src/game/Display/Shaders/" + vertexLocation, GL30.GL_VERTEX_SHADER);
        int fragmentShader = loadShader("src/game/Display/Shaders/" + fragmentLocation, GL30.GL_FRAGMENT_SHADER);
        int shaderProgram = GL30.glCreateProgram();
        GL30.glAttachShader(shaderProgram, vertexShader);
        GL30.glAttachShader(shaderProgram, fragmentShader);
        GL30.glLinkProgram(shaderProgram);
        if (GL30.glGetProgrami(shaderProgram, GL30.GL_LINK_STATUS) == GL30.GL_FALSE) {
            throw new RuntimeException("Error linking program: " + GL30.glGetProgramInfoLog(shaderProgram));
        }
        GL30.glDeleteShader(vertexShader);
        GL30.glDeleteShader(fragmentShader);
        return shaderProgram;
    }
}