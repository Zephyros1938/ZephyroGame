package com.zephyros1938.game.Display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.CharBuffer;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;

import com.zephyros1938.lib.TimeStamp.TimeStamp;

public class Display implements KeyListener {
    static private Integer WIDTH = 25;
    static private Integer HEIGHT = 25;
    static private Integer SCREEN_WIDTH;
    static private Integer SCREEN_HEIGHT;
    static private Integer SCREEN_SIZE = WIDTH * HEIGHT;
    static private Float FONT_SIZE = 0.8f;

    static private Integer playerRow;
    static private Integer playerColumn;
    static private Integer spawnPosition;
    static private Integer playerPosition;

    static private CharBuffer Screen;
    static private StringBuilder ScreenText = new StringBuilder();
    static private String FontStyle;

    static private JEditorPane jTextAreaBoard = new JEditorPane("text/html", "");
    static private JFrame window = new JFrame("Game Display");

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

    public Display(int H, int W, int SCREEN_X, int SCREEN_Y) {
        System.out.println("Display Created");
        HEIGHT = H;
        WIDTH = W;
        SCREEN_WIDTH = SCREEN_X;
        SCREEN_HEIGHT = SCREEN_Y;
        SetDimensions();
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
    }

    private static void InitializeFont() {
        StringBuilder f = new StringBuilder();

        f.append("<html><head><style>pre {font-family: monospace;font-size: \"");
        f.append(FONT_SIZE);
        f.append("em\";letter-spacing: \"");
        f.append(100);
        f.append("em\";}</style></head><body><pre><s>");

        FontStyle = f.toString();
    }

    public static void UpdateScreenVisible() throws NullPointerException {
        ScreenText = new StringBuilder();
        ScreenText.append(FontStyle);
        for (Integer id = 0; id < SCREEN_SIZE; id++) {
            // System.out.println(id * WIDTH + " " + (id + 1) * WIDTH); // debug the indexes
            // for the screen
            char currentChar = Screen.get(id);
            ScreenText.append(currentChar);
            ScreenText.append(currentChar);
            if (id % HEIGHT == HEIGHT - 1 && id != SCREEN_SIZE - 1) {
                ScreenText.append("<br>");
            }
        }
        ScreenText.append("</s></pre></body></html>");
        // System.out.println(ScreenText);
        jTextAreaBoard.setText(ScreenText.toString());
    }

    /* START INITIALIZATION */

    private void SetupDisplay() {
        timeStamps.Start("Setting up JFrame");
        jTextAreaBoard.setEditable(false);
        jTextAreaBoard.setName("Game");

        window.setDefaultCloseOperation(3); // Exit on close
        window.add(jTextAreaBoard);
        window.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        window.setLocationRelativeTo(null);

        jTextAreaBoard.addKeyListener(this);
        jTextAreaBoard.setBorder(
                BorderFactory.createCompoundBorder(
                        jTextAreaBoard.getBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        timeStamps.End();
    }

    private void SetDimensions() {
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
        UpdateScreenVisible();
        window.setVisible(true);
    }

    public void Initialize() {
        SetupDisplay();
        InitializeFont();
        InitScreen();
    }

    /* END INITIALIZATION */

    private class Movement {
        public class Character {
            public static void CharacterMovementSwitch(int key) throws Exception {
                switch (key) {
                    case 0: // UP
                        if (Detection.CanMove(0)) {
                            playerRow = clamp(0, HEIGHT - 1, --playerRow);
                            Move();
                        }
                        break;
                    case 1: // DOWN
                        if (Detection.CanMove(1)) {
                            playerRow = clamp(0, HEIGHT - 1, ++playerRow);
                            Move();
                        }
                        break;
                    case 2: // LEFT
                        if (Detection.CanMove(2)) {
                            playerColumn = clamp(0, WIDTH - 1, --playerColumn);
                            Move();
                        }
                        break;
                    case 3: // RIGHT
                        if (Detection.CanMove(3)) {
                            playerColumn = clamp(0, WIDTH - 1, ++playerColumn);
                            Move();
                        }
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

        public class Detection {
            static Boolean CanMove(int dir) {
                int targetPosition, targetRow = playerRow, targetColumn = playerColumn;
                switch (dir) {
                    case 0: // UP
                        targetRow = clamp(0, HEIGHT - 1, playerRow - 1);
                    case 1: // DOWN
                        targetRow = clamp(0, HEIGHT - 1, playerRow + 1);
                    case 2: // LEFT
                        targetColumn = clamp(0, WIDTH - 1, playerColumn - 1);
                    case 3: // RIGHT
                        targetColumn = clamp(0, WIDTH - 1, playerColumn + 1);
                }
                targetPosition = ((HEIGHT * targetRow) + targetColumn);
                char targetCell = Screen.get(targetPosition);
                System.out.println(targetPosition + " ROW : " + targetRow + " COL : " + targetColumn);
                if (targetCell == Objects.AIR.value || targetCell == Objects.PLAYER.value) {
                    return true;
                }
                return false;
            }
        }
    }

    enum Objects {
        LAND('#'),
        AIR(' '), // could also use —
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