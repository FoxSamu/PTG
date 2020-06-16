/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.ptg.region;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;

/**
 * A {@link Region} implementation that caches the generated values. Such regions are usually managed and created by a
 * {@link CachingRegionContext} instance.
 */
public class CachingRegion implements Region {
    /** The underlying {@link Region} which generates values or transforms values from another {@link Region}. */
    private final Region generator;

    /** The cache size limit of this region. When the cache size exceeds this limit, some entries are removed. */
    private final int cacheSize;

    /** The cache of this region. Coordinates are mapped to long keys using {@link #asLong(int, int)}. */
    private final Long2IntLinkedOpenHashMap cache;

    /**
     * Creates a {@link CachingRegion}. This is usually done by a {@link CachingRegionContext} instance.
     *
     * @param generator The underlying {@link Region} (see {@link #generator})
     * @param cacheSize The cache size limit (see {@link #cacheSize})
     */
    public CachingRegion( Region generator, int cacheSize ) {
        this.generator = generator;
        this.cacheSize = cacheSize;
        cache = new Long2IntLinkedOpenHashMap();
        cache.defaultReturnValue( Integer.MIN_VALUE );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getValue( int x, int z ) {
        long key = asLong( x, z );
        synchronized( cache ) {
            int cached = cache.get( key );
            if( cached != Integer.MIN_VALUE ) {
                return cached;
            } else {
                int value = generator.getValue( x, z );
                cache.put( key, value );
                if( cache.size() > cacheSize ) {
                    for( int i = 0; i < cacheSize / 16; ++ i ) {
                        cache.removeLastInt();
                    }
                }
                return value;
            }
        }
    }

    /**
     * Returns the cache size limit of this {@link CachingRegion}.
     */
    public int getMaxCacheSize() {
        return cacheSize;
    }

    private long asLong( int x, int z ) {
        return (long) x << 32 | z;
    }
}
