/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.voronoi;

import dev.runefox.ptg.noise.RepetitiveNoise2D;
import dev.runefox.ptg.noise.util.Hash;

/**
 * Repeating 2D Voronoi noise generator.
 */
public class RepetitiveVoronoi2D extends RepetitiveNoise2D {

    /**
     * Constructs a Repeating Voronoi noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public RepetitiveVoronoi2D(int seed, int repeat) {
        super(seed, repeat);
    }

    /**
     * Constructs a Repeating Voronoi noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public RepetitiveVoronoi2D(int seed, double scale, int repeat) {
        super(seed, scale, repeat);
    }

    /**
     * Constructs a Repeating Voronoi noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public RepetitiveVoronoi2D(int seed, double scaleX, double scaleY, int repeatX, int repeatY) {
        super(seed, scaleX, scaleY, repeatX, repeatY);
    }

    private long repeat(long val, int repeat) {
        return val % repeat + (val < 0 ? repeat : 0);
    }

    private int hashx(long x, long y) {
        return Hash.hash2I(seed * 12551 + 2315, repeat(x, repeatX), repeat(y, repeatY));
    }

    private int hashy(long x, long y) {
        return Hash.hash2I(seed * 5281 + 1121, repeat(x, repeatX), repeat(y, repeatY));
    }

    @Override
    public double generate(double x, double y) {
        x /= scaleX;
        y /= scaleY;

        return Voronoi.compute(x, y, this::hashx, this::hashy);
    }
}
