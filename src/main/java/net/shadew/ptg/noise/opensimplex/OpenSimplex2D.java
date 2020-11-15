/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.opensimplex;

import net.shadew.ptg.noise.BaseNoise2D;
import net.shadew.ptg.noise.util.Hash;
import net.shadew.ptg.noise.util.NoiseMath;

/**
 * 2D OpenSimplex noise generator based on an
 * <a href=https://gist.github.com/KdotJPG/b1270127455a94ac5d19>implementation by Kurt Spencer</a>
 */
public class OpenSimplex2D extends BaseNoise2D {

    /**
     * Constructs an OpenSimplex noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public OpenSimplex2D(int seed) {
        super(seed);
    }

    /**
     * Constructs an OpenSimplex noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public OpenSimplex2D(int seed, double scale) {
        super(seed, scale);
    }

    /**
     * Constructs an OpenSimplex noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public OpenSimplex2D(int seed, double scaleX, double scaleY) {
        super(seed, scaleX, scaleY);
    }

    private int hash(long x, long y) {
        return Hash.hash2I(seed, x, y);
    }

    @Override
    public double generate(double x, double y) {
        x /= scaleX;
        y /= scaleY;

        return OpenSimplex.compute(x, y, this::hash);
    }
}
