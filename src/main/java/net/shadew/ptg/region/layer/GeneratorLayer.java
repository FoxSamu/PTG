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
public interface GeneratorLayer {
    int generate(RegionRNG rng, int x, int z);

    default <R extends Region> RegionFactory<R> factory(RegionContext<R> ctx, long seed) {
        return () -> {
            RegionRNG rng = ctx.getRNG(seed);
            return ctx.create((x, z) -> generate(rng.position(x, z), x, z));
        };
    }
}
