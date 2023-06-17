/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.Region;
import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatMergerLayer extends MergerLayer {
    @Override
    default int generate(RegionRNG rng, Region regionA, Region regionB, int x, int z) {
        return Float.floatToRawIntBits(generateFP(rng, regionA, regionB, x, z));
    }

    float generateFP(RegionRNG rng, Region regionA, Region regionB, int x, int z);
}
