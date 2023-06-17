/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.simplex;

import dev.runefox.ptg.noise.BaseNoise3D;

/**
 * Inverse-Fractal-Simplex noise generator for 3D space. This generator uses a specified amount of {@link
 * Simplex3D}-instances as octaves.
 */
public class InverseFractalSimplex3D extends BaseNoise3D {

    private final Simplex3D[] noiseOctaves;

    /**
     * Constructs a Inverse-Fractal-Simplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public InverseFractalSimplex3D(int seed, int octaves) {
        super(seed);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Simplex3D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Simplex3D(seed);
        }
    }

    /**
     * Constructs a Inverse-Fractal-Simplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalSimplex3D(int seed, double scale, int octaves) {
        super(seed, scale);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Simplex3D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Simplex3D(seed);
        }
    }

    /**
     * Constructs a Inverse-Fractal-Simplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param scaleZ  The coordinate scaling along Z axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalSimplex3D(int seed, double scaleX, double scaleY, double scaleZ, int octaves) {
        super(seed, scaleX, scaleY, scaleZ);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Simplex3D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Simplex3D(seed);
        }
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        double d = 1;
        double n = 0;

        for (Simplex3D noise : noiseOctaves) {
            n += noise.generate(x / d, y / d, z / d) * d;
            d *= 2;
        }
        return n;
    }

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
        for (Simplex3D noise : noiseOctaves) {
            noise.setSeed(seed);
        }
    }
}
