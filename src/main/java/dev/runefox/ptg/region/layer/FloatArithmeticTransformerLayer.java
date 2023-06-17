/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatArithmeticTransformerLayer extends FloatFilterTransformerLayer {
    float compute(float value);

    @Override
    default float generate(RegionRNG rng, float value) {
        return compute(value);
    }
}
