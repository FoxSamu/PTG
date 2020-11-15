/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.RegionRNG;

public class InterpolateZoomLayer extends ZoomLayer {
    public static final InterpolateZoomLayer INTEGER = new InterpolateZoomLayer();
    public static final InterpolateZoomLayer FLOAT = new InterpolateZoomLayer() {
        @Override
        protected int pickRandom(RegionRNG rng, int a, int b, int c, int d) {
            return Float.floatToRawIntBits(
                (Float.intBitsToFloat(a)
                     + Float.intBitsToFloat(b)
                     + Float.intBitsToFloat(c)
                     + Float.intBitsToFloat(d)) / 4
            );
        }

        @Override
        protected int pickRandom(RegionRNG rng, int a, int b) {
            return Float.floatToRawIntBits(
                (Float.intBitsToFloat(a)
                     + Float.intBitsToFloat(b)) / 2
            );
        }
    };

    protected InterpolateZoomLayer() {
    }

    @Override
    protected int pickRandom(RegionRNG rng, int a, int b, int c, int d) {
        // avg(avg(a, b), avg(c, d)) = avg(a, b, c, d)
        return a + b + c + d >> 2; // x >> 2 = x / 4
    }

    @Override
    protected int pickRandom(RegionRNG rng, int a, int b) {
        return a + b >> 1; // x >> 1 = x / 2
    }
}
