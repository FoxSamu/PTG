/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

public class UnpackFloatLayer implements FilterTransformerLayer {
    private final int precision;
    private final float min;
    private final float max;

    public UnpackFloatLayer(int precision, float min, float max) {
        this.precision = precision;
        this.min = min;
        this.max = max;
    }

    @Override
    public int generate(RegionRNG rng, int value) {
        float t = value / (float) (precision - 1);
        return Float.floatToRawIntBits(t * (max - min) + min);
    }
}
