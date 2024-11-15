package lib.math.Shapes.LWJGL;

import lib.math.Shapes.Shapes.*;

public class ShapesLWJGL extends lib.math.Vector.Vector {
    public static class TriangleData {
        public Float[] vert;
        public Float side;

        public TriangleData(Float[] vert, Float side){
            this.vert = vert;
            this.side = side;
        }
    }
}