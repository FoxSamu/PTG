/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.value;

import dev.runefox.ptg.noise.RepetitiveNoise3D;
import dev.runefox.ptg.noise.util.Hash;

/**
 * Repeating 3D Value noise generator.
 */
public class RepetitiveValue3D extends RepetitiveNoise3D {

    /**
     * Constructs a Repeating Value noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public RepetitiveValue3D(int seed, int repeat) {
        super(seed, repeat);
    }

    /**
     * Constructs a Repeating Value noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public RepetitiveValue3D(int seed, double scale, int repeat) {
        super(seed, scale, repeat);
    }

    /**
     * Constructs a Repeating Value noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     * @param scaleZ The coordinate scaling along Z axis
     */
    public RepetitiveValue3D(int seed, double scaleX, double scaleY, double scaleZ, int repeatX, int repeatY, int repeatZ) {
        super(seed, scaleX, scaleY, scaleZ, repeatX, repeatY, repeatZ);
    }

    private long repeat(long val, int repeat) {
        return val % repeat + (val < 0 ? repeat : 0);
    }

    private int hash(long x, long y, long z) {
        return Hash.hash3I(seed, repeat(x, repeatX), repeat(y, repeatY), repeat(z, repeatZ));
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        return Value.compute(x, y, z, this::hash);
    }
}
