/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.Region;
import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatFilterMergerLayer extends MergerLayer {
    @Override
    default int generate(RegionRNG rng, Region regionA, Region regionB, int x, int z) {
        return Float.floatToRawIntBits(generate(rng, regionA.getFPValue(x, z), regionB.getFPValue(x, z)));
    }

    float generate(RegionRNG rng, float a, float b);
}
