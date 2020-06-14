/*
 * Copyright (c) 2020 RGSW
 * Licensed under the Apache 2.0 license
 */

package net.rgsw.ptg.noise.opensimplex;

import net.rgsw.ptg.noise.BaseNoise3D;
import net.rgsw.ptg.noise.util.NoiseMath;

/**
 * Inverse-Fractal-OpenSimplex noise generator for 3D space. This generator uses a specified amount of {@link
 * OpenSimplex3D}-instances as octaves.
 */
public class InverseFractalOpenSimplex3D extends BaseNoise3D {

    private final OpenSimplex3D[] noiseOctaves;

    /**
     * Constructs a Inverse-Fractal-OpenSimplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public InverseFractalOpenSimplex3D( int seed, int octaves ) {
        super( seed );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new OpenSimplex3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new OpenSimplex3D( seed );
        }
    }

    /**
     * Constructs a Inverse-Fractal-OpenSimplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalOpenSimplex3D( int seed, double scale, int octaves ) {
        super( seed, scale );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new OpenSimplex3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new OpenSimplex3D( seed );
        }
    }

    /**
     * Constructs a Inverse-Fractal-OpenSimplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param scaleZ  The coordinate scaling along Z axis.
     * @param octaves The amount of octaves.
     */
    public InverseFractalOpenSimplex3D( int seed, double scaleX, double scaleY, double scaleZ, int octaves ) {
        super( seed, scaleX, scaleY, scaleZ );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        noiseOctaves = new OpenSimplex3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            noiseOctaves[ i ] = new OpenSimplex3D( seed );
        }
    }

    @Override
    public double generate( double x, double y, double z ) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        double d = 1;
        double n = 0;

        for( OpenSimplex3D noise : noiseOctaves ) {
            n += noise.generate( x / d, y / d, z / d ) * d;
            d *= 2;
        }
        return NoiseMath.clamp( - 1, 1, n );
    }

    @Override
    public void setSeed( int seed ) {
        this.seed = seed;
        for( OpenSimplex3D noise : noiseOctaves ) {
            noise.setSeed( seed );
        }
    }
}
