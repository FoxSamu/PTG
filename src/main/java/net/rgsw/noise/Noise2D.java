/*
 * Copyright (c) 2019 RGSW
 * All rights reserved. Do not distribute.
 * This file is part of the Modernity, and is licensed under the terms and conditions of RedGalaxy.
 *
 * Date:   11 - 14 - 2019
 * Author: rgsw
 */

package net.rgsw.noise;

/**
 * Generic class for 2D noise generator implementations ({@link INoise2D} generators).
 */
public abstract class Noise2D implements INoise2D {
    protected int seed;
    protected final double scaleX;
    protected final double scaleY;

    /**
     * Generic constructor for a 2D noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    protected Noise2D( int seed ) {
        this.seed = seed;
        this.scaleX = 1;
        this.scaleY = 1;
    }

    /**
     * Generic constructor for a 2D noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The scaling of the noise field along all axes
     */
    protected Noise2D( int seed, double scale ) {
        this.seed = seed;
        this.scaleX = scale;
        this.scaleY = scale;
    }

    /**
     * Generic constructor for a 2D noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The scaling of the noise field along X axis
     * @param scaleY The scaling of the noise field along Y axis
     */
    protected Noise2D( int seed, double scaleX, double scaleY ) {
        this.seed = seed;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public abstract double generate( double x, double y );

    /**
     * Generates a noise and multiplies it by a specific amount.
     *
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param mult The multiplier
     * @return The generated noise value
     */
    @Override
    public double generateMultiplied( double x, double y, double mult ) {
        return generate( x, y ) * mult;
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
    public double generateInRange( double x, double y, double min, double max ) {
        return NoiseMath.lerp( min, max, ( generate( x, y ) + 1 ) / 2 );
    }

    /**
     * Sets the seed of this generator
     *
     * @param seed The new seed
     */
    public void setSeed( int seed ) {
        this.seed = seed;
    }

    /**
     * Returns the seed of this generator
     */
    public int getSeed() {
        return seed;
    }
}
