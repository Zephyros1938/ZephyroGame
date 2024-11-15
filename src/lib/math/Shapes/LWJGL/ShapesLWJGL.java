package lib.math.Shapes.LWJGL;

public class ShapesLWJGL {
    public static class TriangleData {
        public Float[] vert;
        public Float[] col;
        public Float[] side;

        public TriangleData(Float[] vert, Float[] col, Float[] side){
            this.vert = vert;
            this.col = col;
            this.side = side;
        }
    }
}