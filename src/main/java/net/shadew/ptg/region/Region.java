/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region;

/**
 * A function that maps XZ-coordinates to integers, based on a procedurally generated noise field. These values are
 * either generated directly, or read from a cache map.
 *
 * @see LazyRegion
 */
@FunctionalInterface
public interface Region {
    /**
     * Gets the value for the specified coordinates by either computing it or reading it from cache.
     *
     * @param x The X coordinate
     * @param z The Z coordinate
     * @return The value at the coordinates.
     */
    int getValue( int x, int z );

    /**
     * Gets the value for the specified coordinates by either computing it or reading it from cache, and returns it as a
     * floating point representation using {@link Float#intBitsToFloat intBitsToFloat}.
     *
     * @param x The X coordinate
     * @param z The Z coordinate
     * @return The floating point value at the coordinates.
     */
    default float getFPValue( int x, int z ) {
        return Float.intBitsToFloat( getValue( x, z ) );
    }
}
