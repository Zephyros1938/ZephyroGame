import com.zephyros1938.game.Display.Display;

public class Main {
    private static int WIDTH = 40;
    private static int HEIGHT = 40;

    private static Display display = new Display(WIDTH, HEIGHT, 1920, 1080);

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        display.Initialize();
    }
}