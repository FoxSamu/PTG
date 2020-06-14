/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.ptg.region;

import net.rgsw.ptg.region.layer.*;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A generic builder for {@link Region}s.
 *
 * @param <R> The fractal type.
 * @param <T> The type of this builder to return in each property setter.
 * @see CachingRegionBuilder
 */
public interface RegionBuilder<R extends Region, T extends RegionBuilder<R, T>> extends RegionFactory<R> {
    /**
     * @return The context this builder depends on
     */
    RegionContext<R> getContext();

    /**
     * @return The underlying {@link RegionFactory} chain
     */
    RegionFactory<R> getFactory();

    /**
     * {@inheritDoc}
     */
    @Override
    default R buildRegion() {
        return getFactory().buildRegion();
    }

    /**
     * Applies a factory function to the underlying {@link RegionFactory} chain.
     *
     * @param function The function to apply
     * @return This instance for chaining.
     */
    T apply( Function<RegionFactory<R>, RegionFactory<R>> function );

    /**
     * Sets the current seed.
     *
     * @param seed The new seed
     * @return This instance for chaining
     */
    T setSeed( long seed );

    /**
     * Computes the next, pseudorandom seed.
     *
     * @return The computed seed
     */
    long nextSeed();

    /**
     * Applies multiple {@linkplain ZoomLayer zoom layers} to the underlying {@link RegionFactory} chain.
     *
     * @param amount The amount of zoom layers to add
     * @return This instance for chaining
     *
     * @see #zoom()
     */
    default T zoom( int amount ) {
        return apply( factory -> ZoomLayer.INSTANCE.magnify( getContext(), nextSeed(), factory, amount ) );
    }

    /**
     * Applies a {@linkplain ZoomLayer zoom layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     *
     * @see #zoom(int)
     */
    default T zoom() {
        return apply( factory -> ZoomLayer.INSTANCE.factory( getContext(), nextSeed(), factory ) );
    }

    /**
     * Applies multiple {@linkplain FuzzyZoomLayer fuzzy zoom layers} to the underlying {@link RegionFactory} chain.
     *
     * @param amount The amount of fuzzy zoom layers to add
     * @return This instance for chaining
     *
     * @see #zoomFuzzy()
     */
    default T zoomFuzzy( int amount ) {
        return apply( factory -> FuzzyZoomLayer.INSTANCE.magnify( getContext(), nextSeed(), factory, amount ) );
    }

    /**
     * Applies a {@linkplain FuzzyZoomLayer fuzzy zoom layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     *
     * @see #zoomFuzzy(int)
     */
    default T zoomFuzzy() {
        return apply( factory -> FuzzyZoomLayer.INSTANCE.factory( getContext(), nextSeed(), factory ) );
    }

    /**
     * Applies a {@linkplain SmoothingLayer smoothing layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T smooth() {
        return apply( factory -> SmoothingLayer.INSTANCE.factory( getContext(), nextSeed(), factory ) );
    }

    /**
     * Applies a {@linkplain VoronoiZoomLayer voronoi zoom layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T zoomVoronoi() {
        return apply( factory -> VoronoiZoomLayer.INSTANCE.factory( getContext(), nextSeed(), factory ) );
    }

    /**
     * Applies a {@linkplain TransformerLayer transformer layer} to the underlying {@link RegionFactory} chain.
     *
     * @param layer The {@link TransformerLayer} to apply.
     * @return This instance for chaining
     */
    default T transform( TransformerLayer layer ) {
        return apply( factory -> layer.factory( getContext(), nextSeed(), factory ) );
    }

    /**
     * Applies a {@linkplain MergerLayer merger layer} to the underlying {@link RegionFactory} chain, using the
     * specified {@link RegionFactory} as second chain.
     *
     * @param layer The {@link MergerLayer} to apply.
     * @return This instance for chaining
     */
    default T merge( MergerLayer layer, RegionFactory<R> otherFactory ) {
        return apply( factory -> layer.factory( getContext(), nextSeed(), factory, otherFactory ) );
    }

    /**
     * Builds a {@link Region} and creates a {@link FractalGenerator} instance, which is then passed as parameter to the
     * specified consumer.
     *
     * @param consumer The consumer that handles the new {@link FractalGenerator} instance.
     * @return This instance for chaining
     */
    @SuppressWarnings( "unchecked" )
    default <D extends FractalGenerator<?>> T export( Consumer<? super D> consumer, Function<Region, ? extends D> factory ) {
        consumer.accept( makeGenerator( factory ) );
        return (T) this;
    }

    /**
     * Builds a {@link Region} and creates a {@link FractalGenerator} instance, which is then set in an array at the
     * specified index.
     *
     * @param arr   The array to set the new {@link FractalGenerator} instance in.
     * @param index The index in the array.
     * @return This instance for chaining
     *
     * @throws ArrayIndexOutOfBoundsException When the specified index is out of the array's bounds.
     */
    @SuppressWarnings( "unchecked" )
    default <D extends FractalGenerator<?>> T export( D[] arr, int index, Function<Region, ? extends D> factory ) {
        arr[ index ] = makeGenerator( factory );
        return (T) this;
    }
}
