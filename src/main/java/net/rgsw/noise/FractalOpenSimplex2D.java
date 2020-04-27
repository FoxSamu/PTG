/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.noise;

/**
 * Fractal-OpenSimplex noise generator for 2D space. This generator uses a specified amount of {@link
 * OpenSimplex2D}-instances as octaves.
 */
public class FractalOpenSimplex2D extends Noise2D {

    private final OpenSimplex2D[] noiseOctaves;

    /**
     * Constructs a Fractal-OpenSimplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public FractalOpenSimplex2D( int seed, int octaves ) {
        super( seed );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new OpenSimplex2D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new OpenSimplex2D( seed + i );
        }
    }

    /**
     * Constructs a Fractal-OpenSimplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public FractalOpenSimplex2D( int seed, double scale, int octaves ) {
        super( seed, scale );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new OpenSimplex2D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new OpenSimplex2D( seed );
        }
    }

    /**
     * Constructs a Fractal-OpenSimplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param octaves The amount of octaves.
     */
    public FractalOpenSimplex2D( int seed, double scaleX, double scaleY, int octaves ) {
        super( seed, scaleX, scaleY );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new OpenSimplex2D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new OpenSimplex2D( seed );
        }
    }

    @Override
    public double generate( double x, double y ) {
        x /= scaleX;
        y /= scaleY;

        double t = 0;
        double d = 1;
        double n = 0;

        for( OpenSimplex2D noise : noiseOctaves ) {
            t += 1 / d;
            n += noise.generate( x * d, y * d ) / d;
            d *= 2;
        }
        return n / t;
    }

    @Override
    public void setSeed( int seed ) {
        this.seed = seed;
        for( OpenSimplex2D noise : noiseOctaves ) {
            noise.setSeed( seed++ );
        }
    }
}
