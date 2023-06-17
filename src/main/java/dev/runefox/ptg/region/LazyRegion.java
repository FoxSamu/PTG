/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;

/**
 * A {@link Region} implementation that caches the generated values. Such regions are usually managed and created by a
 * {@link LazyRegionContext} instance.
 */
public class LazyRegion implements Region {
    /** The underlying {@link Region} which generates values or transforms values from another {@link Region}. */
    private final Region generator;

    /** The cache size limit of this region. When the cache size exceeds this limit, some entries are removed. */
    private final int cacheSize;

    /** The cache of this region. Coordinates are mapped to long keys using {@link #asLong(int, int)}. */
    private final Long2IntLinkedOpenHashMap cache;

    /**
     * Creates a {@link LazyRegion}. This is usually done by a {@link LazyRegionContext} instance.
     *
     * @param generator The underlying {@link Region} (see {@link #generator})
     * @param cacheSize The cache size limit (see {@link #cacheSize})
     */
    public LazyRegion(Region generator, int cacheSize) {
        this.generator = generator;
        this.cacheSize = cacheSize;
        cache = new Long2IntLinkedOpenHashMap();
        cache.defaultReturnValue(Integer.MIN_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getValue(int x, int z) {
        long key = asLong(x, z);
        synchronized (cache) {
            if (cache.containsKey(key)) {
                // Value cached
                return cache.get(key);
            } else {
                // Value not cached, generate it
                int value = generator.getValue(x, z);
                cache.put(key, value);
                if (cache.size() > cacheSize) {
                    for (int i = 0; i < cacheSize / 16; ++i) {
                        cache.removeLastInt();
                    }
                }
                return value;
            }
        }
    }

    /**
     * Returns the cache size limit of this {@link LazyRegion}.
     */
    public int getMaxCacheSize() {
        return cacheSize;
    }

    /**
     * Packs x-z coordinates into a {@code long} value, as by:
     * <pre>
     * (x & 0xFFFFFFFFL) << 32 | z & 0xFFFFFFFFL
     * </pre>
     *
     * @param x X coordinate
     * @param z Z coordinate
     * @return The packed coordinates
     */
    private static long asLong(int x, int z) {
        return (x & 0xFFFFFFFFL) << 32 | z & 0xFFFFFFFFL;
    }
}
