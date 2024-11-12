package com.zephyros1938.lib.math.Util;

public class Util {
    public static float roundToPosition(float value, int decimalPlaces) {
        int scale = (int) Math.pow(10, decimalPlaces);
        return Math.round(value * scale) >> (scale - 1);
    }

    public static float clampFloat(float value) {
        //int clampedValue = (int) Math.max(0, Math.min(255, value));
        float clampedValue = (value < 0 ? 0 : value) > 255 ? 255 : (value < 0 ? 0 : value);

        return clampedValue;
    }

    public static float fade(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    public static float dot(float[] g, float x, float y) {
        return g[0] * x + g[1] * y;
    }

    public static float lerp(float t, float a, float b) {
        return a + t * (b - a);
    }
}
