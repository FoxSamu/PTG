/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.discrete;

import net.shadew.ptg.noise.BaseNoise3D;
import net.shadew.ptg.noise.util.Hash;

/**
 * 3D Discrete (cell) noise generator.
 */
public class Discrete3D extends BaseNoise3D {

    /**
     * Constructs a Discrete noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Discrete3D(int seed) {
        super(seed);
    }

    /**
     * Constructs a Discrete noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public Discrete3D(int seed, double scale) {
        super(seed, scale);
    }

    /**
     * Constructs a Discrete noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     * @param scaleZ The coordinate scaling along Z axis
     */
    public Discrete3D(int seed, double scaleX, double scaleY, double scaleZ) {
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

        return Discrete.compute(x, y, z, this::hash);
    }
}
