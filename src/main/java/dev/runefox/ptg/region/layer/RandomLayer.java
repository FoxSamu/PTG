/*
 * Copyright 2020-2026 O. W. Nankman
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
 * AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

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
