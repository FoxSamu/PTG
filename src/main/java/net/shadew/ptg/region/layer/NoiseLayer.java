/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.noise.Noise2D;
import net.shadew.ptg.region.RegionRNG;

public class NoiseLayer implements FloatGeneratorLayer {
    private final Noise2D noise;

    public NoiseLayer(Noise2D noise) {
        this.noise = noise;
    }

    @Override
    public float generateFP(RegionRNG rng, int x, int z) {
        return (float) noise.generate( x, z );
    }
}
