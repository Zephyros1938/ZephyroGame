package game.Display;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;

public class TextureUtils { // Possibly Useful: https://github.com/mattdesl/lwjgl-basics/wiki/textures
    public static Texture loadTexture(String fileName) throws IOException {
        // Load PNG file
        PNGDecoder decoder;
        ByteBuffer buffer;

        try (InputStream i = new FileInputStream("src/game/Display/Textures/" + fileName)) {
            decoder = new PNGDecoder(i);

            // Create a byte buffer big enough to store RGBA values
            buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
        } catch (Exception e) {
            System.err.println("Error loading texture: " + e.getMessage());
            return new Texture(0);
        }

        // Generate texture ID
        int id = GL30.glGenTextures();
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, id);

        // Set texture parameters
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_REPEAT);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_REPEAT);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR_MIPMAP_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);

        // Upload texture
        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL30.GL_RGBA,
                GL30.GL_UNSIGNED_BYTE, buffer);

        // Generate MipMap
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);

        return new Texture(id);
    }

}

class Texture {

    private int ID;

    public Texture(int ID) {
        this.ID = ID;
    }

    public int getId() {
        return ID;
    }

    public void init(){
        GL30.glActiveTexture(ID);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, ID);
    }
}