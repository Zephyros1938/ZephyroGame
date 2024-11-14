package src.lib.math.Shapes;

public class Shapes extends src.lib.math.Vector.Vector {
    public class Triangle {
        public Vector2 P1,P2,P3;

        public Triangle(Vector2 POINT_1, Vector2 POINT_2, Vector2 POINT_3){
            this.P1 = POINT_1;
            this.P2 = POINT_2;
            this.P3 = POINT_3;
        }
    }
    public class Quad {

        public Vector2 P1,P2,P3,P4;

        public Quad(Vector2 POINT_1, Vector2 POINT_2, Vector2 POINT_3, Vector2 POINT_4){
            this.P1 = POINT_1;
            this.P2 = POINT_2;
            this.P3 = POINT_3;
            this.P4 = POINT_4;
        }
    }
}
