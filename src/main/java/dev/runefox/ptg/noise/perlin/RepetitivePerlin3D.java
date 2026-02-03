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

package dev.runefox.ptg.noise.perlin;

import dev.runefox.ptg.noise.RepetitiveNoise3D;
import dev.runefox.ptg.noise.util.Hash;

/**
 * 3D Perlin noise generator.
 */
public class RepetitivePerlin3D extends RepetitiveNoise3D {

    /**
     * Constructs a Perlin noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public RepetitivePerlin3D(int seed, int repeat) {
        super(seed, repeat);
    }

    /**
     * Constructs a Perlin noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public RepetitivePerlin3D(int seed, double scale, int repeat) {
        super(seed, scale, repeat);
    }

    /**
     * Constructs a Perlin noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     * @param scaleZ The coordinate scaling along Z axis
     */
    public RepetitivePerlin3D(int seed, double scaleX, double scaleY, double scaleZ, int repeatX, int repeatY, int repeatZ) {
        super(seed, scaleX, scaleY, scaleZ, repeatX, repeatY, repeatZ);
    }

    private long repeat(long val, int repeat) {
        return val % repeat + (val < 0 ? repeat : 0);
    }

    private int hash(long x, long y, long z) {
        return Hash.hash3I(seed, repeat(x, repeatX), repeat(y, repeatY), repeat(z, repeatZ));
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        return Perlin.compute(x, y, z, this::hash);
    }
}
