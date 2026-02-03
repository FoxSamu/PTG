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

import dev.runefox.ptg.rng.LongScrambler;

/**
 * A basic implementation of {@link RegionRNG}.
 */
public class LinearCongruentialRNG implements RegionRNG {

    private static final LongScrambler CONSTR_SCRAMBLE = LongScrambler.xorshift(13, -17, 5, -23, 19, -27, 7, -19);

    /**
     * The global seed. This seed is based on the world seed and is the same for all instances used in the same world.
     * This value does not change after initialization.
     */
    private long globalSeed;

    /**
     * The local seed. This seed is based on the {@linkplain #globalSeed global seed} and a seed for this instance. This
     * value does not change after initialization.
     */
    private long localSeed;

    /**
     * The position-local seed. This seed is scrambled and used to generate random values. The inital value is based on
     * the {@linkplain #localSeed local seed} and an XZ coordinate pair. This seed can be reinitialized using {@link
     * #setPosition}.
     */
    private long currentSeed;

    public LinearCongruentialRNG(long worldSeed, long seed) {
        seed = CONSTR_SCRAMBLE.scramble(seed);
        initWorldSeed(worldSeed);
        initLocalSeed(seed);
    }

    private void initWorldSeed(long seed) {
        globalSeed = seed;
        globalSeed *= scramble(globalSeed);
        globalSeed += seed;
        globalSeed *= scramble(globalSeed);
        globalSeed += seed;
        globalSeed *= scramble(globalSeed);
        globalSeed += seed;
    }

    private void initLocalSeed(long seed) {
        localSeed = seed;
        localSeed *= scramble(localSeed);
        localSeed += globalSeed;
        localSeed *= scramble(localSeed);
        localSeed += globalSeed;
        localSeed *= scramble(localSeed);
        localSeed += globalSeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(long x, long z) {
        currentSeed = localSeed;
        currentSeed *= scramble(currentSeed);
        currentSeed += x;
        currentSeed *= scramble(currentSeed);
        currentSeed += z;
        currentSeed *= scramble(currentSeed);
        currentSeed += x;
        currentSeed *= scramble(currentSeed);
        currentSeed += z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int random(int bound) {
        int rand = (int) ((currentSeed >> 24) % bound);
        if (rand < 0) rand += bound;

        currentSeed *= scramble(currentSeed);
        currentSeed += localSeed;
        return rand;
    }

    /**
     * Simple LGC scramble operation.
     */
    private static long scramble(long value) {
        return value * 6364136223846793005L + 1442695040888963407L;
    }
}
