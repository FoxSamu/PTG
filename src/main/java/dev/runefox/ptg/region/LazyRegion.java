/*
 * Copyright 2020-2026 O. W. Nankman
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
 * AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
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
