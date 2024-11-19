package game.Display;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderUtils {
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

    public static int createShaderProgram(String vertexLocation, String fragmentLocation) throws IOException {
        int vertexShader = loadShader("src/game/Display/Shaders/" + vertexLocation, GL20.GL_VERTEX_SHADER);
        int fragmentShader = loadShader("src/game/Display/Shaders/" + fragmentLocation, GL20.GL_FRAGMENT_SHADER);
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