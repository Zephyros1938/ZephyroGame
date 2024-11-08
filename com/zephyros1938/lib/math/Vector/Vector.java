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

        public Vector2 subtract(Vector2 other) {
            return new Vector2(this.X - other.X, this.Y - other.Y);
        }

        public Vector2 multiply(Vector2 other) {
            return new Vector2(this.X * other.X, this.Y * other.Y);
        }

        public Vector2 dot(double scalar) {
            return new Vector2(this.X * scalar, this.Y * scalar);
        }

        public double magnitude() {
            return Math.sqrt(this.X * this.X + this.Y * this.Y);
        }

        public Vector2 normalize() {
            double mag = magnitude();
            if (mag == 0) {
                return new Vector2(0, 0);
            }
            return new Vector2(this.X / mag, this.Y / mag);
        }

        @Override
        public String toString() {
            return "(" + this.X + "," + this.Y + ")";
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

        public Vector3 subtract(Vector3 other) {
            return new Vector3(this.X - other.X, this.Y - other.Y, this.Z - other.Z);
        }

        public Vector3 multiply(Vector3 other) {
            return new Vector3(this.X * other.X, this.Y * other.Y, this.Z * other.Z);
        }

        public Vector3 dot(double scalar) {
            return new Vector3(this.X * scalar, this.Y * scalar, this.Z * scalar);
        }

        public double magnitude() {
            return Math.sqrt(this.X * this.X + this.Y * this.Y + this.Z * this.Z);
        }

        public Vector3 normalize() {
            double mag = magnitude();
            if (mag == 0) {
                return new Vector3(0, 0, 0);
            }
            return new Vector3(this.X / mag, this.Y / mag, this.Z / mag);
        }

        @Override
        public String toString() {
            return "(" + this.X + "," + this.Y + "," + this.Z + ")";
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

        public Vector4 subtract(Vector4 other) {
            return new Vector4(this.X - other.X, this.Y - other.Y, this.Z - other.Z, this.W - other.W);
        }

        public Vector4 multiply(Vector4 other) {
            return new Vector4(this.X * other.X, this.Y * other.Y, this.Z * other.Z, this.W * other.W);
        }

        public Vector4 dot(double scalar) {
            return new Vector4(this.X * scalar, this.Y * scalar, this.Z * scalar, this.W * scalar);
        }

        public double magnitude() {
            return Math.sqrt(this.X * this.X + this.Y * this.Y + this.Z * this.Z + this.W * this.W);
        }

        public Vector4 normalize() {
            double mag = magnitude();
            if (mag == 0) {
                return new Vector4(0, 0, 0, 0);
            }
            return new Vector4(this.X / mag, this.Y / mag, this.Z / mag, this.W / mag);
        }
    }
}
