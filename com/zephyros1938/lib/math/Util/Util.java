package com.zephyros1938.lib.math.Util;

public class Util {
    public static int clampInt(int val, int min, int max) {
        return (val < min ? min : val) > max ? max : (val < min ? min : val);
    }

    public static float clampFloat(float value) {
        return (value < 0 ? 0 : value) > 255 ? 255 : (value < 0 ? 0 : value);
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
