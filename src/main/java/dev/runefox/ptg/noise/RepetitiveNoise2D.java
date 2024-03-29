/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise;

import dev.runefox.ptg.noise.util.NoiseMath;

/**
 * Generic class for repeating 2D noise generator implementations ({@link Noise2D} generators).
 */
public abstract class RepetitiveNoise2D implements Noise2D {
    protected int seed;
    protected final double scaleX;
    protected final double scaleY;
    protected final int repeatX;
    protected final int repeatY;

    /**
     * Generic constructor for a 2D noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    protected RepetitiveNoise2D(int seed, int repeat) {
        this.seed = seed;
        this.scaleX = 1;
        this.scaleY = 1;
        this.repeatX = repeat;
        this.repeatY = repeat;
    }

    /**
     * Generic constructor for a 2D noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The scaling of the noise field along all axes
     */
    protected RepetitiveNoise2D(int seed, double scale, int repeat) {
        this.seed = seed;
        this.scaleX = scale;
        this.scaleY = scale;
        this.repeatX = repeat;
        this.repeatY = repeat;
    }

    /**
     * Generic constructor for a 2D noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The scaling of the noise field along X axis
     * @param scaleY The scaling of the noise field along Y axis
     */
    protected RepetitiveNoise2D(int seed, double scaleX, double scaleY, int repeatX, int repeatY) {
        this.seed = seed;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.repeatX = repeatX;
        this.repeatY = repeatY;
    }

    /**
     * Generates a noise and multiplies it by a specific amount.
     *
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param mult The multiplier
     * @return The generated noise value
     */
    @Override
    public double generateMultiplied(double x, double y, double mult) {
        return generate(x, y) * mult;
    }

    /**
     * Generates a noise value in a specific range.
     *
     * @param x   The x coordinate
     * @param y   The y coordinate
     * @param min The minimum limit of the range
     * @param max The maximum limit of the range
     * @return The generated noise value
     */
    public double generateInRange(double x, double y, double min, double max) {
        return NoiseMath.lerp(min, max, (generate(x, y) + 1) / 2);
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
     * Returns the seed of this generator
     */
    public int getSeed() {
        return seed;
    }
}
