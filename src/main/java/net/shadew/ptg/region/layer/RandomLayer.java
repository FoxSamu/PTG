/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.RegionRNG;

public class RandomLayer implements GeneratorLayer {
    private final RandomFunction function;

    public RandomLayer(RandomFunction function) {
        this.function = function;
    }

    public RandomLayer(int min, int max) {
        this(rng -> rng.random(max - min + 1) + min);
    }

    public RandomLayer(int[] ints) {
        this(rng -> rng.pickRandom(ints));
    }

    public RandomLayer(double oneChance) {
        this(rng -> rng.randomDouble() < oneChance ? 1 : 0);
    }

    @Override
    public int generate(RegionRNG rng, int x, int z) {
        return function.random(rng);
    }

    @FunctionalInterface
    public interface RandomFunction {
        int random(RegionRNG rng);
    }
}
