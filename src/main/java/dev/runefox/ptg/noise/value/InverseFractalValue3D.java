/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.value;

import dev.runefox.ptg.noise.BaseNoise3D;

/**
 * Inverse-Fractal-Value noise generator for 3D space. This generator uses a specified amount of {@link
 * Value3D}-instances as octaves.
 */
public class InverseFractalValue3D extends BaseNoise3D {

    private final Value3D[] noiseOctaves;

    /**
     * Constructs a Inverse-Fractal-Value noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public InverseFractalValue3D(int seed, int octaves) {
        super(seed);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Value3D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Value3D(seed);
        }
    }

    /**
     * Constructs a Inverse-Fractal-Value noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalValue3D(int seed, double scale, int octaves) {
        super(seed, scale);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Value3D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Value3D(seed);
        }
    }

    /**
     * Constructs a Inverse-Fractal-Value noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param scaleZ  The coordinate scaling along Z axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalValue3D(int seed, double scaleX, double scaleY, double scaleZ, int octaves) {
        super(seed, scaleX, scaleY, scaleZ);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Value3D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Value3D(seed);
        }
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        double d = 1;
        double n = 0;

        for (Value3D noise : noiseOctaves) {
            n += noise.generate(x / d, y / d, z / d) * d;
            d *= 2;
        }
        return n;
    }

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
        for (Value3D value : noiseOctaves) {
            value.setSeed(seed++);
        }
    }
}
