/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.rng;

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
    int scramble( int seed );

    /**
     * Creates a basic linear congruential (LGC) scrambler:
     * <pre>seed -> seed * mul + add</pre>
     *
     * @param add The addend constant.
     * @param mul The multiplier constant.
     * @return A LGC long scrambler
     */
    static IntScrambler lgc( int add, int mul ) {
        return seed -> seed * mul + add;
    }
}
