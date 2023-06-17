/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.simplex;

import dev.runefox.ptg.noise.BaseNoise3D;
import dev.runefox.ptg.noise.util.Hash;

/**
 * 3D Simplex noise generator based on an
 * <a href=https://github.com/SRombauts/SimplexNoise/blob/master/references/SimplexNoise.java>implementation by Stefan
 * Gustavson and Peter Eastman</a>
 */
public class Simplex3D extends BaseNoise3D {

    /**
     * Constructs an Simplex noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Simplex3D(int seed) {
        super(seed);
    }

    /**
     * Constructs an Simplex noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public Simplex3D(int seed, double scale) {
        super(seed, scale);
    }

    /**
     * Constructs an Simplex noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     * @param scaleZ The coordinate scaling along Z axis
     */
    public Simplex3D(int seed, double scaleX, double scaleY, double scaleZ) {
        super(seed, scaleX, scaleY, scaleZ);
    }

    private int hash(long x, long y, long z) {
        return Hash.hash3I(seed, x, y, z);
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        return Simplex.noise(x / 2, y / 2, z / 2, this::hash);
    }
}
