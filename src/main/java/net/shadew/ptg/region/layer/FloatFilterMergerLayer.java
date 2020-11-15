/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.Region;
import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatFilterMergerLayer extends MergerLayer {
    @Override
    default int generate(RegionRNG rng, Region regionA, Region regionB, int x, int z) {
        return Float.floatToRawIntBits(generate(rng, regionA.getFPValue(x, z), regionB.getFPValue(x, z)));
    }

    float generate(RegionRNG rng, float a, float b);
}
