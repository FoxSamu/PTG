/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.value;

import dev.runefox.ptg.noise.RepetitiveNoise3D;

/**
 * Repeating fractal-Value noise generator for 3D space. This generator uses a specified amount of {@link
 * RepetitiveValue3D}-instances as octaves.
 */
public class RepetitiveFractalValue3D extends RepetitiveNoise3D {

    private final RepetitiveValue3D[] noiseOctaves;

    /**
     * Constructs a Fractal-Value noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param repeat  The amount of noise grid cells before repeating.
     * @param octaves The amount of octaves.
     */
    public RepetitiveFractalValue3D(int seed, int repeat, int octaves) {
        super(seed, repeat);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new RepetitiveValue3D[octaves];

        int r = repeat;
        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new RepetitiveValue3D(seed, repeat);
            r *= 2;
        }
    }

    /**
     * Constructs a Fractal-Value noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param repeat  The amount of noise grid cells before repeating.
     * @param octaves The amount of octaves.
     */
    public RepetitiveFractalValue3D(int seed, double scale, int repeat, int octaves) {
        super(seed, scale, repeat);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new RepetitiveValue3D[octaves];

        int r = repeat;
        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new RepetitiveValue3D(seed, r);
            r *= 2;
        }
    }

    /**
     * Constructs a Fractal-Value noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param scaleZ  The coordinate scaling along Z axis.
     * @param repeatX The amount of noise grid cells before repeating the X axis.
     * @param repeatY The amount of noise grid cells before repeating the Y axis.
     * @param repeatZ The amount of noise grid cells before repeating the Z axis.
     * @param octaves The amount of octaves.
     */
    public RepetitiveFractalValue3D(int seed, double scaleX, double scaleY, double scaleZ, int repeatX, int repeatY, int repeatZ, int octaves) {
        super(seed, scaleX, scaleY, scaleZ, repeatX, repeatY, repeatZ);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new RepetitiveValue3D[octaves];

        int rx = repeatX;
        int ry = repeatY;
        int rz = repeatZ;
        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new RepetitiveValue3D(seed, 1, 1, 1, rx, ry, rz);
            rx *= 2;
            ry *= 2;
            rz *= 2;
        }
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        double d = 1;
        double n = 0;

        for (RepetitiveValue3D noise : noiseOctaves) {
            n += noise.generate(x * d, y * d, z * d) / d;
            d *= 2;
        }
        return n;
    }

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
        for (RepetitiveValue3D value : noiseOctaves) {
            value.setSeed(seed++);
        }
    }
}
