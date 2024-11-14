package src.lib.math.Shapes;

public class Shapes extends src.lib.math.Vector.Vector {
    public class Triangle {
        public Vector2 BL, TP, BR;

        public Triangle(Vector2 POINT_1, Vector2 POINT_2, Vector2 POINT_3) {
            this.BL = POINT_1;
            this.TP = POINT_2;
            this.BR = POINT_3;
        }

        public Triangle(Float[] POINTS) {
            this.BL = new Vector2(POINTS[0], POINTS[1]);
            this.TP = new Vector2(POINTS[2], POINTS[3]);
            this.BR = new Vector2(POINTS[4], POINTS[5]);
        }

        public Triangle(Float BLX, Float BLY, Float TPX, Float TPY, Float BRX, Float BRY) {
            this.BL = new Vector2(BLX, BLY);
            this.TP = new Vector2(TPX, TPY);
            this.BR = new Vector2(BRX, BRY);
        }

        @Override
        public String toString() {
            return "(" + this.BL.toString() + ", " + this.TP.toString() + ", " + this.BR.toString() + ")";
        }
    }

    public class Pyramid {
        public Vector3 BL, TP, BR;

        public Pyramid(Vector3 POINT_1, Vector3 POINT_2, Vector3 POINT_3) {
            this.BL = POINT_1;
            this.TP = POINT_2;
            this.BR = POINT_3;
        }

        public Pyramid(Float[] POINTS) {
            this.BL = new Vector3(POINTS[0], POINTS[1], POINTS[2]);
            this.TP = new Vector3(POINTS[3], POINTS[4], POINTS[5]);
            this.BR = new Vector3(POINTS[6], POINTS[7], POINTS[8]);
        }

        public Pyramid(Float BLX, Float BLY, Float BLZ, Float TPX, Float TPY, Float TPZ, Float BRX, Float BRY, Float BRZ) {
            this.BL = new Vector3(BLX, BLY, BLZ);
            this.TP = new Vector3(TPX, TPY, TPZ);
            this.BR = new Vector3(BRX, BRY, BRZ);
        }

        @Override
        public String toString() {
            return "(" + this.BL.toString() + ", " + this.TP.toString() + ", " + this.BR.toString() + ")";
        }
    }

    public class Quad {

        public Vector2 BL, BR, TR, TL;

        public Quad(Vector2 POINT_1, Vector2 POINT_2, Vector2 POINT_3, Vector2 POINT_4) {
            this.BL = POINT_1;
            this.BR = POINT_2;
            this.TR = POINT_3;
            this.TL = POINT_4;
        }
    }
}
