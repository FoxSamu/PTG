/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.noise;

/**
 * Utility class that generates random values based on a seed and coordinates, using hash functions.
 */
public final class Hash {

    private final static int X_PRIME = 1619;
    private final static int Y_PRIME = 31337;
    private final static int Z_PRIME = 6971;
    private final static int W_PRIME = 1013;

    private Hash() {
    }

    /**
     * Generates a random double value between -1 and 1 for 1D coordinates.
     *
     * @param seed The seed, can be any {@code int}
     * @param x    The x coordinate
     * @return The random double value
     */
    public static double hash1D( int seed, int x ) {
        int hash = seed;
        if( x < 0 ) x = Integer.MAX_VALUE + x;
        hash ^= X_PRIME * x;

        hash *= hash * hash * 60493;
        hash = hash >> 13 ^ hash;

        return (double) ( hash & Integer.MAX_VALUE ) / Integer.MAX_VALUE;
    }

    /**
     * Generates a random double value between -1 and 1 for 2D coordinates.
     *
     * @param seed The seed, can be any {@code int}
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @return The random double value
     */
    public static double hash2D( int seed, int x, int y ) {
        int hash = seed;
        if( x < 0 ) x = Integer.MAX_VALUE + x;
        if( y < 0 ) y = Integer.MAX_VALUE + y;
        hash ^= X_PRIME * x;
        hash ^= Y_PRIME * y;

        hash *= hash * hash * 60493;
        hash = hash >> 13 ^ hash;

        return (double) ( hash & Integer.MAX_VALUE ) / Integer.MAX_VALUE;
    }

    /**
     * Generates a random double value between -1 and 1 for 3D coordinates.
     *
     * @param seed The seed, can be any {@code int}
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @return The random double value
     */
    public static double hash3D( int seed, int x, int y, int z ) {
        int hash = seed;
        if( x < 0 ) x = Integer.MAX_VALUE + x;
        if( y < 0 ) y = Integer.MAX_VALUE + y;
        if( z < 0 ) z = Integer.MAX_VALUE + z;
        hash ^= X_PRIME * x;
        hash ^= Y_PRIME * y;
        hash ^= Z_PRIME * z;

        hash *= hash * hash * 60493;
        hash = hash >> 13 ^ hash;

        return (double) ( hash & Integer.MAX_VALUE ) / Integer.MAX_VALUE;
    }

    /**
     * Generates a random double value between -1 and 1 for 4D coordinates.
     *
     * @param seed The seed, can be any {@code int}
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @param w    The w coordinate
     * @return The random double value
     */
    public static double hash4D( int seed, int x, int y, int z, int w ) {
        int hash = seed;
        if( x < 0 ) x = Integer.MAX_VALUE + x;
        if( y < 0 ) y = Integer.MAX_VALUE + y;
        if( z < 0 ) z = Integer.MAX_VALUE + z;
        if( w < 0 ) w = Integer.MAX_VALUE + w;
        hash ^= X_PRIME * x;
        hash ^= Y_PRIME * y;
        hash ^= Z_PRIME * z;
        hash ^= W_PRIME * w;

        hash *= hash * hash * 60493;
        hash = hash >> 13 ^ hash;

        return (double) ( hash & Integer.MAX_VALUE ) / Integer.MAX_VALUE;
    }

    /**
     * Generates a random int value between integer limits for 1D coordinates.
     *
     * @param seed The seed, can be any {@code int}
     * @param x    The x coordinate
     * @return The random integer value
     */
    public static int hash1I( int seed, int x ) {
        int hash = seed;
        if( x < 0 ) x = Integer.MAX_VALUE + x;
        hash ^= X_PRIME * x;

        hash *= hash * hash * 60493;
        hash = hash >> 13 ^ hash;

        return hash;
    }

    /**
     * Generates a random int value between integer limits for 2D coordinates.
     *
     * @param seed The seed, can be any {@code int}
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @return The random integer value
     */
    public static int hash2I( int seed, int x, int y ) {
        int hash = seed;
        if( x < 0 ) x = Integer.MAX_VALUE + x;
        if( y < 0 ) y = Integer.MAX_VALUE + y;
        hash ^= X_PRIME * x;
        hash ^= Y_PRIME * y;

        hash *= hash * hash * 60493;
        hash = hash >> 13 ^ hash;

        return hash;
    }

    /**
     * Generates a random int value between integer limits for 3D coordinates.
     *
     * @param seed The seed, can be any {@code int}
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @return The random integer value
     */
    public static int hash3I( int seed, int x, int y, int z ) {
        int hash = seed;
        if( x < 0 ) x = Integer.MAX_VALUE + x;
        if( y < 0 ) y = Integer.MAX_VALUE + y;
        if( z < 0 ) z = Integer.MAX_VALUE + z;
        hash ^= X_PRIME * x;
        hash ^= Y_PRIME * y;
        hash ^= Z_PRIME * z;

        hash *= hash * hash * 60493;
        hash = hash >> 13 ^ hash;

        return hash;
    }

    /**
     * Generates a random int value between integer limits for 4D coordinates.
     *
     * @param seed The seed, can be any {@code int}
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param z    The z coordinate
     * @param w    The w coordinate
     * @return The random integer value
     */
    public static int hash4I( int seed, int x, int y, int z, int w ) {
        int hash = seed;
        if( x < 0 ) x = Integer.MAX_VALUE + x;
        if( y < 0 ) y = Integer.MAX_VALUE + y;
        if( z < 0 ) z = Integer.MAX_VALUE + z;
        if( w < 0 ) w = Integer.MAX_VALUE + w;
        hash ^= X_PRIME * x;
        hash ^= Y_PRIME * y;
        hash ^= Z_PRIME * z;
        hash ^= W_PRIME * w;

        hash *= hash * hash * 60493;
        hash = hash >> 13 ^ hash;

        return hash;
    }

    // Double precision hash functions
    public static int hash1I( int seed, long x ) {
        return 31 * hash1I( seed, (int) ( x & 0xFFFFFFFFL ) )
                   + hash1I( seed * 31 + Y_PRIME, (int) ( x >>> 32 & 0xFFFFFFFFL ) );
    }

    public static int hash2I( int seed, long x, long y ) {
        return 31 * hash2I( seed, (int) ( x & 0xFFFFFFFFL ), (int) ( y & 0xFFFFFFFFL ) )
                   + hash2I( seed * 31 + Y_PRIME, (int) ( x >>> 32 & 0xFFFFFFFFL ), (int) ( y >>> 32 & 0xFFFFFFFFL ) );
    }

    public static int hash3I( int seed, long x, long y, long z ) {
        return 31 * hash3I( seed, (int) ( x & 0xFFFFFFFFL ), (int) ( y & 0xFFFFFFFFL ), (int) ( z & 0xFFFFFFFFL ) )
                   + hash3I( seed * 31 + Y_PRIME, (int) ( x >>> 32 & 0xFFFFFFFFL ), (int) ( y >>> 32 & 0xFFFFFFFFL ), (int) ( z >>> 32 & 0xFFFFFFFFL ) );
    }

    public static int hash4I( int seed, long x, long y, long z, long w ) {
        return 31 * hash4I( seed, (int) ( x & 0xFFFFFFFFL ), (int) ( y & 0xFFFFFFFFL ), (int) ( z & 0xFFFFFFFFL ), (int) ( w & 0xFFFFFFFFL ) )
                   + hash4I( seed * 31 + Y_PRIME, (int) ( x >>> 32 & 0xFFFFFFFFL ), (int) ( y >>> 32 & 0xFFFFFFFFL ), (int) ( z >>> 32 & 0xFFFFFFFFL ), (int) ( w >>> 32 & 0xFFFFFFFFL ) );
    }
}
