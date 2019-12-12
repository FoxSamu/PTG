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
 * 2D Perlin noise generator.
 */
public class Perlin2D extends Noise2D {

    /**
     * Constructs a Perlin noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Perlin2D( int seed ) {
        super( seed );
    }

    /**
     * Constructs a Perlin noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public Perlin2D( int seed, double scale ) {
        super( seed, scale );
    }

    /**
     * Constructs a Perlin noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public Perlin2D( int seed, double scaleX, double scaleY ) {
        super( seed, scaleX, scaleY );
    }

    private static final int[][] GRAD = {
        { - 1, - 1 }, { - 1, 1 }, { 1, - 1 }, { 1, 1 },
        { - 1, - 1 }, { - 1, 1 }, { 1, - 1 }, { 1, 1 },
        { - 1, 0 }, { - 1, 0 }, { 1, 0 }, { 1, 0 },
        { 0, - 1 }, { 0, 1 }, { 0, - 1 }, { 0, 1 }
    };

    private int gradIndex( int x, int y ) {
        return Hash.hash2I( this.seed, x, y ) & 15;
    }

    private double lerp( double a, double b, double x ) {
        return a + x * ( b - a );
    }

    private static double smooth( double t ) {
        return t * t * t * ( t * ( t * 6 - 15 ) + 10 );
    }

    private static int fastfloor( double v ) {
        int d = v < 0 ? - 1 : 0;
        return (int) v + d;
    }

    @Override
    public double generate( double x, double y ) {
        x /= this.scaleX;
        y /= this.scaleY;

        int minx = fastfloor( x );
        int miny = fastfloor( y );
        int maxx = minx + 1;
        int maxy = miny + 1;

        double smoothx = smooth( x - minx );
        double smoothy = smooth( y - miny );


        int idx1 = this.gradIndex( minx, miny );
        int idx2 = this.gradIndex( maxx, miny );
        int idx3 = this.gradIndex( minx, maxy );
        int idx4 = this.gradIndex( maxx, maxy );

        int[] grad1 = GRAD[ idx1 ];
        int[] grad2 = GRAD[ idx2 ];
        int[] grad3 = GRAD[ idx3 ];
        int[] grad4 = GRAD[ idx4 ];

        double dist10 = x - minx;
        double dist11 = y - miny;
        double dist20 = x - maxx;
        double dist21 = y - miny;
        double dist30 = x - minx;
        double dist31 = y - maxy;
        double dist40 = x - maxx;
        double dist41 = y - maxy;

        double dot1 = dist10 * grad1[ 0 ] + dist11 * grad1[ 1 ];
        double dot2 = dist20 * grad2[ 0 ] + dist21 * grad2[ 1 ];
        double dot3 = dist30 * grad3[ 0 ] + dist31 * grad3[ 1 ];
        double dot4 = dist40 * grad4[ 0 ] + dist41 * grad4[ 1 ];

        double lerp12 = this.lerp( dot1, dot2, smoothx );
        double lerp34 = this.lerp( dot3, dot4, smoothx );

        return this.lerp( lerp12, lerp34, smoothy );
    }
}
