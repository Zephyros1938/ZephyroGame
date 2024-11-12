package com.zephyros1938.lib.math.PerlinNoise;

/*
 * RESOURCES:
 * - https://jaysmito101.hashnode.dev/perlins-noise-algorithm
 */

import java.util.Random;

import com.zephyros1938.lib.math.Util.Util;

public class PerlinNoise {
    private static final int GRADIENT_SIZE = 256;
    private final int[] perm = new int[512];

    private long S = 1; // seed

    private static final float[][] grad2 = {
            { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 },
            { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }
    };

    public PerlinNoise(long seed) {
        S = seed;

        int[] p = new int[GRADIENT_SIZE];
        for (int i = 0; i < GRADIENT_SIZE; i++)
            p[i] = i;

        Random random = new Random(S);
        for (int i = GRADIENT_SIZE - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = p[i];
            p[i] = p[j];
            p[j] = temp;
        }

        for (int i = 0; i < 512; i++) {
            perm[i] = p[i & 255];
        }
    }

    public void changeSeed(long changeTo) {
        S = changeTo;
    }

    public float noise(float x, float y) {
        int X = (int) Math.floor(x) & 255; // GET UNIT SQUARE CONTAINING POINT
        int Y = (int) Math.floor(y) & 255;
        //System.out.println("X&Y : " + X + " " + Y);

        float xCoord = (float) Math.floor(x); // RELATIVE XY COORDS WITHIN THE SQUARE
        float yCoord = (float) Math.floor(y);

        x -= xCoord; // RELATIVE XY COORDS WITHIN THE SQUARE
        y -= yCoord;

        float u = Util.fade(x);
        float v = Util.fade(y);

        // HASH 4 CORNERS
        int aa = perm[X + perm[Y]] & 3;
        int ab = perm[X + perm[Y + 1]] & 3;
        int ba = perm[X + 1 + perm[Y]] & 3;
        int bb = perm[X + 1 + perm[Y + 1]] & 3;

        // ADD BLEND RESULTS FROM 4 CORNERS OF SQUARE
        float gradAA = Util.dot(grad2[aa], x, y);
        float gradBA = Util.dot(grad2[ba], x - 1, y);
        float gradAB = Util.dot(grad2[ab], x, y - 1);
        float gradBB = Util.dot(grad2[bb], x - 1, y - 1);

        float lerpX1 = Util.lerp(u, gradAA, gradBA);
        float lerpX2 = Util.lerp(u, gradAB, gradBB);

        float lerpedValue = Util.lerp(v, lerpX1, lerpX2);

        /* // DEBUG
         * System.out.println(
         * "X: " + X + ", Y: " + Y +
         * "\nxCoord: " + xCoord + ", yCoord: " + yCoord +
         * "\nu: " + u + ", v: " + v +
         * "\naa: " + aa + ", ab: " + ab + ", ba: " + ba + ", bb: " + bb +
         * "\ngradAA: " + gradAA + ", gradBA: " + gradBA + ", gradAB: " + gradAB +
         * ", gradBB: " + gradBB +
         * "\nlerpX1: " + lerpX1 + ", lerpX2: " + lerpX2 +
         * "\nResult: " + lerpedValue + "\n");
         */
        //System.out.println(lerpedValue);

        return lerpedValue;
    }
}