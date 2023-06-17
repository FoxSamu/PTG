/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.discrete;

import dev.runefox.ptg.noise.BaseNoise2D;

/**
 * Inverse-Fractal-Discrete noise generator for 2D space. This generator uses a specified amount of {@link
 * Discrete2D}-instances as octaves.
 */
public class InverseFractalDiscrete2D extends BaseNoise2D {

    private final Discrete2D[] noiseOctaves;

    /**
     * Constructs a Inverse-Fractal-Discrete noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public InverseFractalDiscrete2D(int seed, int octaves) {
        super(seed);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Discrete2D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Discrete2D(seed);
        }
    }

    /**
     * Constructs a Inverse-Fractal-Discrete noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalDiscrete2D(int seed, double scale, int octaves) {
        super(seed, scale);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Discrete2D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Discrete2D(seed);
        }
    }

    /**
     * Constructs a Inverse-Fractal-Discrete noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalDiscrete2D(int seed, double scaleX, double scaleY, int octaves) {
        super(seed, scaleX, scaleY);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Discrete2D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Discrete2D(seed);
        }
    }

    @Override
    public double generate(double x, double y) {
        x /= scaleX;
        y /= scaleY;

        double d = 1;
        double n = 0;

        for (Discrete2D noise : noiseOctaves) {
            n += noise.generate(x / d, y / d) * d;
            d *= 2;
        }
        return n;
    }

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
        for (Discrete2D value : noiseOctaves) {
            value.setSeed(seed++);
        }
    }
}
