/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.Region;
import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface FilterTransformerLayer extends TransformerLayer {
    @Override
    default int generate(RegionRNG rng, Region region, int x, int z) {
        return generate(rng, region.getValue(x, z));
    }

    int generate(RegionRNG rng, int value);
}
