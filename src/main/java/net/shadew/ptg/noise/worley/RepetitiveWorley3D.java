/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.worley;

import net.shadew.ptg.noise.RepetitiveNoise3D;
import net.shadew.ptg.noise.util.Hash;

/**
 * Repeating 3D Worley noise generator.
 */
public class RepetitiveWorley3D extends RepetitiveNoise3D {

    /**
     * Constructs a Repeating Worley noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public RepetitiveWorley3D(int seed, int repeat) {
        super(seed, repeat);
    }

    /**
     * Constructs a Repeating Worley noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public RepetitiveWorley3D(int seed, double scale, int repeat) {
        super(seed, scale, repeat);
    }

    /**
     * Constructs a Repeating Worley noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     * @param scaleZ The coordinate scaling along Z axis
     */
    public RepetitiveWorley3D(int seed, double scaleX, double scaleY, double scaleZ, int repeatX, int repeatY, int repeatZ) {
        super(seed, scaleX, scaleY, scaleZ, repeatX, repeatY, repeatZ);
    }

    private long repeat(long val, int repeat) {
        return val % repeat + (val < 0 ? repeat : 0);
    }

    private int hashx(long x, long y, long z) {
        return Hash.hash3I(seed * 12551 + 2315, repeat(x, repeatX), repeat(y, repeatY), repeat(z, repeatZ));
    }

    private int hashy(long x, long y, long z) {
        return Hash.hash3I(seed * 5281 + 1121, repeat(x, repeatX), repeat(y, repeatY), repeat(z, repeatZ));
    }

    private int hashz(long x, long y, long z) {
        return Hash.hash3I(seed * 8911 + 731, repeat(x, repeatX), repeat(y, repeatY), repeat(z, repeatZ));
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        return Worley.compute(x, y, z, this::hashx, this::hashy, this::hashz);
    }
}
