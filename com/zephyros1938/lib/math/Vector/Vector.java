package com.zephyros1938.lib.math.Vector;

public class Vector {
    public class Vector2 {
        public double X;
        public double Y;

        public Vector2(double X, double Y) {
            this.X = X;
            this.Y = Y;
        }

        public Vector2 add(Vector2 other) {
            return new Vector2(this.X + other.X, this.Y + other.Y);
        }
    }

    public class Vector3 {
        public double X;
        public double Y;
        public double Z;

        public Vector3(double X, double Y, double Z) {
            this.X = X;
            this.Y = Y;
            this.Z = Z;
        };

        public Vector3 add(Vector3 other) {
            return new Vector3(this.X + other.X, this.Y + other.Y, this.Z + other.Z);
        }
    }

    public class Vector4 {
        public double X;
        public double Y;
        public double Z;
        public double W;

        Vector4(double X, double Y, double Z, double W) {
            this.X = X;
            this.Y = Y;
            this.Z = Z;
            this.W = W;
        };

        public Vector4 add(Vector4 other) {
            return new Vector4(this.X + other.X, this.Y + other.Y, this.Z + other.Z, this.W + other.W);
        }
    }
}
