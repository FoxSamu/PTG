/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.Region;
import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatFilterTransformerLayer extends TransformerLayer {
    @Override
    default int generate(RegionRNG rng, Region region, int x, int z) {
        return Float.floatToRawIntBits(generate(rng, region.getFPValue(x, z)));
    }

    float generate(RegionRNG rng, float value);
}
