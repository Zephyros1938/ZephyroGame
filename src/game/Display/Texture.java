package game.Display;

import java.io.IOException;
import org.lwjgl.opengl.GL30;

public class Texture {

    private int ID;
    private int TEX_WIDTH;
    private int TEX_HEIGHT;
    private CharSequence TEXTURE_NAME;
    private int ATLAS_SLICE_X;
    private int ATLAS_SLICE_Y;

    public Texture(CharSequence FILENAME, CharSequence TEXTURE_NAME, int ATLAS_SLICE_X, int ATLAS_SLICE_Y) {
        try {
            this.ID = TextureUtils.loadTexture(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ATLAS_SLICE_X = ATLAS_SLICE_X;
        this.ATLAS_SLICE_Y = ATLAS_SLICE_Y;
        this.TEXTURE_NAME = TEXTURE_NAME;
    }

    public int getId() {
        return this.ID;
    }

    public CharSequence getName(){
        return this.TEXTURE_NAME;
    }

    public int getDimensionX() {
        return this.TEX_WIDTH;
    }

    public int getDimensionY() {
        return this.TEX_HEIGHT;
    }
}