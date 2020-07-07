/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatGeneratorLayer extends GeneratorLayer {
    @Override
    default int generate(RegionRNG rng, int x, int z) {
        return Float.floatToRawIntBits(generateFP(rng, x, z));
    }

    float generateFP(RegionRNG rng, int x, int z);
}
