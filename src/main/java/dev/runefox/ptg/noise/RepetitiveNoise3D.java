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

package dev.runefox.ptg.noise;

import dev.runefox.ptg.noise.util.NoiseMath;

/**
 * Generic class for repeating 3D noise generator implementations ({@link Noise3D} generators).
 */
public abstract class RepetitiveNoise3D implements Noise3D {
    protected int seed;
    protected final double scaleX;
    protected final double scaleY;
    protected final double scaleZ;
    protected final int repeatX;
    protected final int repeatY;
    protected final int repeatZ;

    /**
     * Generic constructor for a 3D noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public RepetitiveNoise3D(int seed, int repeat) {
        this.seed = seed;
        this.scaleX = 1;
        this.scaleY = 1;
        this.scaleZ = 1;
        this.repeatX = repeat;
        this.repeatY = repeat;
        this.repeatZ = repeat;
    }

    /**
     * Generic constructor for a 3D noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The scaling of the noise field along all axes
     */
    public RepetitiveNoise3D(int seed, double scale, int repeat) {
        this.seed = seed;
        this.scaleX = scale;
        this.scaleY = scale;
        this.scaleZ = scale;
        this.repeatX = repeat;
        this.repeatY = repeat;
        this.repeatZ = repeat;
    }

    /**
     * Generic constructor for a 3D noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The scaling of the noise field along X axis
     * @param scaleY The scaling of the noise field along Y axis
     * @param scaleZ The scaling of the noise field along Z axis
     */
    public RepetitiveNoise3D(int seed, double scaleX, double scaleY, double scaleZ, int repeatX, int repeatY, int repeatZ) {
        this.seed = seed;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
        this.repeatX = repeatX;
        this.repeatY = repeatY;
        this.repeatZ = repeatZ;
    }

    /**
     * Returns the seed of this generator
     */
    public int getSeed() {
        return seed;
    }

    /**
     * Sets the seed of this generator
     *
     * @param seed The new seed
     */
    public void setSeed(int seed) {
        this.seed = seed;
    }

    /**
     * Generates a noise and multiplies it by a specific amount.
     *
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @param mult The multiplier
     * @return The generated noise value
     */
    @Override
    public double generateMultiplied(double x, double y, double z, double mult) {
        return generate(x, y, z) * mult;
    }

    /**
     * Generates a noise value in a specific range.
     *
     * @param x   The x coordinate
     * @param y   The y coordinate
     * @param z   The z coordinate
     * @param min The minimum limit of the range
     * @param max The maximum limit of the range
     * @return The generated noise value
     */
    public double generateInRange(double x, double y, double z, double min, double max) {
        return NoiseMath.lerp(min, max, (generate(x, y, z) + 1) / 2);
    }
}
