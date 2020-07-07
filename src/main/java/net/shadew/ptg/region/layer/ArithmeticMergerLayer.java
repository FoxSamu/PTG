/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface ArithmeticMergerLayer extends FilterMergerLayer {
    int compute(int a, int b);

    @Override
    default int generate(RegionRNG rng, int a, int b) {
        return compute(a, b);
    }
}
