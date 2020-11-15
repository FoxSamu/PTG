/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.discrete;

import net.shadew.ptg.noise.BaseNoise2D;
import net.shadew.ptg.noise.util.Hash;

/**
 * 2D Discrete (cell) noise generator.
 */
public class Discrete2D extends BaseNoise2D {

    /**
     * Constructs a Discrete noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Discrete2D(int seed) {
        super(seed);
    }

    /**
     * Constructs a Discrete noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public Discrete2D(int seed, double scale) {
        super(seed, scale);
    }

    /**
     * Constructs a Discrete noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public Discrete2D(int seed, double scaleX, double scaleY) {
        super(seed, scaleX, scaleY);
    }

    private int hash(long x, long y) {
        return Hash.hash2I(seed, x, y);
    }

    @Override
    public double generate(double x, double y) {
        x /= scaleX;
        y /= scaleY;

        return Discrete.compute(x, y, this::hash);
    }
}
