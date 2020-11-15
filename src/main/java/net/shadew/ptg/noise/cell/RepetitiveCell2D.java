/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.cell;

import net.shadew.ptg.noise.RepetitiveNoise2D;
import net.shadew.ptg.noise.util.Hash;

/**
 * Repeating 2D Cell noise generator.
 */
public class RepetitiveCell2D extends RepetitiveNoise2D {

    /**
     * Constructs a Repeating Cell noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public RepetitiveCell2D(int seed, int repeat) {
        super(seed, repeat);
    }

    /**
     * Constructs a Repeating Cell noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public RepetitiveCell2D(int seed, double scale, int repeat) {
        super(seed, scale, repeat);
    }

    /**
     * Constructs a Repeating Cell noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public RepetitiveCell2D(int seed, double scaleX, double scaleY, int repeatX, int repeatY) {
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

    private int hashv(long x, long y) {
        return Hash.hash2I(seed * 12475 + 3187, repeat(x, repeatX), repeat(y, repeatY));
    }

    @Override
    public double generate(double x, double y) {
        x /= scaleX;
        y /= scaleY;

        return Cell.compute(x, y, this::hashx, this::hashy, this::hashv);
    }
}
