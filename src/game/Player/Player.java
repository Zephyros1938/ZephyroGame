package src.game.Player;

import src.lib.math.Util.Util;

public class Player {

    private final Integer PLAYER_ID;
    private final String PLAYER_NAME;
    private static Integer MAX_HEIGHT;
    private static Integer MAX_WIDTH;
    private static Integer P_X;
    private static Integer P_Y;
    private static Integer P_POS;
    private Controller c = new Controller();

    public Player(int PID, String NAME, int MAX_HEIGHT, int MAX_WIDTH) {
        this.PLAYER_ID = PID;
        this.PLAYER_NAME = NAME;
        this.MAX_HEIGHT = MAX_HEIGHT;
        this.MAX_WIDTH = MAX_WIDTH;
        this.P_X = (int) (MAX_WIDTH / 2);
        this.P_Y = (int) (MAX_HEIGHT / 2);
    }

    static class Controller {

        public Controller() {
        }

        public static void ControlSwitch(int key) {
            switch (key) {
                case 0: // UP
                    if (Detection.CanMove(0)) {
                        P_Y = Util.clampInt(0, MAX_HEIGHT - 1, P_Y - 1);
                        Move();
                        break;
                    }
                    System.out.println("Cannot move");
                    break;
                case 1: // DOWN
                    if (Detection.CanMove(1)) {
                        P_Y = Util.clampInt(0, MAX_HEIGHT - 1, P_Y + 1);
                        Move();
                        break;
                    }
                    System.out.println("Cannot move");
                    break;
                case 2: // LEFT
                    if (Detection.CanMove(2)) {
                        P_X = Util.clampInt(0, MAX_WIDTH - 1, P_X - 1);
                        Move();
                        break;
                    }
                    System.out.println("Cannot move");
                    break;
                case 3: // RIGHT
                    if (Detection.CanMove(3)) {
                        P_X = Util.clampInt(0, MAX_WIDTH - 1, P_X + 1);
                        Move();
                        break;
                    }
                    System.out.println("Cannot move");
                    break;
                default:
                    break;
            }
        }

        public static void Move() {
            P_POS = ((MAX_HEIGHT * P_X) + P_Y);
        }

        private static Byte[][] controlKeySet = {
                { 87, 0 },
                { 38, 0 },
                { 83, 1 },
                { 40, 1 },
                { 65, 2 },
                { 37, 2 },
                { 68, 3 },
                { 39, 3 } };
    }

    public static class Detection { // something is going wrong
                                    // here
        private static Boolean CanMove(int dir) {
            int targetPosition = P_POS, targetRow = P_Y, targetColumn = P_X;
            System.out.println(dir);
            if (dir == 0) { // UP
                targetRow = Util.clampInt(0, MAX_HEIGHT - 1, P_Y - 1);
                targetPosition = ((MAX_HEIGHT * (targetRow)) + P_X);
            } else if (dir == 1) { // DOWN
                targetRow = Util.clampInt(0, MAX_HEIGHT - 1, P_Y + 1);
                targetPosition = ((MAX_HEIGHT * (targetRow)) + P_X);
            } else if (dir == 2) { // LEFT
                targetColumn = Util.clampInt(0, MAX_WIDTH - 1, P_X - 1);
                targetPosition = ((MAX_HEIGHT * (P_Y)) + targetColumn);
            } else if (dir == 3) { // RIGHT
                targetColumn = Util.clampInt(0, MAX_WIDTH - 1, P_X + 1);
                targetPosition = ((MAX_HEIGHT * (P_Y)) + targetColumn);
            }

            return false;
        }
    }
}
