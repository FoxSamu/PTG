/*
 * Copyright (c) 2020 RGSW
 * Licensed under the Apache 2.0 license
 */

package net.rgsw.ptg.noise.util;

/**
 * Helper class for basic mathematical operations used during noise generation.
 */
public final class NoiseMath {

    private NoiseMath() {
    }

    /**
     * Interpolates value between 0 and 1 to value between a and b using linear interpolation
     *
     * @param x Value between 0 and 1
     * @see NoiseMath#unlerp(double, double, double)
     */
    public static double lerp( double a, double b, double x ) {
        return a + x * ( b - a );
    }

    /**
     * Interpolates value between a and b to value between 0 and 1 using linear interpolation
     *
     * @param x Value between a and b
     * @see NoiseMath#lerp(double, double, double)
     */
    public static double unlerp( double a, double b, double x ) {
        return ( x - a ) / ( b - a );
    }

    /**
     * Applies smoothing function to a double value between 0 and 1, according to how Ken Perlin defined it.
     *
     * @param t The linear value (0 - 1)
     * @return The smoothed value (0 - 1)
     */
    public static double smooth( double t ) {
        return t * t * t * ( t * ( t * 6 - 15 ) + 10 );
    }

    /**
     * Limits a value to a range, applying a minimum and a maximum limit to a value.
     *
     * @param a The minimum limit
     * @param b The maximum limit
     * @param x The value, which should be limited to
     * @return The value, limited between <i>a</i> and <i>b</i>
     */
    public static double clamp( double a, double b, double x ) {
        if( a > b ) throw new IllegalArgumentException( "Minimum limit is more than maximum limit" );
        return x < a ? a : x > b ? b : x;
    }

    public static long floor( double v ) {
        long lv = (long) v;
        return v < 0 ? lv - 1 : lv;
    }

    public static int floorI( double v ) {
        int lv = (int) v;
        return v < 0 ? lv - 1 : lv;
    }

}
