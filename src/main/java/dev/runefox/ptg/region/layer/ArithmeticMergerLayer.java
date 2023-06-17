/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface ArithmeticMergerLayer extends FilterMergerLayer {
    int compute(int a, int b);

    @Override
    default int generate(RegionRNG rng, int a, int b) {
        return compute(a, b);
    }
}
