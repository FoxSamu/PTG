/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.noise.Noise2D;
import dev.runefox.ptg.region.RegionRNG;

public class NoiseLayer implements FloatGeneratorLayer {
    private final Noise2D noise;

    public NoiseLayer(Noise2D noise) {
        this.noise = noise;
    }

    @Override
    public float generateFP(RegionRNG rng, int x, int z) {
        return (float) noise.generate(x, z);
    }
}
