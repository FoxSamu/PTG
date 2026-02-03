/*
 * Copyright 2020-2026 O. W. Nankman
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
 * AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package dev.runefox.ptg.noise.cell;

import dev.runefox.ptg.noise.BaseNoise3D;

/**
 * Inverse-Fractal-Cell noise generator for 3D space. This generator uses a specified amount of {@link Cell3D}-instances
 * as octaves.
 */
public class InverseFractalCell3D extends BaseNoise3D {

    private final Cell3D[] noiseOctaves;

    /**
     * Constructs a Inverse-Fractal-Cell noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public InverseFractalCell3D(int seed, int octaves) {
        super(seed);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Cell3D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Cell3D(seed);
        }
    }

    /**
     * Constructs a Inverse-Fractal-Cell noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalCell3D(int seed, double scale, int octaves) {
        super(seed, scale);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Cell3D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Cell3D(seed);
        }
    }

    /**
     * Constructs a Inverse-Fractal-Cell noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param scaleZ  The coordinate scaling along Z axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalCell3D(int seed, double scaleX, double scaleY, double scaleZ, int octaves) {
        super(seed, scaleX, scaleY, scaleZ);

        if (octaves < 1) {
            throw new IllegalArgumentException("There should be at least one octave.");
        }

        noiseOctaves = new Cell3D[octaves];

        for (int i = 0; i < octaves; i++) {
            noiseOctaves[i] = new Cell3D(seed);
        }
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        double d = 1;
        double n = 0;

        for (Cell3D noise : noiseOctaves) {
            n += noise.generate(x / d, y / d, z / d) * d;
            d *= 2;
        }
        return n;
    }

    @Override
    public void setSeed(int seed) {
        this.seed = seed;
        for (Cell3D value : noiseOctaves) {
            value.setSeed(seed++);
        }
    }
}
