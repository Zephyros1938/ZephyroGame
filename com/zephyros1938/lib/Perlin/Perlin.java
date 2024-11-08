package com.zephyros1938.lib.Perlin;

import com.zephyros1938.lib.math.Vector.Vector.Vector2;

/*
 * RESOURCES:
 * - https://jaysmito101.hashnode.dev/perlins-noise-algorithm
 */

public class Perlin { // TODO: add perlin functions

    private static int B = 256;
    private static Vector2 gradients;

    public Vector2 noise(Vector2 in) {
        Vector2 P0 = new Vector2(Math.floor(in.X), Math.floor(in.Y));
        Vector2 P1 = new Vector2(Math.floor(in.X), Math.floor(in.Y) + 1.0);
        Vector2 P2 = new Vector2(Math.floor(in.X) + 1.0, Math.floor(in.Y));
        Vector2 P3 = new Vector2(Math.floor(in.X) + 1.0, Math.floor(in.Y) + 1.0);

        Vector2 UV_P0 = P0.randomUnitVector();
        Vector2 UV_P1 = P1.randomUnitVector();
        Vector2 UV_P2 = P2.randomUnitVector();
        Vector2 UV_P3 = P3.randomUnitVector();

        return in;
    }
}
