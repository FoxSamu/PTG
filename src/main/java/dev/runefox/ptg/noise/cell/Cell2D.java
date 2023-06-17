/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.cell;

import dev.runefox.ptg.noise.BaseNoise2D;
import dev.runefox.ptg.noise.util.Hash;

/**
 * 2D Cell (voronoi, cell value) noise generator.
 */
public class Cell2D extends BaseNoise2D {

    /**
     * Constructs a Cell noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Cell2D(int seed) {
        super(seed);
    }

    /**
     * Constructs a Cell noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public Cell2D(int seed, double scale) {
        super(seed, scale);
    }

    /**
     * Constructs a Cell noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public Cell2D(int seed, double scaleX, double scaleY) {
        super(seed, scaleX, scaleY);
    }

    private int hashx(long x, long y) {
        return Hash.hash2I(seed * 12551 + 2315, x, y);
    }

    private int hashy(long x, long y) {
        return Hash.hash2I(seed * 5281 + 1121, x, y);
    }

    private int hashv(long x, long y) {
        return Hash.hash2I(seed * 12475 + 3187, x, y);
    }

    @Override
    public double generate(double x, double y) {
        x /= scaleX;
        y /= scaleY;

        return Cell.compute(x, y, this::hashx, this::hashy, this::hashv);
    }
}
