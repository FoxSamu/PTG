/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.Region;
import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface FilterMergerLayer extends MergerLayer {
    @Override
    default int generate(RegionRNG rng, Region regionA, Region regionB, int x, int z) {
        return generate(rng, regionA.getValue(x, z), regionB.getValue(x, z));
    }

    int generate(RegionRNG rng, int a, int b);
}
