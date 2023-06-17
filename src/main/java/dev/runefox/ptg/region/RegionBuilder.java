/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region;

import dev.runefox.ptg.region.layer.*;
import dev.runefox.ptg.region.util.FloatSelector;
import dev.runefox.ptg.region.util.IntSelector;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A generic builder for {@link Region}s.
 *
 * @param <R> The fractal type.
 * @param <T> The type of this builder to return in each property setter.
 * @see LazyRegionBuilder
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
    T apply(Function<RegionFactory<R>, RegionFactory<R>> function);

    /**
     * Sets the current seed.
     *
     * @param seed The new seed
     * @return This instance for chaining
     */
    T setSeed(long seed);

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
    default T zoom(int amount) {
        return apply(factory -> ZoomLayer.INSTANCE.magnify(getContext(), nextSeed(), factory, amount));
    }

    /**
     * Applies a {@linkplain ZoomLayer zoom layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     *
     * @see #zoom(int)
     */
    default T zoom() {
        return apply(factory -> ZoomLayer.INSTANCE.factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies multiple {@linkplain FuzzyZoomLayer fuzzy zoom layers} to the underlying {@link RegionFactory} chain.
     *
     * @param amount The amount of fuzzy zoom layers to add
     * @return This instance for chaining
     *
     * @see #zoomFuzzy()
     */
    default T zoomFuzzy(int amount) {
        return apply(factory -> FuzzyZoomLayer.INSTANCE.magnify(getContext(), nextSeed(), factory, amount));
    }

    /**
     * Applies a {@linkplain FuzzyZoomLayer fuzzy zoom layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     *
     * @see #zoomFuzzy(int)
     */
    default T zoomFuzzy() {
        return apply(factory -> FuzzyZoomLayer.INSTANCE.factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies multiple {@linkplain InterpolateZoomLayer interpolating zoom layers} to the underlying {@link
     * RegionFactory} chain.
     *
     * @param amount The amount of zoom layers to add
     * @return This instance for chaining
     *
     * @see #zoomInterp()
     */
    default T zoomInterp(int amount) {
        return apply(factory -> InterpolateZoomLayer.INTEGER.magnify(getContext(), nextSeed(), factory, amount));
    }

    /**
     * Applies a {@linkplain InterpolateZoomLayer interpolating zoom layer} to the underlying {@link RegionFactory}
     * chain.
     *
     * @return This instance for chaining
     *
     * @see #zoomInterp(int)
     */
    default T zoomInterp() {
        return apply(factory -> InterpolateZoomLayer.INTEGER.factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies multiple {@linkplain InterpolateZoomLayer interpolating zoom layers} to the underlying {@link
     * RegionFactory} chain, considering floating point representations instead of integers.
     *
     * @param amount The amount of zoom layers to add
     * @return This instance for chaining
     *
     * @see #zoomInterpF()
     */
    default T zoomInterpF(int amount) {
        return apply(factory -> InterpolateZoomLayer.FLOAT.magnify(getContext(), nextSeed(), factory, amount));
    }

    /**
     * Applies a {@linkplain InterpolateZoomLayer interpolating zoom layer} to the underlying {@link RegionFactory}
     * chain, considering floating point representations instead of integers.
     *
     * @return This instance for chaining
     *
     * @see #zoomInterpF(int)
     */
    default T zoomInterpF() {
        return apply(factory -> InterpolateZoomLayer.FLOAT.factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies a {@linkplain SmoothingLayer smoothing layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T smooth() {
        return apply(factory -> SmoothingLayer.INSTANCE.factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies a {@linkplain VoronoiZoomLayer voronoi zoom layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T zoomVoronoi() {
        return apply(factory -> VoronoiZoomLayer.INSTANCE.factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies a {@linkplain PackFloatLayer float packing layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T packFP(int precision, float min, float max) {
        return apply(factory -> new PackFloatLayer(precision, min, max).factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies a {@linkplain PackFloatLayer float packing layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T packFP(int precision) {
        return apply(factory -> new PackFloatLayer(precision, 0, 1).factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies a {@linkplain UnpackFloatLayer float unpacking layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T unpackFP(int precision, float min, float max) {
        return apply(factory -> new UnpackFloatLayer(precision, min, max).factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies a {@linkplain UnpackFloatLayer float unpacking layer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T unpackFP(int precision) {
        return apply(factory -> new UnpackFloatLayer(precision, 0, 1).factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies a {@link ReplaceLayer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T replace(IntSelector sel, int value) {
        return transform(new ReplaceLayer(sel, value));
    }

    /**
     * Applies a {@link ReplaceLayer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T replace(int sel, int value) {
        return transform(new ReplaceLayer(sel, value));
    }

    /**
     * Applies a {@link ReplaceLayer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T replaceF(FloatSelector sel, float value) {
        return transform(new ReplaceLayer(sel, value));
    }

    /**
     * Applies a {@link ReplaceLayer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T replaceF(float sel, float value) {
        return transform(new ReplaceLayer(sel, value));
    }

    /**
     * Applies an {@link ArithmeticTransformerLayer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T arithmetic(ArithmeticTransformerLayer layer) {
        return transform(layer);
    }

    /**
     * Applies a {@link FloatArithmeticTransformerLayer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T arithmeticF(FloatArithmeticTransformerLayer layer) {
        return transform(layer);
    }

    /**
     * Applies an {@link OutlineLayer} to the underlying {@link RegionFactory} chain.
     *
     * @return This instance for chaining
     */
    default T outline() {
        return transform(OutlineLayer.INSTANCE);
    }

    /**
     * Applies a {@linkplain TransformerLayer transformer layer} to the underlying {@link RegionFactory} chain.
     *
     * @param layer The {@link TransformerLayer} to apply.
     * @return This instance for chaining
     */
    default T transform(TransformerLayer layer) {
        return apply(factory -> layer.factory(getContext(), nextSeed(), factory));
    }

    /**
     * Applies a {@link ReplaceMergeLayer} to the underlying {@link RegionFactory} chain, using the specified {@link
     * RegionFactory} as second chain.
     *
     * @return This instance for chaining
     */
    default T replace(IntSelector sel, RegionFactory<R> other) {
        return merge(new ReplaceMergeLayer(sel), other);
    }

    /**
     * Applies a {@link ReplaceMergeLayer} to the underlying {@link RegionFactory} chain, using the specified {@link
     * RegionFactory} as second chain.
     *
     * @return This instance for chaining
     */
    default T replace(int sel, RegionFactory<R> other) {
        return merge(new ReplaceMergeLayer(sel), other);
    }

    /**
     * Applies a {@link ReplaceMergeLayer} to the underlying {@link RegionFactory} chain, using the specified {@link
     * RegionFactory} as second chain.
     *
     * @return This instance for chaining
     */
    default T replaceF(FloatSelector sel, RegionFactory<R> other) {
        return merge(new ReplaceMergeLayer(sel), other);
    }

    /**
     * Applies a {@link ReplaceMergeLayer} to the underlying {@link RegionFactory} chain, using the specified {@link
     * RegionFactory} as second chain.
     *
     * @return This instance for chaining
     */
    default T replaceF(float sel, RegionFactory<R> other) {
        return merge(new ReplaceMergeLayer(sel), other);
    }

    /**
     * Applies an {@link ArithmeticMergerLayer} to the underlying {@link RegionFactory} chain, using the specified
     * {@link RegionFactory} as second chain.
     *
     * @return This instance for chaining
     */
    default T arithmetic(ArithmeticMergerLayer layer, RegionFactory<R> otherFactory) {
        return merge(layer, otherFactory);
    }

    /**
     * Applies a {@link FloatArithmeticMergerLayer} to the underlying {@link RegionFactory} chain, using the specified
     * {@link RegionFactory} as second chain.
     *
     * @return This instance for chaining
     */
    default T arithmeticF(FloatArithmeticMergerLayer layer, RegionFactory<R> otherFactory) {
        return merge(layer, otherFactory);
    }

    /**
     * Applies a {@linkplain MergerLayer merger layer} to the underlying {@link RegionFactory} chain, using the
     * specified {@link RegionFactory} as second chain.
     *
     * @param layer The {@link MergerLayer} to apply.
     * @return This instance for chaining
     */
    default T merge(MergerLayer layer, RegionFactory<R> otherFactory) {
        return apply(factory -> layer.factory(getContext(), nextSeed(), factory, otherFactory));
    }

    /**
     * Builds a {@link Region} and creates a {@link FractalGenerator} instance, which is then passed as parameter to the
     * specified consumer.
     *
     * @param consumer The consumer that handles the new {@link FractalGenerator} instance.
     * @return This instance for chaining
     */
    @SuppressWarnings("unchecked")
    default <D extends FractalGenerator<?>> T export(Consumer<? super D> consumer, Function<Region, ? extends D> factory) {
        consumer.accept(makeGenerator(factory));
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
    @SuppressWarnings("unchecked")
    default <D extends FractalGenerator<?>> T export(D[] arr, int index, Function<Region, ? extends D> factory) {
        arr[index] = makeGenerator(factory);
        return (T) this;
    }
}
