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
 * Fractal-OpenSimplex noise generator for 3D space. This generator uses a specified amount of {@link
 * OpenSimplex3D}-instances as octaves.
 */
public class FractalOpenSimplex3D extends Noise3D {

    private final OpenSimplex3D[] noiseOctaves;

    /**
     * Constructs a Fractal-OpenSimplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param octaves The amount of octaves.
     */
    public FractalOpenSimplex3D( int seed, int octaves ) {
        super( seed );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        this.noiseOctaves = new OpenSimplex3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            this.noiseOctaves[ i ] = new OpenSimplex3D( seed );
        }
    }

    /**
     * Constructs a Fractal-OpenSimplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scale   The coordinate scaling along every axis.
     * @param octaves The amount of octaves.
     */
    public FractalOpenSimplex3D( int seed, double scale, int octaves ) {
        super( seed, scale );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        this.noiseOctaves = new OpenSimplex3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            this.noiseOctaves[ i ] = new OpenSimplex3D( seed );
        }
    }

    /**
     * Constructs a Fractal-OpenSimplex noise generator.
     *
     * @param seed    The seed, may be any {@code int}.
     * @param scaleX  The coordinate scaling along X axis.
     * @param scaleY  The coordinate scaling along Y axis.
     * @param scaleZ  The coordinate scaling along Z axis.
     * @param octaves The amount of octaves.
     */
    public FractalOpenSimplex3D( int seed, double scaleX, double scaleY, double scaleZ, int octaves ) {
        super( seed, scaleX, scaleY, scaleZ );

        if( octaves < 1 ) {
            throw new IllegalArgumentException( "There should be at least one octave." );
        }

        this.noiseOctaves = new OpenSimplex3D[ octaves ];

        for( int i = 0; i < octaves; i++ ) {
            this.noiseOctaves[ i ] = new OpenSimplex3D( seed );
        }
    }

    @Override
    public double generate( double x, double y, double z ) {
        x /= this.scaleX;
        y /= this.scaleY;
        z /= this.scaleZ;

        double d = 1;
        double n = 0;

        for( OpenSimplex3D noise : this.noiseOctaves ) {
            n += noise.generate( x * d, y * d, z * d ) / d;
            d *= 2;
        }
        return NoiseMath.clamp( - 1, 1, n );
    }

    @Override
    public void setSeed( int seed ) {
        this.seed = seed;
        for( OpenSimplex3D noise : this.noiseOctaves ) {
            noise.setSeed( this.seed );
        }
    }
}
