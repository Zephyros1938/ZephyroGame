import java.nio.CharBuffer;
import java.util.Map;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;

import javax.swing.*;

import com.zephyros1938.lib.TimeStamp.*;

public class Main {

    private static int WIDTH = 10;
    private static int HEIGHT = 10;

    private static Display display = new Display();

    public static void main(String[] args) throws Exception {
        display.SetDimensions((byte) WIDTH, (byte) HEIGHT);

        display.SetupDisplay();

        display.UpdateScreenVisible();
    }
}

class Display extends JFrame implements KeyListener {
    private byte WIDTH = 0;
    private byte HEIGHT = 0;
    private byte FONT_SIZE = 10;

    private CharBuffer Screen = CharBuffer.allocate(HEIGHT * WIDTH);
    private StringBuilder ScreenText = new StringBuilder();

    private TimeStamp timeStamps = new TimeStamp();

    private JTextArea jTextArea = new JTextArea(WIDTH, HEIGHT);

    private JFrame window = new JFrame("Game Display");

    private Font defaultFont = new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE);

    private Map<Integer, Object> movementKeySet = Map.of(
            87, (byte) 0,
            38, (byte) 0,
            83, (byte) 1,
            40, (byte) 1,
            65, (byte) 2,
            37, (byte) 2,
            68, (byte) 3,
            39, (byte) 3);

    public Display() {
        System.out.println("Display Created");
    }

    public void SetupDisplay() {
        timeStamps.Start("Setting up JFrame");
        jTextArea.setEditable(false);
        jTextArea.setSelectionStart(0);
        jTextArea.setSelectionEnd(0);
        jTextArea.setFocusable(false);
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

    public void SetDimensions(byte W, byte H) throws Exception {
        timeStamps.Start("Setting JFrame Dimensions");
        HEIGHT = H;
        WIDTH = W;
        Screen = CharBuffer.allocate(HEIGHT * WIDTH);
        for (int i = 0; i < Screen.capacity(); i++) {
            Screen.put(i, Objects.AIR.value);
        }
        timeStamps.End();
    }

    private void UpdateScreenBuffer(byte position, Objects e) {
        for (int i = 0; i < Screen.capacity(); i++) {
            Screen.put(i, Objects.AIR.value);
        }
    }

    public void UpdateScreenVisible() throws NullPointerException {
        ScreenText = new StringBuilder();
        for (Byte id = 0; id < HEIGHT; id++) {
            ScreenText.append(Screen.subSequence(id, id + WIDTH));
            if (id != HEIGHT - 1) {
                ScreenText.append("\n");
            }
        }
        jTextArea.insert(ScreenText.toString(), 0);
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
            public static void CharacterMovementSwitch(int key) {
                switch (key) {
                    case 87:
                    case 38:
                        System.out.println("up");
                        break;
                    case 83:
                    case 40:
                        System.out.println("down");
                        break;
                    case 65:
                    case 37:
                        System.out.println("left");
                        break;
                    case 68:
                    case 39:
                        System.out.println("right");
                        break;
                }
            }

            private static void CharacterMovement(byte dir) {
                switch (dir) {
                    case 0:
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
        Movement.Character.CharacterMovementSwitch(e.getKeyCode());
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