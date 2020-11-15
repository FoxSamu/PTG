/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.cell;

import net.shadew.ptg.noise.BaseNoise3D;
import net.shadew.ptg.noise.util.Hash;

/**
 * 3D Cell (voronoi, cell value) noise generator.
 */
public class Cell3D extends BaseNoise3D {

    /**
     * Constructs a Cell noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Cell3D(int seed) {
        super(seed);
    }

    /**
     * Constructs a Cell noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public Cell3D(int seed, double scale) {
        super(seed, scale);
    }

    /**
     * Constructs a Cell noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     * @param scaleZ The coordinate scaling along Z axis
     */
    public Cell3D(int seed, double scaleX, double scaleY, double scaleZ) {
        super(seed, scaleX, scaleY, scaleZ);
    }

    private int hashx(long x, long y, long z) {
        return Hash.hash3I(seed * 12551 + 2315, x, y, z);
    }

    private int hashy(long x, long y, long z) {
        return Hash.hash3I(seed * 5281 + 1121, x, y, z);
    }

    private int hashz(long x, long y, long z) {
        return Hash.hash3I(seed * 8911 + 731, x, y, z);
    }

    private int hashv(long x, long y, long z) {
        return Hash.hash3I(seed * 12475 + 3187, x, y, z);
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        return Cell.compute(x, y, z, this::hashx, this::hashy, this::hashz, this::hashv);
    }
}
