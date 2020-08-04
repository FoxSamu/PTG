/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.perlin;

import net.shadew.ptg.noise.BaseNoise2D;
import net.shadew.ptg.noise.RepetitiveNoise2D;

/**
 * Repeating gractal-Perlin noise generator for 2D space. This generator uses a specified amount of {@link
 * RepetitivePerlin2D}-instances as octaves.
 */
public class RepetitiveFractalPerlin2D extends RepetitiveNoise2D {

    private final RepetitivePerlin2D[] noiseOctaves;

    /**
     * Constructs a Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param repeat  The amount of noise grid cells before repeating.
     * @param octaves The amount of octaves.
     */
    public RepetitiveFractalPerlin2D(int seed, int repeat, int octaves) {
        super(seed, repeat);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new RepetitivePerlin2D[octaves];

        int r = repeat;
        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new RepetitivePerlin2D(seed + i, r);
            r *= 2;
        }
    }

    /**
     * Constructs a Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param repeat  The amount of noise grid cells before repeating.
     * @param octaves The amount of octaves.
     */
    public RepetitiveFractalPerlin2D(int seed, double scale, int repeat, int octaves) {
        super(seed, scale, repeat);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new RepetitivePerlin2D[octaves];

        int r = repeat;
        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new RepetitivePerlin2D(seed, r);
            r *= 2;
        }
    }

    /**
     * Constructs a Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param repeatX The amount of noise grid cells before repeating the X axis.
     * @param repeatY The amount of noise grid cells before repeating the Y axis.
     * @param octaves The amount of octaves.
     */
    public RepetitiveFractalPerlin2D(int seed, double scaleX, double scaleY, int repeatX, int repeatY, int octaves) {
        super(seed, scaleX, scaleY, repeatX, repeatY);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new RepetitivePerlin2D[octaves];

        int rx = repeatX;
        int ry = repeatY;
        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new RepetitivePerlin2D(seed, 1, 1, rx, ry);
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

        for (RepetitivePerlin2D noise : noiseOctaves) {
            n += noise.generate(x * d, y * d) / d;
            d *= 2;
        }
        return n;
    }

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
        for (RepetitivePerlin2D perlin : noiseOctaves) {
            perlin.setSeed(seed++);
        }
    }
}
