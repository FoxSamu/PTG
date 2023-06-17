/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.Region;
import dev.runefox.ptg.region.RegionContext;
import dev.runefox.ptg.region.RegionFactory;
import dev.runefox.ptg.region.RegionRNG;

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
