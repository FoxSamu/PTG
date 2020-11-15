/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region;

import net.shadew.ptg.region.layer.GeneratorLayer;
import net.shadew.ptg.region.layer.MergerLayer;
import net.shadew.ptg.region.layer.TransformerLayer;

/**
 * A {@link RegionContext} implementation that uses {@link Region}s that cache their values: {@link LazyRegion}s.
 */
public class LazyRegionContext implements RegionContext<LazyRegion> {
    private final int initCacheSize;
    private final int cacheSizeMul;
    private final int cacheLimit;
    private final long worldSeed;

    /**
     * Creates a {@link LazyRegionContext} with default configuration.
     *
     * @param initCacheSize The initial cache size used when creating a generator region.
     * @param worldSeed     The world seed.
     */
    public LazyRegionContext(int initCacheSize, long worldSeed) {
        this(initCacheSize, 4, 1024, worldSeed);
    }

    /**
     * Creates a {@link LazyRegionContext} with custom configuration.
     *
     * @param initCacheSize The initial cache size used when creating a generator region.
     * @param cacheSizeMul  The cache size multiplier used when creating a transformer or merger region.
     * @param cacheLimit    The cache size limit. Computed cache sizes can't be more than this value.
     * @param worldSeed     The world seed.
     */
    public LazyRegionContext(int initCacheSize, int cacheSizeMul, int cacheLimit, long worldSeed) {
        this.initCacheSize = Math.min(cacheLimit, initCacheSize);
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
    public LazyRegion create(Region generator) {
        return new LazyRegion(generator, initCacheSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegion create(Region generator, LazyRegion region) {
        return new LazyRegion(generator, computeCacheSize(region.getMaxCacheSize()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegion create(Region generator, LazyRegion regionA, LazyRegion regionB) {
        int max = Math.max(regionA.getMaxCacheSize(), regionB.getMaxCacheSize());
        return new LazyRegion(generator, computeCacheSize(max));
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
    public LazyRegionBuilder extend(RegionFactory<LazyRegion> factory, long seed) {
        return new LazyRegionBuilder(this, factory, seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionBuilder extend(RegionFactory<LazyRegion> factory) {
        return extend(factory, worldSeed & 0xFFFF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionBuilder generate(GeneratorLayer layer, long seed) {
        return new LazyRegionBuilder(this, layer.factory(this, seed), seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionBuilder generate(GeneratorLayer layer) {
        return generate(layer, worldSeed & 0xFFFF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionBuilder transform(TransformerLayer layer, RegionFactory<LazyRegion> factory, long seed) {
        return new LazyRegionBuilder(this, layer.factory(this, seed, factory), seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionBuilder transform(TransformerLayer layer, RegionFactory<LazyRegion> factory) {
        return transform(layer, factory, worldSeed & 0xFFFF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionBuilder merge(MergerLayer layer, RegionFactory<LazyRegion> factoryA, RegionFactory<LazyRegion> factoryB, long seed) {
        return new LazyRegionBuilder(this, layer.factory(this, seed, factoryA, factoryB), seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionBuilder merge(MergerLayer layer, RegionFactory<LazyRegion> factoryA, RegionFactory<LazyRegion> factoryB) {
        return merge(layer, factoryA, factoryB, worldSeed & 0xFFFF);
    }
}
