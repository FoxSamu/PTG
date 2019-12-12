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
 * Fractal-Perlin noise generator for 3D space. This generator uses a specified amount of {@link Perlin3D}-instances as
 * octaves.
 */
public class FractalPerlin3D extends Noise3D {

    private final Perlin3D[] noiseOctaves;

    /**
     * Constructs a Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public FractalPerlin3D( int seed, int octaves ) {
        super( seed );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        this.noiseOctaves = new Perlin3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            this.noiseOctaves[ i ] = new Perlin3D( seed );
        }
    }

    /**
     * Constructs a Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public FractalPerlin3D( int seed, double scale, int octaves ) {
        super( seed, scale );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        this.noiseOctaves = new Perlin3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            this.noiseOctaves[ i ] = new Perlin3D( seed );
        }
    }

    /**
     * Constructs a Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param scaleZ  The coordinate scaling along Z axis.
     * @param octaves The amount of octaves.
     */
    public FractalPerlin3D( int seed, double scaleX, double scaleY, double scaleZ, int octaves ) {
        super( seed, scaleX, scaleY, scaleZ );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        this.noiseOctaves = new Perlin3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            this.noiseOctaves[ i ] = new Perlin3D( seed );
        }
    }

    @Override
    public double generate( double x, double y, double z ) {
        x /= this.scaleX;
        y /= this.scaleY;
        z /= this.scaleZ;

        double t = 0;
        double d = 1;
        double n = 0;

        for( Perlin3D noise : this.noiseOctaves ) {
            t += 1 / d;
            n += noise.generate( x * d, y * d, z * d ) / d;
            d *= 2;
        }
        return n / t;
    }

    @Override
    public void setSeed( int seed ) {
        this.seed = seed;
        for( Perlin3D perlin : this.noiseOctaves ) {
            perlin.setSeed( this.seed );
        }
    }
}