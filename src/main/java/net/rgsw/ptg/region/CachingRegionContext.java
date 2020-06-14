/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.ptg.region;

import net.rgsw.ptg.region.layer.GeneratorLayer;
import net.rgsw.ptg.region.layer.MergerLayer;
import net.rgsw.ptg.region.layer.TransformerLayer;

/**
 * A {@link RegionContext} implementation that uses {@link Region}s that cache their values: {@link CachingRegion}s.
 */
public class CachingRegionContext implements RegionContext<CachingRegion> {
    private final int initCacheSize;
    private final int cacheSizeMul;
    private final int cacheLimit;
    private final long worldSeed;

    /**
     * Creates a {@link CachingRegionContext} with default configuration.
     *
     * @param initCacheSize The initial cache size used when creating a generator region.
     * @param worldSeed     The world seed.
     */
    public CachingRegionContext( int initCacheSize, long worldSeed ) {
        this( initCacheSize, 4, 1024, worldSeed );
    }

    /**
     * Creates a {@link CachingRegionContext} with custom configuration.
     *
     * @param initCacheSize The initial cache size used when creating a generator region.
     * @param cacheSizeMul  The cache size multiplier used when creating a transformer or merger region.
     * @param cacheLimit    The cache size limit. Computed cache sizes can't be more than this value.
     * @param worldSeed     The world seed.
     */
    public CachingRegionContext( int initCacheSize, int cacheSizeMul, int cacheLimit, long worldSeed ) {
        this.initCacheSize = Math.min( cacheLimit, initCacheSize );
        this.cacheSizeMul = cacheSizeMul;
        this.cacheLimit = cacheLimit;
        this.worldSeed = worldSeed;
    }

    /**
     * Returns the initial cache size for generator regions.
     */
    public int getInitialCacheSize() {
        return initCacheSize;
    }

    /**
     * Returns the cache size multiplier for transformer regions. The cache size of a transformer region is computed by
     * multiplying the cache size of the underlying region with this value.
     */
    public int getCacheSizeMultiplier() {
        return cacheSizeMul;
    }

    /**
     * Returns the maximum cache size. Computed cache sizes can't be more than this value.
     */
    public int getCacheSizeLimit() {
        return cacheLimit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegion create( Region generator ) {
        return new CachingRegion( generator, initCacheSize );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegion create( Region generator, CachingRegion region ) {
        return new CachingRegion( generator, computeCacheSize( region.getMaxCacheSize() ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegion create( Region generator, CachingRegion regionA, CachingRegion regionB ) {
        int max = Math.max( regionA.getMaxCacheSize(), regionB.getMaxCacheSize() );
        return new CachingRegion( generator, computeCacheSize( max ) );
    }

    private int computeCacheSize( int size ) {
        return Math.min( cacheLimit, size * cacheSizeMul );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long worldSeed() {
        return worldSeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegionRNG getRNG( long seed ) {
        return new LinearCongruentialRNG( worldSeed, seed );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder extend( RegionFactory<CachingRegion> factory, long seed ) {
        return new CachingRegionBuilder( this, factory, seed );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder extend( RegionFactory<CachingRegion> factory ) {
        return extend( factory, worldSeed & 0xFFFF );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder generate( GeneratorLayer layer, long seed ) {
        return new CachingRegionBuilder( this, layer.factory( this, seed ), seed );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder generate( GeneratorLayer layer ) {
        return generate( layer, worldSeed & 0xFFFF );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder transform( TransformerLayer layer, RegionFactory<CachingRegion> factory, long seed ) {
        return new CachingRegionBuilder( this, layer.factory( this, seed, factory ), seed );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder transform( TransformerLayer layer, RegionFactory<CachingRegion> factory ) {
        return transform( layer, factory, worldSeed & 0xFFFF );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder merge( MergerLayer layer, RegionFactory<CachingRegion> factoryA, RegionFactory<CachingRegion> factoryB, long seed ) {
        return new CachingRegionBuilder( this, layer.factory( this, seed, factoryA, factoryB ), seed );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder merge( MergerLayer layer, RegionFactory<CachingRegion> factoryA, RegionFactory<CachingRegion> factoryB ) {
        return merge( layer, factoryA, factoryB, worldSeed & 0xFFFF );
    }
}
