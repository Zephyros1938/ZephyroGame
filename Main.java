import com.zephyros1938.game.Display.Display;
import com.zephyros1938.lib.math.PerlinNoise.PerlinNoise;


public class Main {
    private static int WIDTH = 50;
    private static int HEIGHT = 50;

    private static PerlinNoise p = new PerlinNoise(1);

    private static Display display = new Display(WIDTH, HEIGHT, 1920, 1080);

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        display.Initialize();
    }
}