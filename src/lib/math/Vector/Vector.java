package lib.math.Vector;

public class Vector {

    public static class Vector2 {

        public float x;
        public float y;

        /* CONSTRUCTORS */

        public Vector2(Float x, Float y) {
            this.x = x;
            this.y = y;
        }

        public static Vector2[] fromArray(Float[] values) {
            if (values.length % 2 != 0) {
                throw new IllegalArgumentException("Array length must be even to create Vector2 pairs.");
            }

            Vector2[] vectors = new Vector2[values.length / 2];
            for (int i = 0; i < values.length; i += 2) {
                vectors[i / 2] = new Vector2(values[i], values[i + 1]);
            }
            return vectors;
        }

        /* METHODS */

        public Vector2 add(Vector2 other) {
            return new Vector2(this.x + other.x, this.y + other.y);
        }

        public Vector2 subtract(Vector2 other) {
            return new Vector2(this.x - other.x, this.y - other.y);
        }

        public Vector2 multiply(Vector2 other) {
            return new Vector2(this.x * other.x, this.y * other.y);
        }

        public Vector2 dot(Float scalar) {
            return new Vector2(this.x * scalar, this.y * scalar);
        }

        public Float magnitude() {
            return (float) Math.sqrt(this.x * this.x + this.y * this.y);
        }

        public Vector2 normal() {
            Float mag = magnitude();
            if (mag == 0) {
                return new Vector2(0.0f, 0.0f);
            }
            return new Vector2(this.x / mag, this.y / mag);
        }

        public Vector2 randomUnitVector() {
            Float theta = (float) (2 * Math.PI * Math.random());
            Float uv_x = (float) Math.sin(theta);
            Float uv_y = (float) Math.cos(theta);

            Vector2 uv = new Vector2(uv_x, uv_y);

            return this.add(uv);
        }

        @Override
        public String toString() {
            return "(" + this.x + "," + this.y + ")";
        }
    }

    public static class Vector3 {
        public float x;
        public float y;
        public float z;

        /* CONSTRUCTORS */

        public Vector3(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        };

        public static Vector3[] fromArray(Float[] values) {
            if (values.length % 3 != 0) {
                throw new IllegalArgumentException("Array length must be a multiple of 3 to create Vector3 pairs.");
            }

            Vector3[] vectors = new Vector3[values.length / 3];
            for (int i = 0; i < values.length; i += 3) {
                vectors[i / 3] = new Vector3(values[i], values[i + 1], values[i + 2]);
            }
            return vectors;
        }

        /* METHODS */

        public Vector3 add(Vector3 other) {
            return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
        }

        public Vector3 subtract(Vector3 other) {
            return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
        }

        public Vector3 multiply(Vector3 other) {
            return new Vector3(this.x * other.x, this.y * other.y, this.z * other.z);
        }

        public Vector3 dot(Float scalar) {
            return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
        }

        public Float magnitude() {
            return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        }

        public Vector3 normalize() {
            float mag = magnitude();
            if (mag == 0) {
                return new Vector3(0.0f, 0.0f, 0.0f);
            }
            return new Vector3(this.x / mag, this.y / mag, this.z / mag);
        }

        @Override
        public String toString() {
            return "(" + this.x + "," + this.y + "," + this.z + ")";
        }
    }

    public static class Vector4 {
        public float x;
        public float y;
        public float z;
        public float w;

        /* CONSTRUCTORS */

        public Vector4(float x, float y, float z, float w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        };

        public static Vector4[] fromArray(Float[] values) {
            if (values.length % 4 != 0) {
                throw new IllegalArgumentException("Array length must be a multiple of 4 to create Vector4 pairs.");
            }

            Vector4[] vectors = new Vector4[values.length / 4];
            for (int i = 0; i < values.length; i += 4) {
                vectors[i / 4] = new Vector4(values[i], values[i + 1], values[i + 2], values[i + 3]);
            }
            return vectors;
        }

        /* METHODS */

        public Vector4 add(Vector4 other) {
            return new Vector4(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w);
        }

        public Vector4 subtract(Vector4 other) {
            return new Vector4(this.x - other.x, this.y - other.y, this.z - other.z, this.w - other.w);
        }

        public Vector4 multiply(Vector4 other) {
            return new Vector4(this.x * other.x, this.y * other.y, this.z * other.z, this.w * other.w);
        }

        public Vector4 dot(Float scalar) {
            return new Vector4(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
        }

        public Float magnitude() {
            return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
        }

        public Vector4 normalize() {
            float mag = magnitude();
            if (mag == 0) {
                return new Vector4(0.0f, 0.0f, 0.0f, 0.0f);
            }
            return new Vector4(this.x / mag, this.y / mag, this.z / mag, this.w / mag);
        }

        @Override
        public String toString() {
            return "(" + this.x + "," + this.y + "," + this.z + "," + this.w + ")";
        }
    }
}
