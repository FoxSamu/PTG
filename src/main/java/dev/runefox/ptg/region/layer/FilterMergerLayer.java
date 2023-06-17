/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.Region;
import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface FilterMergerLayer extends MergerLayer {
    @Override
    default int generate(RegionRNG rng, Region regionA, Region regionB, int x, int z) {
        return generate(rng, regionA.getValue(x, z), regionB.getValue(x, z));
    }

    int generate(RegionRNG rng, int a, int b);
}
