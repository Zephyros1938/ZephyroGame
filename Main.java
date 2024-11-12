import com.zephyros1938.game.Display.Display;

public class Main {
    private static int WIDTH = 50;
    private static int HEIGHT = 50;

    private static Display display = new Display(WIDTH, HEIGHT, 1920, 1080);

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        display.Initialize();

        //System.out.println((0b0101 + (~0b0111)) + " " + (5 - 8));
    }
}