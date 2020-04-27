/*
 * Copyright (c) 2020 RGSW
 * Licensed under the Apache 2.0 license
 */

package net.rgsw.noise;

/**
 * Generic class for 3D noise generator implementations ({@link INoise3D} generators).
 */
public abstract class Noise3D implements INoise3D {
    protected int seed;
    protected final double scaleX;
    protected final double scaleY;
    protected final double scaleZ;

    /**
     * Generic constructor for a 3D noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Noise3D( int seed ) {
        this.seed = seed;
        this.scaleX = 1;
        this.scaleY = 1;
        this.scaleZ = 1;
    }

    /**
     * Generic constructor for a 3D noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The scaling of the noise field along all axes
     */
    public Noise3D( int seed, double scale ) {
        this.seed = seed;
        this.scaleX = scale;
        this.scaleY = scale;
        this.scaleZ = scale;
    }

    /**
     * Generic constructor for a 3D noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The scaling of the noise field along X axis
     * @param scaleY The scaling of the noise field along Y axis
     * @param scaleZ The scaling of the noise field along Z axis
     */
    public Noise3D( int seed, double scaleX, double scaleY, double scaleZ ) {
        this.seed = seed;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    /**
     * Returns the seed of this generator
     */
    public int getSeed() {
        return this.seed;
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
     * Generates a noise and multiplies it by a specific amount.
     *
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @param mult The multiplier
     * @return The generated noise value
     */
    @Override
    public double generateMultiplied( double x, double y, double z, double mult ) {
        return generate( x, y, z ) * mult;
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
    public double generateInRange( double x, double y, double z, double min, double max ) {
        return NoiseMath.lerp( min, max, ( generate( x, y, z ) + 1 ) / 2 );
    }
}
