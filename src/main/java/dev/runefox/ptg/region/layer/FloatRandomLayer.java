/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

public class FloatRandomLayer implements FloatGeneratorLayer {
    private final RandomFunction function;

    public FloatRandomLayer(RandomFunction function) {
        this.function = function;
    }

    public FloatRandomLayer(float min, float max) {
        this(rng -> rng.randomFloat() * (max - min) + min);
    }

    public FloatRandomLayer(float[] floats) {
        this(rng -> rng.pickRandom(floats));
    }

    @Override
    public float generateFP(RegionRNG rng, int x, int z) {
        return function.random(rng);
    }

    @FunctionalInterface
    public interface RandomFunction {
        float random(RegionRNG rng);
    }
}
