package lib.math.Shapes.LWJGL;

import lib.math.Shapes.Shapes.*;

public class ShapesLWJGL extends lib.math.Vector.Vector {
    public static class TriangleData {
        public Float[] vert;
        public Float[] col;

        public TriangleData(Float[] vert, Float[] col){
            this.vert = vert;
            this.col = col;
        }
    }
}