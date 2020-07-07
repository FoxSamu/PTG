/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region;


import java.util.function.Function;

/**
 * A region factory builds {@link Region} instances. These factories are usually wrapped by other factories that apply
 * transformations to the {@link Region}s they build, forming a factory chain. A {@link RegionBuilder} is used to build
 * the factory chain and also implements this interface.
 *
 * @param <R> The region type this factory builds.
 */
@FunctionalInterface
public interface RegionFactory<R extends Region> {
    /**
     * Builds a {@link Region}.
     *
     * @return The built region.
     */
    R buildRegion();

    /**
     * Makes a {@link FractalGenerator} from a built {@link Region}.
     *
     * @return The created {@link FractalGenerator} instance.
     */
    default <D extends FractalGenerator<?>> D makeGenerator( Function<Region, ? extends D> factory ) {
        return factory.apply( buildRegion() );
    }
}
