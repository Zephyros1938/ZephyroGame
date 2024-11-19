package game.Display;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLUtil;

import de.matthiasmann.twl.utils.PNGDecoder;

public class TextureUtils { // Possibly Useful: https://github.com/mattdesl/lwjgl-basics/wiki/textures
    public static Texture loadTexture(String fileName) throws IOException {
        PNGDecoder decoder;

        // load png file
        try {
            InputStream i = new FileInputStream("src/game/Display/Textures/"+fileName);
            decoder = new PNGDecoder(i);
        } catch (Exception e) {
            System.out.println(e);
            return new Texture(0);
        }

        // create a byte buffer big enough to store RGBA values
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());

        // decode
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);

        // flip the buffer so its ready to read
        buffer.flip();

        // create a texture
        int id = GL20.glGenTextures();

        // bind the texture
        GL20.glBindTexture(GL20.GL_TEXTURE_2D, id);

        // tell opengl how to unpack bytes
        GL20.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 1);

        // set the texture parameters, can be GL_LINEAR or GL_NEAREST
        GL20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_LINEAR);
        GL20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_LINEAR);

        // upload texture
        GL20.glTexImage2D(GL20.GL_TEXTURE_2D, 0, GL20.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL20.GL_RGBA,
                GL20.GL_UNSIGNED_BYTE, buffer);

        // Generate Mip Map
        GL30.glGenerateMipmap(GL20.GL_TEXTURE_2D);

        return new Texture(id);
    }
}

class Texture {

    private int id;

    public Texture(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}