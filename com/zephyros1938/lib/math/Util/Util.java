package com.zephyros1938.lib.math.Util;

public class Util {
    public static double roundToPosition(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }

    public static double clampDouble(double value) {
        int clampedValue = (int) Math.max(0, Math.min(255, value));

        return clampedValue;
    }

    public static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    public static double dot(double[] g, double x, double y) {
        return g[0] * x + g[1] * y;
    }

    public static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }
}
