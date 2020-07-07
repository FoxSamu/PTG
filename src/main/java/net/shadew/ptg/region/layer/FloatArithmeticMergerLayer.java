/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatArithmeticMergerLayer extends FloatFilterMergerLayer {
    float compute(float a, float b);

    @Override
    default float generate(RegionRNG rng, float a, float b) {
        return compute(a, b);
    }
}
