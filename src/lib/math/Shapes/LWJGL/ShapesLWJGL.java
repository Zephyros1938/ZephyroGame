package src.lib.math.Shapes.LWJGL;

import src.lib.math.Shapes.Shapes.*;

public class ShapesLWJGL extends src.lib.math.Vector.Vector {
    public static class TriangleData {
        public Triangle vert;
        public Pyramid col;

        public TriangleData(Triangle vert, Pyramid col){
            this.vert = vert;
            this.col = col;
        }
    }
}