package game;

import game.Display.Display;

public class Main {
    private static int WIDTH = 55;
    private static int HEIGHT = 55;

    private static Display display = new Display(WIDTH, HEIGHT, 720, 720);

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        System.out.println(WIDTH * HEIGHT + " SIZE");

        display.Initialize();
    }
}