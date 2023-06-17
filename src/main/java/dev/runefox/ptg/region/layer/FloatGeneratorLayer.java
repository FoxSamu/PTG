/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatGeneratorLayer extends GeneratorLayer {
    @Override
    default int generate(RegionRNG rng, int x, int z) {
        return Float.floatToRawIntBits(generateFP(rng, x, z));
    }

    float generateFP(RegionRNG rng, int x, int z);
}
