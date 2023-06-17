/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.voronoi;

import dev.runefox.ptg.noise.BaseNoise3D;
import dev.runefox.ptg.noise.util.Hash;

/**
 * 3D Voronoi (distance to edge) noise generator.
 */
public class Voronoi3D extends BaseNoise3D {

    /**
     * Constructs a Voronoi noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Voronoi3D(int seed) {
        super(seed);
    }

    /**
     * Constructs a Voronoi noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public Voronoi3D(int seed, double scale) {
        super(seed, scale);
    }

    /**
     * Constructs a Voronoi noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     * @param scaleZ The coordinate scaling along Z axis
     */
    public Voronoi3D(int seed, double scaleX, double scaleY, double scaleZ) {
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

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        return Voronoi.compute(x, y, z, this::hashx, this::hashy, this::hashz);
    }
}
