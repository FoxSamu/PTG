/*
 * Copyright (c) 2020 RGSW
 * Licensed under the Apache 2.0 license
 */

package net.rgsw.noise;

/**
 * Inverse-Fractal-Perlin noise generator for 3D space. This generator uses a specified amount of {@link
 * Perlin3D}-instances as octaves.
 */
public class InverseFractalPerlin3D extends Noise3D {

    private final Perlin3D[] noiseOctaves;

    /**
     * Constructs a Inverse-Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public InverseFractalPerlin3D( int seed, int octaves ) {
        super( seed );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new Perlin3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new Perlin3D( seed );
        }
    }

    /**
     * Constructs a Inverse-Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalPerlin3D( int seed, double scale, int octaves ) {
        super( seed, scale );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new Perlin3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new Perlin3D( seed );
        }
    }

    /**
     * Constructs a Inverse-Fractal-Perlin noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param scaleZ  The coordinate scaling along Z axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalPerlin3D( int seed, double scaleX, double scaleY, double scaleZ, int octaves ) {
        super( seed, scaleX, scaleY, scaleZ );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new Perlin3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new Perlin3D( seed );
        }
    }

    @Override
    public double generate( double x, double y, double z ) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        double t = 0;
        double d = 1;
        double n = 0;

        for( Perlin3D noise : noiseOctaves ) {
            t += 1 * d;
            n += noise.generate( x / d, y / d, z / d ) * d;
            d *= 2;
        }
        return n / t;
    }

    @Override
    public void setSeed( int seed ) {
        this.seed = seed;
        for( Perlin3D perlin : noiseOctaves ) {
            perlin.setSeed( seed++ );
        }
    }
}
