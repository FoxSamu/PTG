/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.Region;
import net.shadew.ptg.region.RegionContext;
import net.shadew.ptg.region.RegionFactory;
import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface TransformerLayer {
    int generate(RegionRNG rng, Region region, int x, int z);

    default <R extends Region> RegionFactory<R> factory(RegionContext<R> ctx, long seed, RegionFactory<R> regionFactory) {
        return () -> {
            RegionRNG rng = ctx.getRNG(seed);
            R region = regionFactory.buildRegion();
            return ctx.create((x, z) -> generate(rng.position(x, z), region, x, z), region);
        };
    }
}
