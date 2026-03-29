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

package dev.runefox.ptg.region;

import dev.runefox.ptg.rng.FastLCG;
import dev.runefox.ptg.rng.RNG;
import dev.runefox.ptg.rng.RNGPrimer;

/**
 * A basic implementation of {@link RegionRNG} based on {@link FastLCG}.
 */
public class RegionLCG extends FastLCG implements RegionRNG {
    private final long baseSeed;

    private RegionLCG(long baseSeed) {
        super(baseSeed);
        this.baseSeed = baseSeed;
    }

    public RegionLCG(long worldSeed, long seed) {
        this(computeBaseSeed(worldSeed, seed));
    }

    private static long computeBaseSeed(long worldSeed, long seed) {
        long globalSeed = worldSeed;
        globalSeed *= scramble(globalSeed);
        globalSeed += worldSeed;
        globalSeed *= scramble(globalSeed);
        globalSeed += worldSeed;
        globalSeed *= scramble(globalSeed);
        globalSeed += worldSeed;

        long baseSeed = seed;
        baseSeed *= scramble(baseSeed);
        baseSeed += globalSeed;
        baseSeed *= scramble(baseSeed);
        baseSeed += globalSeed;
        baseSeed *= scramble(baseSeed);
        baseSeed += globalSeed;

        return baseSeed;
    }

    @Override
    public RegionRNG seedFromPosition(int x, int z) {
        long seed = baseSeed;
        seed *= scramble(seed);
        seed += x;
        seed *= scramble(seed);
        seed += z;
        seed *= scramble(seed);
        seed += x;
        seed *= scramble(seed);
        seed += z;
        super.seed(seed);
        return this;
    }

    @Override
    public void seed(long seed) {
        super.seed(scramble(seed ^ baseSeed));
    }

    @Override
    public RNG fork() {
        throw new UnsupportedOperationException("Forking is not supported on RegionRNG");
    }

    @Override
    public RNGPrimer forkPrimer() {
        throw new UnsupportedOperationException("Forking is not supported on RegionRNG");
    }
}
