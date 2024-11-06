import java.nio.CharBuffer;
import java.util.Map;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;

import javax.swing.*;

import com.zephyros1938.lib.TimeStamp.*;

public class Main {
    private static int WIDTH = 25;
    private static int HEIGHT = 25;

    private static Display display = new Display();

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {

        display.SetDimensions(WIDTH, HEIGHT);

        display.SetupDisplay();

        display.InitScreen();
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

    static private JTextArea jTextAreaBoard = new JTextArea(WIDTH, HEIGHT);
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

    public Display() {
        System.out.println("Display Created");
    }

    public void ListVariables() {
        System.out.println("WIDTH SET : " + WIDTH + "\nHEIGHT SET : " + HEIGHT + "\nSCREEN SIZE : " + SCREEN_SIZE
                + "\nSPAWN POSITION : " + spawnPosition + " ROW : " + playerRow + " COL : " + playerColumn);
    }

    public void SetupDisplay() {
        timeStamps.Start("Setting up JFrame");
        jTextAreaBoard.setEditable(false);
        jTextAreaBoard.setName("Game");
        SetupFont();

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

    public void SetDimensions(int W, int H) throws Exception {
        timeStamps.Start("Setting JFrame Dimensions");
        HEIGHT = H;
        WIDTH = W;
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

    private static void UpdateScreenBuffer() {
        for (int i = 0; i < SCREEN_SIZE; i++) {
            if (i == playerPosition) {
                Screen.put(i, Objects.PLAYER.value);
            } else {
                Screen.put(i, Objects.AIR.value);
            }
        }
    }

    public static void UpdateScreenVisible() throws NullPointerException {
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
    }

    public static void InitScreen() throws NullPointerException {
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

    private void SetupFont() {
        // Setup JTextArea font to have a roughly 1:1 ratio
        // character width and height
        @SuppressWarnings("unchecked") // Ignore unchecked cast
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) defaultFont.getAttributes();
        attributes.put(TextAttribute.TRACKING, FONT_SIZE / 10);
        jTextAreaBoard.setFont(Font.getFont(attributes));
    }

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
        PLAYER('@'),
        ORB('O'),
        ENEMY(':');

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