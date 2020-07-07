/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface ArithmeticTransformerLayer extends FilterTransformerLayer {
    int compute(int value);

    @Override
    default int generate(RegionRNG rng, int value) {
        return compute(value);
    }
}
