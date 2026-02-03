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

package dev.runefox.ptg.rng;

/**
 * An int scrambler scrambles int values. This function returns a pseudo-random int value based on the input seed. An
 * int scrambler must be defined so that every input value has only one unique output value. Two input values may have
 * the same output value: this means that not all possible output values are associated with an input value.
 */
@FunctionalInterface
public interface IntScrambler {
    /**
     * The identity int scrambler. This returns the input value as output value directly, without any calculations.
     */
    IntScrambler IDENTITY = seed -> seed;

    /**
     * Scrambles an int value.
     *
     * @param seed The input value: a 'seed'.
     * @return The output value.
     */
    int scramble(int seed);

    /**
     * Creates a basic linear congruential (LGC) scrambler:
     * <pre>seed -> seed * mul + add</pre>
     *
     * @param add The addend constant.
     * @param mul The multiplier constant.
     * @return A LGC long scrambler
     */
    static IntScrambler lgc(int add, int mul) {
        return seed -> seed * mul + add;
    }
}
