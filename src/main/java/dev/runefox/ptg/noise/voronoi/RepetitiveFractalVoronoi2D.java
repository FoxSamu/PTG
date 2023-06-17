/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.voronoi;

import dev.runefox.ptg.noise.RepetitiveNoise2D;

/**
 * Repeating fractal-Voronoi noise generator for 2D space. This generator uses a specified amount of {@link
 * RepetitiveVoronoi2D}-instances as octaves.
 */
public class RepetitiveFractalVoronoi2D extends RepetitiveNoise2D {

    private final RepetitiveVoronoi2D[] noiseOctaves;

    /**
     * Constructs a Fractal-Voronoi noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param repeat  The amount of noise grid cells before repeating.
     * @param octaves The amount of octaves.
     */
    public RepetitiveFractalVoronoi2D(int seed, int repeat, int octaves) {
        super(seed, repeat);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new RepetitiveVoronoi2D[octaves];

        int r = repeat;
        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new RepetitiveVoronoi2D(seed + i, r);
            r *= 2;
        }
    }

    /**
     * Constructs a Fractal-Voronoi noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param repeat  The amount of noise grid cells before repeating.
     * @param octaves The amount of octaves.
     */
    public RepetitiveFractalVoronoi2D(int seed, double scale, int repeat, int octaves) {
        super(seed, scale, repeat);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new RepetitiveVoronoi2D[octaves];

        int r = repeat;
        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new RepetitiveVoronoi2D(seed, r);
            r *= 2;
        }
    }

    /**
     * Constructs a Fractal-Voronoi noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param repeatX The amount of noise grid cells before repeating the X axis.
     * @param repeatY The amount of noise grid cells before repeating the Y axis.
     * @param octaves The amount of octaves.
     */
    public RepetitiveFractalVoronoi2D(int seed, double scaleX, double scaleY, int repeatX, int repeatY, int octaves) {
        super(seed, scaleX, scaleY, repeatX, repeatY);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new RepetitiveVoronoi2D[octaves];

        int rx = repeatX;
        int ry = repeatY;
        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new RepetitiveVoronoi2D(seed, 1, 1, rx, ry);
            rx *= 2;
            ry *= 2;
        }
    }

    @Override
    public double generate(double x, double y) {
        x /= scaleX;
        y /= scaleY;

        double d = 1;
        double n = 0;

        for (RepetitiveVoronoi2D noise : noiseOctaves) {
            n += noise.generate(x * d, y * d) / d;
            d *= 2;
        }
        return n;
    }

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
        for (RepetitiveVoronoi2D value : noiseOctaves) {
            value.setSeed(seed++);
        }
    }
}
