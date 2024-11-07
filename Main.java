import java.nio.CharBuffer;
import java.util.Map;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;

import javax.swing.*;

import com.zephyros1938.lib.TimeStamp.*;

public class Main {
    private static int WIDTH = 4;
    private static int HEIGHT = 4;

    private static Display display = new Display(WIDTH, HEIGHT);

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        display.Initialize();
    }
}

class Display extends JFrame implements KeyListener {
    static private Integer WIDTH = 25;
    static private Integer HEIGHT = 25;
    static private Integer SCREEN_SIZE = WIDTH * HEIGHT;
    static private Integer FONT_SIZE = 15;

    static private Integer playerRow;
    static private Integer playerColumn;
    static private Integer spawnPosition;
    static private Integer playerPosition;

    static private CharBuffer Screen;
    static private StringBuilder ScreenText = new StringBuilder();
    static private String FontStyle;

    static private JEditorPane jTextAreaBoard = new JEditorPane("text/html", "");
    static private JFrame window = new JFrame("Game Display");
    static private Font defaultFont = new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE);

    static private TimeStamp timeStamps = new TimeStamp();

    public static int clamp(int min, int max, int value) {
        return (value < min ? min : value) > max ? max : (value < min ? min : value);
    }

    static private Map<Integer, Integer> movementKeySet = Map.of(
            87, 0,
            38, 0,
            83, 1,
            40, 1,
            65, 2,
            37, 2,
            68, 3,
            39, 3);

    public Display(int H, int W) {
        System.out.println("Display Created");
        HEIGHT = H;
        WIDTH = W;
        SetDimensions(WIDTH, HEIGHT);
    }

    public void ListVariables() {
        System.out.println("WIDTH SET : " + WIDTH + "\nHEIGHT SET : " + HEIGHT + "\nSCREEN SIZE : " + SCREEN_SIZE
                + "\nSPAWN POSITION : " + spawnPosition + " ROW : " + playerRow + " COL : " + playerColumn);
    }

    private static void UpdateScreenBuffer() {
        for (int i = 0; i < SCREEN_SIZE; i++) {
            if (i == playerPosition) {
                Screen.put(i, Objects.PLAYER.value);
            } else {
                Screen.put(i, Objects.AIR.value);
            }
        }
    } /* test */

    private static void InitializeFont() {
        StringBuilder f = new StringBuilder();

        f.append("<style>p {font-family: monospace;color: red;font-size: ");
        f.append(FONT_SIZE);
        f.append("px;letter-spacing: ");
        f.append(FONT_SIZE * 2.5);
        f.append("px;</style><p>");

        FontStyle = f.toString();
                
    }

    public static void UpdateScreenVisible() throws NullPointerException {
        ScreenText = new StringBuilder();
        ScreenText.append(FontStyle);
        for (Byte id = 0; id < SCREEN_SIZE; id++) {
            // System.out.println(id * WIDTH + " " + (id + 1) * WIDTH); // debug the indexes
            // for the screen
            ScreenText.append(Screen.subSequence(id, (id + 1)));
            if (id % HEIGHT == HEIGHT - 1 && id != SCREEN_SIZE-1) {
                ScreenText.append("<br>");
            }
        }
        ScreenText.append("</p>");
        jTextAreaBoard.setText(ScreenText.toString());
    }

    /* START INITIALIZATION */

    private void SetupDisplay() {
        timeStamps.Start("Setting up JFrame");
        jTextAreaBoard.setEditable(false);
        jTextAreaBoard.setName("Game");

        window.setDefaultCloseOperation(3); // Exit on close
        window.add(jTextAreaBoard);
        window.setSize(WIDTH * FONT_SIZE * 2, HEIGHT * FONT_SIZE * 2);
        window.setLocationRelativeTo(null);

        jTextAreaBoard.addKeyListener(this);
        jTextAreaBoard.setBorder(
                BorderFactory.createCompoundBorder(
                        jTextAreaBoard.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        timeStamps.End();
    }

    private void SetDimensions(int W, int H) {
        timeStamps.Start("Setting JFrame Dimensions");
        SCREEN_SIZE = (WIDTH * HEIGHT);
        Screen = CharBuffer.allocate(SCREEN_SIZE);

        playerRow = (WIDTH / 2);
        playerColumn = (HEIGHT / 2);

        spawnPosition = (playerRow * HEIGHT) + playerColumn;
        playerPosition = spawnPosition;
        Screen = CharBuffer.allocate(SCREEN_SIZE);
        for (int i = 0; i < SCREEN_SIZE; i++) {
            if (i == playerPosition) {
                Screen.put(i, Objects.PLAYER.value);
            } else {
                Screen.put(i, Objects.AIR.value);
            }
        }

        ListVariables();
        timeStamps.End();
    }

    private static void InitScreen() throws NullPointerException {
        ScreenText = new StringBuilder();
        for (Byte id = 0; id < HEIGHT; id++) {
            // System.out.println(id * WIDTH + " " + (id + 1) * WIDTH); // debug the indexes
            // for the screen
            ScreenText.append(Screen.subSequence(id * WIDTH, (id + 1) * WIDTH));
            if (id != HEIGHT - 1) {
                ScreenText.append("\n");
            }
        }
        jTextAreaBoard.setText(ScreenText.toString());
        window.setVisible(true);
    }

    public void Initialize() {
        SetupDisplay();
        InitScreen();
        InitializeFont();
    }

    /* END INITIALIZATION */

    private class Movement {
        public class Character {
            public static void CharacterMovementSwitch(int key) throws Exception {
                switch (key) {
                    case 0:
                        playerRow = clamp(0, HEIGHT - 1, --playerRow);
                        Move();
                        break;
                    case 1:
                        playerRow = clamp(0, HEIGHT - 1, ++playerRow);
                        Move();
                        break;
                    case 2:
                        playerColumn = clamp(0, WIDTH - 1, --playerColumn);
                        Move();
                        break;
                    case 3:
                        playerColumn = clamp(0, WIDTH - 1, ++playerColumn);
                        Move();
                        break;
                    default:
                        break;
                }
            }

            public static void Move() {
                // timeStamps.Start("Moving Player");
                playerPosition = ((HEIGHT * playerRow) + playerColumn);
                UpdateScreenBuffer();
                UpdateScreenVisible();
                // timeStamps.End();
            }
        }
    }

    enum Objects {
        LAND('#'),
        AIR('—'),
        PLAYER('Y'),
        ORB('O'),
        ENEMY(':'),
        BIGENEMY('%');

        private final char value;

        Objects(char value) {
            this.value = value;
        }

        public char getObject() {
            return value;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle key press events here
        // System.out.println("Key pressed: " + e.getKeyCode());
        try {
            Movement.Character.CharacterMovementSwitch(movementKeySet.get(e.getKeyCode()));
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key release events here
        // System.out.println("Key released: " + e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle key typed events here
        // System.out.println("Key typed: " + e.getKeyChar());
    }
}