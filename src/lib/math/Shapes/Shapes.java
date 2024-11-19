package lib.math.Shapes;

public class Shapes extends lib.math.Vector {
    public class Triangle {
        public Vector2 BL, TP, BR;

        public Triangle(Vector2 POINT_1, Vector2 POINT_2, Vector2 POINT_3) {
            this.BL = POINT_1;
            this.TP = POINT_2;
            this.BR = POINT_3;
        }

        public Triangle(Float[] POINTS) {
            if(POINTS.length!=6){
                throw new IllegalArgumentException("POINTS must be exactly 6 length.");
            }
            this.BL = new Vector2(POINTS[0], POINTS[1]);
            this.TP = new Vector2(POINTS[2], POINTS[3]);
            this.BR = new Vector2(POINTS[4], POINTS[5]);
        }

        public Triangle(Float BL_X, Float BL_Y, Float TP_X, Float TP_Y, Float BR_X, Float BR_Y) {
            this.BL = new Vector2(BL_X, BL_Y);
            this.TP = new Vector2(TP_X, TP_Y);
            this.BR = new Vector2(BR_X, BR_Y);
        }

        public Float[] toFloatArray() {
            return new Float[] { BL.x, BL.y, TP.x, TP.y, BR.x, BR.y };
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
            if(POINTS.length!=9){
                throw new IllegalArgumentException("POINTS must be exactly 9 length.");
            }
            this.BL = new Vector3(POINTS[0], POINTS[1], POINTS[2]);
            this.TP = new Vector3(POINTS[3], POINTS[4], POINTS[5]);
            this.BR = new Vector3(POINTS[6], POINTS[7], POINTS[8]);
        }

        public Pyramid(Float BL_X, Float BL_Y, Float BL_Z, Float TP_X, Float TP_Y, Float TP_Z, Float BR_X, Float BR_Y,
                Float BR_Z) {
            this.BL = new Vector3(BL_X, BL_Y, BL_Z);
            this.TP = new Vector3(TP_X, TP_Y, TP_Z);
            this.BR = new Vector3(BR_X, BR_Y, BR_Z);
        }

        public Float[] toFloatArray() {
            return new Float[] { BL.x, BL.y, BL.z, TP.x, TP.y, TP.z, BR.x, BR.y, BR.z };
        }

        @Override
        public String toString() {
            return "((" + this.BL.toString() + "), (" + this.TP.toString() + "), (" + this.BR.toString() + "))";
        }
    }

    public class Pentatope {
        public Vector4 BL, TP, BR;

        public Pentatope(Vector4 POINT_1, Vector4 POINT_2, Vector4 POINT_3) {
            this.BL = POINT_1;
            this.TP = POINT_2;
            this.BR = POINT_3;
        }

        public Pentatope(Float[] POINTS) {
            this.BL = new Vector4(POINTS[0], POINTS[1], POINTS[2], POINTS[3]);
            this.TP = new Vector4(POINTS[4], POINTS[5], POINTS[6], POINTS[7]);
            this.BR = new Vector4(POINTS[8], POINTS[9], POINTS[10], POINTS[11]);
        }

        public Pentatope(
                Float BL_X, Float BL_Y, Float BL_Z, Float BL_W,
                Float TP_X, Float TP_Y, Float TP_Z, Float TP_W,
                Float BR_X, Float BR_Y, Float BR_Z, Float BR_W) {
            this.BL = new Vector4(BL_X, BL_Y, BL_Z, BL_W);
            this.TP = new Vector4(TP_X, TP_Y, TP_Z, TP_W);
            this.BR = new Vector4(BR_X, BR_Y, BR_Z, BR_W);
        }

        public Float[] toFloatArray() {
            return new Float[] { BL.x, BL.y, BL.z, BL.w, TP.x, TP.y, TP.z, TP.w, BR.x, BR.y, BR.z, BR.w };
        }

        @Override
        public String toString() {
            return "((" + this.BL.toString() + "), (" + this.TP.toString() + "), (" + this.BR.toString() + "))";
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
