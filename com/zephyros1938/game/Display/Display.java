package com.zephyros1938.game.Display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.CharBuffer;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;

import com.zephyros1938.lib.TimeStamp.TimeStamp;
import com.zephyros1938.lib.math.PerlinNoise.PerlinNoise;
import com.zephyros1938.lib.math.Util.Util;

import com.zephyros1938.game.Player.Player;

public class Display implements KeyListener {
    static private Integer WIDTH = 5;
    static private Integer HEIGHT = 5;
    static private Integer SCREEN_WIDTH;
    static private Integer SCREEN_HEIGHT;
    static private Integer SCREEN_SIZE = WIDTH * HEIGHT;
    static private Float FONT_SIZE = 0.8f;

    static private Integer playerRow;
    static private Integer playerColumn;
    static private Integer spawnPosition;
    static private Integer playerPosition;

    static private PerlinNoise terrainPerlinNoise = new PerlinNoise(123141);
    static private float terrainAmplitude = 1.0f;
    static private float terrainFrequency = 0.2f;

    static private CharBuffer Screen;
    static private StringBuilder ScreenText = new StringBuilder();
    static private String FontStyle;

    static private JEditorPane jTextAreaBoard = new JEditorPane("text/html", "");
    static private JFrame window = new JFrame("Game Display");

    static private TimeStamp timeStamps = new TimeStamp();

    static private Player player = new Player(0, "Player", HEIGHT, WIDTH);

    public static int clamp(int min, int max, int value) {
        return (value < min ? min : value) > max ? max : (value < min ? min : value);
    }

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
        int x, y, screenIndex;
        float xi, yi, n, c;
        /*
         * for (int i = 0; i < SCREEN_SIZE; i++) {
         * if (i == playerPosition) {
         * Screen.put(i, Objects.PLAYER.value);
         * } else {
         * Screen.put(i, Objects.AIR.value);
         * }
         * }
         */

        for (int index = 0; index < SCREEN_SIZE; index++) {
            x = index / WIDTH;
            y = index % WIDTH;

            xi = x * terrainFrequency;
            yi = y * terrainFrequency;

            n = terrainPerlinNoise.noise(xi, yi) * terrainAmplitude;

            n += 1.0;
            n /= 2.0;

            c = Math.round(255 * n);

            screenIndex = (y * HEIGHT) + x;
            // System.out.println(c);

            if (x == playerColumn && y == playerRow) {
                Screen.put(screenIndex, Objects.PLAYER.value);
            } else {
                Screen.put(screenIndex, getTerrainBlock(c));
            }
        }
        /*
         * ! OLD CODE FOR REFERENCE !
         * for (int y = 0; y < HEIGHT; y++) {
         * for (int x = 0; x < WIDTH; x++) { // ! USE THIS FOR BETTER NOISE RESULTS:
         * // https://rtouti.github.io/graphics/perlin-noise-algorithm
         * float xi = x * terrainFrequency, yi = y * terrainFrequency;
         * 
         * float n = terrainPerlinNoise.noise(xi, yi) * terrainAmplitude;
         * 
         * n += 1.0;
         * n /= 2.0;
         * 
         * float c = Math.round(255 * n);
         * 
         * int screenIndex = (y * HEIGHT) + x;
         * // System.out.println(c);
         * 
         * if (x == playerColumn && y == playerRow) {
         * Screen.put(screenIndex, Objects.PLAYER.value);
         * } else {
         * Screen.put(screenIndex, getTerrainBlock(c));
         * }
         * }
         * }
         */
    }

    private static char getTerrainBlock(float height) {
        if (height >= 140.0) {
            return Objects.LAND.value;
        } else {
            return Objects.AIR.value;
        }
    }

    private static void InitializeFont() {
        StringBuilder f = new StringBuilder();

        f.append("<html><head><style>pre {font-family: monospace;font-size: \"");
        f.append(FONT_SIZE);
        f.append("em\";letter-spacing: \"");
        f.append(100);
        f.append("em\";}</style></head><body><pre>");

        FontStyle = f.toString();
    }

    public static void UpdateScreenVisible() throws NullPointerException {
        ScreenText.setLength(0);
        ScreenText.append("<pre>");
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
        //ScreenText.append("</pre><br>PC: " + playerColumn + " PR: " + playerRow + " PP: " + playerPosition
        //        + "</body></html>");
        // System.out.println(ScreenText);
        ScreenText.append("</pre>");
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
        UpdateScreenBuffer();

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

    enum Objects {
        LAND('#'),
        AIR(' '),
        PLAYER('Y'),
        ORB('O'),
        ENEMY('Ё'),
        BIGENEMY('Ж');
    
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
        System.out.println("Key pressed: " + e.getKeyCode() + e.getKeyChar());
        try {
            int pa = p.c.controlKeySet.get(e.getKeyCode());
            System.out.println(pa);
            Player.Controller.ControlSwitch(pa);
        } catch (Exception e1) {
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