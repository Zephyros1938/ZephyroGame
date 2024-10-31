import java.nio.CharBuffer;

import java.util.Map;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;

import javax.swing.*;

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

    public Display() {
        System.out.println("Display Created");
    }

    public void SetupDisplay() {
        timeStamps.Start("Setting up JFrame");
        jTextArea.setEditable(false);
        SetupFont();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(jTextArea);

        window.setSize(WIDTH * FONT_SIZE * 2, HEIGHT * FONT_SIZE * 2);
        window.setVisible(true);
        timeStamps.End();

        jTextArea.addKeyListener(this);

        jTextArea.setBorder(BorderFactory.createCompoundBorder(
                jTextArea.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    public void SetDimensions(byte W, byte H) {
        timeStamps.Start("Setting JFrame Dimensions");
        HEIGHT = H;
        WIDTH = W;
        Screen = CharBuffer.allocate(HEIGHT * WIDTH);
        for (int i = 0; i < Screen.capacity(); i++) {
            Screen.put(i, Objects.AIR.value);
        }
        timeStamps.End();
    }

    private void UpdateScreenBuffer() {
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

class TimeStamp {

    private long Time;
    private String Task;

    public void Start(String tsk) {
        Time = System.currentTimeMillis();
        Task = tsk;
    }

    public void End() {
        System.out.println("Completed " + Task + " In " + (System.currentTimeMillis() - Time) + "ms");
    }
}

class HexDecoder {
    public static int[] Bit_24(int x) {
        int r = x >> 0x10 & 0xff;
        int g = x >> 0x08 & 0xff;
        int b = x & 0xff;

        int[] combo = { r, g, b };

        return combo;
    }

    public static int[] Bit_16(int x) {
        int r = (x & 0xf00) >> 0x008;
        int g = (x & 0x0f0) >> 0x004;
        int b = x & 0x00f;

        r = r << 4 | r;
        g = g << 4 | g;
        b = b << 4 | b;

        int[] combo = { r, g, b };

        return combo;
    }
}