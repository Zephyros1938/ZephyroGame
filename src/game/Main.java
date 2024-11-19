package game;

import game.Display.*;

public class Main {
    private static Display display = new Display(720, 720);

    public static void main(String[] args) throws Exception {
        //TextureUtils.loadTexture("Default.png");
        display.run();
    }
}