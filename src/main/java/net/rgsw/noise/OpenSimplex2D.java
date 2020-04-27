/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.noise;

/**
 * 2D OpenSimplex noise generator based on an implementation by Kurt Spencer: https://gist.github.com/KdotJPG/b1270127455a94ac5d19
 */
public class OpenSimplex2D extends Noise2D {
    private static final double STRETCH_CONSTANT_2D = - 0.211324865405187;
    private static final double SQUISH_CONSTANT_2D = 0.366025403784439;

    private static final double NORM_CONSTANT_2D = 47;

    private static final byte[] GRAD = {
        5, 2,
        2, 5,
        - 5, 2,
        - 2, 5,
        5, - 2,
        2, - 5,
        - 5, - 2,
        - 2, - 5,
    };

    /**
     * Constructs an OpenSimplex noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public OpenSimplex2D( int seed ) {
        super( seed );
    }

    /**
     * Constructs an OpenSimplex noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public OpenSimplex2D( int seed, double scale ) {
        super( seed, scale );
    }

    /**
     * Constructs an OpenSimplex noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public OpenSimplex2D( int seed, double scaleX, double scaleY ) {
        super( seed, scaleX, scaleY );
    }


    private double extrapolate( long xsb, long ysb, double dx, double dy ) {
        int index = Hash.hash2I( seed, xsb, ysb ) & 0x0E;
        return GRAD[ index ] * dx + GRAD[ index + 1 ] * dy;
    }

    private static long fastfloor( double x ) {
        int xi = (int) x;
        return x < xi ? xi - 1 : xi;
    }

    @Override
    public double generate( double x, double y ) {

        x /= scaleX;
        y /= scaleY;

        // Place input coordinates onto grid.
        double stretchOffset = ( x + y ) * STRETCH_CONSTANT_2D;
        double xs = x + stretchOffset;
        double ys = y + stretchOffset;

        // Floor to get grid coordinates of rhombus (stretched square) super-cell origin.
        long xsb = fastfloor( xs );
        long ysb = fastfloor( ys );

        // Skew out to get actual coordinates of rhombus origin. We'll need these later.
        double squishOffset = ( xsb + ysb ) * SQUISH_CONSTANT_2D;
        double xb = xsb + squishOffset;
        double yb = ysb + squishOffset;

        // Compute grid coordinates relative to rhombus origin.
        double xins = xs - xsb;
        double yins = ys - ysb;

        // Sum those together to get a value that determines which region we're in.
        double inSum = xins + yins;

        // Positions relative to origin point.
        double dx0 = x - xb;
        double dy0 = y - yb;

        // We'll be defining these inside the next block and using them afterwards.
        double dx_ext;
        double dy_ext;
        long xsv_ext;
        long ysv_ext;

        double value = 0;

        // Contribution (1,0)
        double dx1 = dx0 - 1 - SQUISH_CONSTANT_2D;
        double dy1 = dy0 - 0 - SQUISH_CONSTANT_2D;
        double attn1 = 2 - dx1 * dx1 - dy1 * dy1;
        if( attn1 > 0 ) {
            attn1 *= attn1;
            value += attn1 * attn1 * extrapolate( xsb + 1, ysb, dx1, dy1 );
        }

        // Contribution (0,1)
        double dx2 = dx0 - 0 - SQUISH_CONSTANT_2D;
        double dy2 = dy0 - 1 - SQUISH_CONSTANT_2D;
        double attn2 = 2 - dx2 * dx2 - dy2 * dy2;
        if( attn2 > 0 ) {
            attn2 *= attn2;
            value += attn2 * attn2 * extrapolate( xsb, ysb + 1, dx2, dy2 );
        }

        if( inSum <= 1 ) { // We're inside the triangle (2-Simplex) at (0,0)
            double zins = 1 - inSum;
            if( zins > xins || zins > yins ) { // (0,0) is one of the closest two triangular vertices
                if( xins > yins ) {
                    xsv_ext = xsb + 1;
                    ysv_ext = ysb - 1;
                    dx_ext = dx0 - 1;
                    dy_ext = dy0 + 1;
                } else {
                    xsv_ext = xsb - 1;
                    ysv_ext = ysb + 1;
                    dx_ext = dx0 + 1;
                    dy_ext = dy0 - 1;
                }
            } else { // (1,0) and (0,1) are the closest two vertices.
                xsv_ext = xsb + 1;
                ysv_ext = ysb + 1;
                dx_ext = dx0 - 1 - 2 * SQUISH_CONSTANT_2D;
                dy_ext = dy0 - 1 - 2 * SQUISH_CONSTANT_2D;
            }
        } else { // We're inside the triangle (2-Simplex) at (1,1)
            double zins = 2 - inSum;
            if( zins < xins || zins < yins ) { // (0,0) is one of the closest two triangular vertices
                if( xins > yins ) {
                    xsv_ext = xsb + 2;
                    ysv_ext = ysb;
                    dx_ext = dx0 - 2 - 2 * SQUISH_CONSTANT_2D;
                    dy_ext = dy0 + 0 - 2 * SQUISH_CONSTANT_2D;
                } else {
                    xsv_ext = xsb;
                    ysv_ext = ysb + 2;
                    dx_ext = dx0 + 0 - 2 * SQUISH_CONSTANT_2D;
                    dy_ext = dy0 - 2 - 2 * SQUISH_CONSTANT_2D;
                }
            } else { // (1,0) and (0,1) are the closest two vertices.
                dx_ext = dx0;
                dy_ext = dy0;
                xsv_ext = xsb;
                ysv_ext = ysb;
            }
            xsb += 1;
            ysb += 1;
            dx0 = dx0 - 1 - 2 * SQUISH_CONSTANT_2D;
            dy0 = dy0 - 1 - 2 * SQUISH_CONSTANT_2D;
        }

        // Contribution (0,0) or (1,1)
        double attn0 = 2 - dx0 * dx0 - dy0 * dy0;
        if( attn0 > 0 ) {
            attn0 *= attn0;
            value += attn0 * attn0 * extrapolate( xsb, ysb, dx0, dy0 );
        }

        // Extra Vertex
        double attn_ext = 2 - dx_ext * dx_ext - dy_ext * dy_ext;
        if( attn_ext > 0 ) {
            attn_ext *= attn_ext;
            value += attn_ext * attn_ext * extrapolate( xsv_ext, ysv_ext, dx_ext, dy_ext );
        }

        return NoiseMath.clamp( - 1, 1, value / NORM_CONSTANT_2D );
    }
}
