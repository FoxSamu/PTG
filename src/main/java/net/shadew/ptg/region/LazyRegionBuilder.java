/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region;


import java.util.function.Function;

import net.shadew.ptg.rng.LongScrambler;

/**
 * A builder for {@link LazyRegion}s, managed by a {@link LazyRegionContext}.
 */
public class LazyRegionBuilder implements RegionBuilder<LazyRegion, LazyRegionBuilder> {
    /** A default seed scrambler to compute a series of seeds for each applied transformation. */
    private static final LongScrambler DEFAULT_SCRAMBLER = LongScrambler.xorshift(13, -17, 5, -23, 19, -7).masked(0xFFFFL);

    /** The managing region context. */
    private final LazyRegionContext context;

    /** The built {@link RegionFactory} chain. */
    private RegionFactory<LazyRegion> factory;

    /** The current seed, which is scrambled by {@link #DEFAULT_SCRAMBLER} to create a series of random seeds. */
    private long seed;

    /**
     * Creates a caching region builder. This constructor must be used via one of:
     * <ul>
     *     <li>{@link LazyRegionContext#extend}</li>
     *     <li>{@link LazyRegionContext#generate}</li>
     *     <li>{@link LazyRegionContext#transform}</li>
     *     <li>{@link LazyRegionContext#merge}</li>
     * </ul>
     *
     * @param context  The managing region context that is used to create {@link LazyRegion}s.
     * @param root     The root factory. This is needed to build a factory chain and must not be null.
     * @param rootSeed The root seed to be scrambled.
     */
    LazyRegionBuilder(LazyRegionContext context, RegionFactory<LazyRegion> root, long rootSeed) {
        this.context = context;
        this.factory = root;
        this.seed = DEFAULT_SCRAMBLER.scramble(rootSeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionContext getContext() {
        return context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegionFactory<LazyRegion> getFactory() {
        return factory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionBuilder apply(Function<RegionFactory<LazyRegion>, RegionFactory<LazyRegion>> function) {
        factory = function.apply(factory);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LazyRegionBuilder setSeed(long seed) {
        this.seed = seed;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long nextSeed() {
        long out = seed;
        seed = DEFAULT_SCRAMBLER.scramble(seed);
        return out;
    }
}
