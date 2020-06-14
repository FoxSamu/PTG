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
import net.rgsw.ptg.region.layer.RandomLayer;

/**
 * A generic region building context.
 *
 * @param <R> The region type this context handles.
 * @see CachingRegionContext
 */
public interface RegionContext<R extends Region> {

    /**
     * Creates a wrapping generator {@link Region}.
     *
     * @param generator The underlying {@link Region} generator.
     * @return The created {@link Region}
     */
    R create( Region generator );

    /**
     * Creates a wrapping transformer {@link Region}.
     *
     * @param generator The underlying {@link Region} generator.
     * @param region    The {@link Region} that's being transformed.
     * @return The created {@link Region}
     */
    R create( Region generator, R region );

    /**
     * Creates a wrapping merger {@link Region}.
     *
     * @param generator The underlying {@link Region} generator.
     * @param regionA   The first {@link Region} that's being merged.
     * @param regionB   The second {@link Region} that's being merged.
     * @return The created {@link Region}
     */
    R create( Region generator, R regionA, R regionB );

    /**
     * Returns the world seed this context relies on, which is used to create {@link Region} factories.
     *
     * @return The world seed.
     */
    long worldSeed();

    /**
     * Creates a {@linkplain RegionRNG fast random number generator} instance for the specified seed.
     *
     * @param seed The seed for this instance.
     * @return The created {@link RegionRNG} instance.
     */
    RegionRNG getRNG( long seed );

    /**
     * Creates a {@link RegionBuilder} by extending the specified {@link RegionFactory}.
     *
     * @param factory The factory to extend.
     * @param seed    The seed to use.
     * @return The created {@link RegionBuilder}.
     *
     * @see #extend(RegionFactory)
     */
    RegionBuilder<R, ?> extend( RegionFactory<R> factory, long seed );

    /**
     * Creates a {@link RegionBuilder} by extending the specified {@link RegionFactory}. The seed is computed from the
     * world seed.
     *
     * @param factory The factory to extend.
     * @return The created {@link RegionBuilder}.
     *
     * @see #extend(RegionFactory, long)
     */
    default RegionBuilder<R, ?> extend( RegionFactory<R> factory ) {
        return extend( factory, worldSeed() & 0xFFFF );
    }

    /**
     * Creates a {@link RegionBuilder} by creating a {@link RegionFactory} from the specified {@link GeneratorLayer}.
     *
     * @param layer The layer to create factory from.
     * @param seed  The seed to use.
     * @return The created {@link RegionBuilder}.
     *
     * @see #generate(GeneratorLayer)
     */
    default RegionBuilder<R, ?> generate( GeneratorLayer layer, long seed ) {
        return extend( layer.factory( this, seed ), seed );
    }

    /**
     * Creates a {@link RegionBuilder} by creating a {@link RegionFactory} from the specified {@link GeneratorLayer}.
     * The seed is computed from the world seed.
     *
     * @param layer The layer to create factory from.
     * @return The created {@link RegionBuilder}.
     *
     * @see #generate(GeneratorLayer, long)
     */
    default RegionBuilder<R, ?> generate( GeneratorLayer layer ) {
        return generate( layer, worldSeed() & 0xFFFF );
    }

    /**
     * Creates a {@link RegionBuilder} by transforming a {@link RegionFactory} using the specified {@link
     * TransformerLayer}.
     *
     * @param layer   The layer used to transform the specified factory.
     * @param factory The factory to transform.
     * @param seed    The seed to use.
     * @return The created {@link RegionBuilder}.
     *
     * @see #transform(TransformerLayer, RegionFactory)
     */
    default RegionBuilder<R, ?> transform( TransformerLayer layer, RegionFactory<R> factory, long seed ) {
        return extend( layer.factory( this, seed, factory ), seed );
    }

    /**
     * Creates a {@link RegionBuilder} by transforming a {@link RegionFactory} using the specified {@link
     * TransformerLayer}. The seed is computed from the world seed.
     *
     * @param layer   The layer used to transform the specified factory.
     * @param factory The factory to transform.
     * @return The created {@link RegionBuilder}.
     *
     * @see #transform(TransformerLayer, RegionFactory, long)
     */
    default RegionBuilder<R, ?> transform( TransformerLayer layer, RegionFactory<R> factory ) {
        return transform( layer, factory, worldSeed() & 0xFFFF );
    }

    /**
     * Creates a {@link RegionBuilder} by merging two {@link RegionFactory}s using the specified {@link MergerLayer}.
     *
     * @param layer    The layer used to merge the specified factories.
     * @param factoryA The first factory to transform.
     * @param factoryB The second factory to transform.
     * @param seed     The seed to use.
     * @return The created {@link RegionBuilder}.
     *
     * @see #merge(MergerLayer, RegionFactory, RegionFactory)
     */
    default RegionBuilder<R, ?> merge( MergerLayer layer, RegionFactory<R> factoryA, RegionFactory<R> factoryB, long seed ) {
        return extend( layer.factory( this, seed, factoryA, factoryB ), seed );
    }

    /**
     * Creates a {@link RegionBuilder} by merging two {@link RegionFactory}s using the specified {@link MergerLayer}.
     * The seed is computed from the world seed.
     *
     * @param layer    The layer used to merge the specified factories.
     * @param factoryA The first factory to transform.
     * @param factoryB The second factory to transform.
     * @return The created {@link RegionBuilder}.
     *
     * @see #merge(MergerLayer, RegionFactory, RegionFactory, long)
     */
    default RegionBuilder<R, ?> merge( MergerLayer layer, RegionFactory<R> factoryA, RegionFactory<R> factoryB ) {
        return merge( layer, factoryA, factoryB, worldSeed() & 0xFFFF );
    }

    default RegionBuilder<R, ?> random( RandomLayer.RandomFunction func, long seed ) {
        return generate( new RandomLayer( func ), seed );
    }

    default RegionBuilder<R, ?> random( RandomLayer.RandomFunction func ) {
        return generate( new RandomLayer( func ) );
    }

    default RegionBuilder<R, ?> random( int min, int max, long seed ) {
        return generate( new RandomLayer( min, max ), seed );
    }

    default RegionBuilder<R, ?> random( int min, int max ) {
        return generate( new RandomLayer( min, max ) );
    }

    default RegionBuilder<R, ?> binary( double oneChance, long seed ) {
        return generate( new RandomLayer( oneChance ), seed );
    }

    default RegionBuilder<R, ?> binary( double oneChance ) {
        return generate( new RandomLayer( oneChance ) );
    }

    default RegionBuilder<R, ?> pick( int[] ints, long seed ) {
        return generate( new RandomLayer( ints ), seed );
    }

    default RegionBuilder<R, ?> pick( int[] ints ) {
        return generate( new RandomLayer( ints ) );
    }
}
