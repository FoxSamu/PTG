/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface ArithmeticTransformerLayer extends FilterTransformerLayer {
    int compute(int value);

    @Override
    default int generate(RegionRNG rng, int value) {
        return compute(value);
    }
}
