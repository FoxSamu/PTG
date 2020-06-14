/*
 * Copyright (c) 2020 RGSW
 * Licensed under the Apache 2.0 license
 */

package net.rgsw.ptg.noise.perlin;

import net.rgsw.ptg.noise.util.Hash;
import net.rgsw.ptg.noise.RepetitiveNoise2D;

/**
 * 2D Perlin noise generator.
 */
public class RepetitivePerlin2D extends RepetitiveNoise2D {

    /**
     * Constructs a Perlin noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public RepetitivePerlin2D( int seed, int repeat ) {
        super( seed, repeat );
    }

    /**
     * Constructs a Perlin noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public RepetitivePerlin2D( int seed, double scale, int repeat ) {
        super( seed, scale, repeat );
    }

    /**
     * Constructs a Perlin noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public RepetitivePerlin2D( int seed, double scaleX, double scaleY, int repeatX, int repeatY ) {
        super( seed, scaleX, scaleY, repeatX, repeatY );
    }

    private static final int[][] GRAD = {
        { - 1, - 1 },
        { - 1, 1 },
        { 1, - 1 },
        { 1, 1 },
        { - 1, - 1 },
        { - 1, 1 },
        { 1, - 1 },
        { 1, 1 },
        { - 1, 0 },
        { - 1, 0 },
        { 1, 0 },
        { 1, 0 },
        { 0, - 1 },
        { 0, 1 },
        { 0, - 1 },
        { 0, 1 }
    };

    private long repeat( long val, int repeat ) {
        return val % repeat + ( val < 0 ? repeat : 0 );
    }

    private int gradIndex( long x, long y ) {
        return Hash.hash2I( seed, repeat( x, repeatX ), repeat( y, repeatY ) ) & 15;
    }

    private static double lerp( double a, double b, double x ) {
        return a + x * ( b - a );
    }

    private static double smooth( double t ) {
        return t * t * t * ( t * ( t * 6 - 15 ) + 10 );
    }

    private static long fastfloor( double v ) {
        int d = v < 0 ? - 1 : 0;
        return (long) v + d;
    }

    @Override
    public double generate( double x, double y ) {
        x /= scaleX;
        y /= scaleY;

        long minx = fastfloor( x );
        long miny = fastfloor( y );
        long maxx = minx + 1L;
        long maxy = miny + 1L;

        double smoothx = smooth( x - minx );
        double smoothy = smooth( y - miny );


        int idx1 = gradIndex( minx, miny );
        int idx2 = gradIndex( maxx, miny );
        int idx3 = gradIndex( minx, maxy );
        int idx4 = gradIndex( maxx, maxy );

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

        double lerp12 = lerp( dot1, dot2, smoothx );
        double lerp34 = lerp( dot3, dot4, smoothx );

        return lerp( lerp12, lerp34, smoothy );
    }
}
