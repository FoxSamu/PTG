/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

public class StaticValueLayer implements GeneratorLayer {
    private final int value;

    public StaticValueLayer(int value) {
        this.value = value;
    }

    public StaticValueLayer(float value) {
        this.value = Float.floatToRawIntBits(value);
    }

    @Override
    public int generate(RegionRNG rng, int x, int z) {
        return value;
    }
}
