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
 * Fractal-Perlin noise generator for 2D space. This generator uses a specified amount of {@link Perlin2D}-instances as
 * octaves.
 */
public class FractalPerlin2D extends Noise2D {

    private final Perlin2D[] noiseOctaves;

    /**
     * Constructs a Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public FractalPerlin2D( int seed, int octaves ) {
        super( seed );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new Perlin2D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new Perlin2D( seed + i );
        }
    }

    /**
     * Constructs a Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public FractalPerlin2D( int seed, double scale, int octaves ) {
        super( seed, scale );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new Perlin2D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new Perlin2D( seed );
        }
    }

    /**
     * Constructs a Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param octaves The amount of octaves.
     */
    public FractalPerlin2D( int seed, double scaleX, double scaleY, int octaves ) {
        super( seed, scaleX, scaleY );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new Perlin2D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new Perlin2D( seed );
        }
    }

    @Override
    public double generate( double x, double y ) {
        x /= scaleX;
        y /= scaleY;

        double t = 0;
        double d = 1;
        double n = 0;

        for( Perlin2D noise : noiseOctaves ) {
            t += 1 / d;
            n += noise.generate( x * d, y * d ) / d;
            d *= 2;
        }
        return n / t;
    }

    @Override
    public void setSeed( int seed ) {
        this.seed = seed;
        for( Perlin2D perlin : noiseOctaves ) {
            perlin.setSeed( seed++ );
        }
    }
}
