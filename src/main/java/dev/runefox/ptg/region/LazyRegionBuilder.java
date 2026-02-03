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


import dev.runefox.ptg.rng.LongScrambler;

import java.util.function.Function;

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
