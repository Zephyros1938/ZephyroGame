import java.nio.CharBuffer;
import java.util.Map;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;

import javax.swing.*;

import com.zephyros1938.lib.TimeStamp.*;
import com.zephyros1938.lib.CharBufferUtils.*;

public class Main {
    private static int WIDTH = 25;
    private static int HEIGHT = 25;

    private static Display display = new Display();

    public static void main(String[] args) throws Exception {

        display.SetDimensions(WIDTH, HEIGHT);

        display.SetupDisplay();

        display.UpdateScreenVisible();
    }
}

class Display extends JFrame implements KeyListener {
    static private Integer WIDTH = 25;
    static private Integer HEIGHT = 25;
    static private Integer SCREEN_SIZE;
    static private Integer SPAWN_POSITION;
    static private Integer FONT_SIZE = 10;

    static private Integer Row;
    static private Integer Column;
    static private Integer playerPosition;

    static private CharBuffer Screen;
    static private StringBuilder ScreenText = new StringBuilder();

    static private TimeStamp timeStamps = new TimeStamp();

    static private JTextArea jTextArea = new JTextArea(WIDTH, HEIGHT);

    static private JFrame window = new JFrame("Game Display");

    static private Font defaultFont = new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE);

    public static Integer clamp(int min, int max, int value) {
        return Math.max(min, Math.min(max, value));
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
                + "\nSPAWN POSITION : " + SPAWN_POSITION + " ROW : " + Row + " COL : " + Column);
    }

    public void SetupDisplay() {
        timeStamps.Start("Setting up JFrame");
        jTextArea.setEditable(false);
        jTextArea.setSelectionStart(-1);
        jTextArea.setSelectionEnd(-1);
        // jTextArea.setFocusable(false);
        jTextArea.setName("Game");
        SetupFont();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(jTextArea);

        window.setSize(WIDTH * FONT_SIZE * 2, HEIGHT * FONT_SIZE * 2);
        window.setVisible(true);
        window.setLocationRelativeTo(null);

        jTextArea.addKeyListener(this);

        jTextArea.setBorder(
                BorderFactory.createCompoundBorder(
                        jTextArea.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        timeStamps.End();
    }

    public void SetDimensions(int W, int H) throws Exception {
        timeStamps.Start("Setting JFrame Dimensions");
        HEIGHT = H;
        WIDTH = W;
        SCREEN_SIZE = (WIDTH * HEIGHT);
        Screen = CharBuffer.allocate(SCREEN_SIZE);

        Row = (WIDTH / 2);
        Column = (HEIGHT / 2);

        SPAWN_POSITION = (Row * HEIGHT) + Column;
        playerPosition = SPAWN_POSITION;
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
        jTextArea.setText(ScreenText.toString());
    }

    private void SetupFont() {
        // Setup JTextArea font to have a roughly 1:1 ratio
        // character width and height
        @SuppressWarnings("unchecked") // Ignore unchecked cast
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) defaultFont.getAttributes();
        attributes.put(TextAttribute.TRACKING, FONT_SIZE / 10);
        jTextArea.setFont(Font.getFont(attributes));
    }

    private class Movement {
        public class Character {
            public static void CharacterMovementSwitch(int key) throws Exception {
                switch (key) {
                    case 0:
                        Row = clamp(0, HEIGHT - 1, --Row);
                        playerPosition = ((HEIGHT * Row) + Column);
                        System.out.println("ROW : " + Row + " COL : " + Column + " POS : " + playerPosition);
                        UpdateScreenBuffer();
                        UpdateScreenVisible();
                        break;
                    case 1:
                        Row = clamp(0, HEIGHT - 1, ++Row);
                        playerPosition = ((HEIGHT * Row) + Column);
                        System.out.println("ROW : " + Row + " COL : " + Column + " POS : " + playerPosition);
                        UpdateScreenBuffer();
                        UpdateScreenVisible();
                        break;
                    case 2:
                        Column = clamp(0, WIDTH - 1, --Column);
                        playerPosition = ((WIDTH * Row) + Column);
                        System.out.println("ROW : " + Row + " COL : " + Column + " POS : " + playerPosition);
                        UpdateScreenBuffer();
                        UpdateScreenVisible();
                        break;
                    case 3:
                        Column = clamp(0, WIDTH - 1, ++Column);
                        playerPosition = ((WIDTH * Row) + Column);
                        System.out.println("ROW : " + Row + " COL : " + Column + " POS : " + playerPosition);
                        UpdateScreenBuffer();
                        UpdateScreenVisible();
                        break;
                }
            }
        }
    }

    enum Objects {
        LAND('#'),
        AIR('.'),
        PLAYER('Y'),
        ORB('O'),
        ENEMY('X');

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