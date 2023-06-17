/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatArithmeticMergerLayer extends FloatFilterMergerLayer {
    float compute(float a, float b);

    @Override
    default float generate(RegionRNG rng, float a, float b) {
        return compute(a, b);
    }
}
