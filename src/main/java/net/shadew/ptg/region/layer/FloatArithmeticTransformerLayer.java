/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatArithmeticTransformerLayer extends FloatFilterTransformerLayer {
    float compute(float value);

    @Override
    default float generate(RegionRNG rng, float value) {
        return compute(value);
    }
}
