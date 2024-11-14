package lib.math.Shapes.LWJGL;

import lib.math.Shapes.Shapes.*;

public class ShapesLWJGL extends lib.math.Vector.Vector {
    public static class TriangleData {
        public Triangle vert;
        public Float[] col = new Float[12];

        public TriangleData(Triangle vert, Pentatope col){
            this.vert = vert;
            this.col = col.toFloatArray();
        }
    }
}