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
 * A long scrambler scrambles long values. This function returns a pseudo-random long value based on the input seed. A
 * long scrambler must be defined so that every input value has only one unique output value. Two input values may have
 * the same output value: this means that not all possible output values are associated with an input value.
 */
@FunctionalInterface
public interface LongScrambler {
    /**
     * The identity long scrambler. This returns the input value as output value directly, without any calculations.
     */
    LongScrambler IDENTITY = seed -> seed;

    /**
     * Scrambles a long value.
     *
     * @param seed The input value: a 'seed'.
     * @return The output value.
     */
    long scramble(long seed);

    /**
     * Creates a basic linear congruential (LGC) scrambler:
     * <pre>seed -> seed * mul + add</pre>
     *
     * @param add The addend constant.
     * @param mul The multiplier constant.
     * @return A LGC long scrambler
     */
    static LongScrambler lgc(long add, long mul) {
        return seed -> seed * mul + add;
    }

    static LongScrambler xorshift(int... leftShifts) {
        if (leftShifts == null || leftShifts.length == 0) throw new IllegalArgumentException();
        return seed -> {
            for (int ls : leftShifts) {
                if (ls < 0) seed ^= seed >> -ls;
                else seed ^= seed << ls;
            }
            return seed;
        };
    }

    static LongScrambler xorshift(long... seeds) {
        if (seeds == null || seeds.length == 0) throw new IllegalArgumentException();
        int[] intSeeds = new int[seeds.length * 8];
        int count = 0;
        for (long seed : seeds) {
            int a = (int) (seed & 0xFFFFL);
            int b = (int) (seed >>> 16 & 0xFFFFL);
            int c = (int) (seed >>> 32 & 0xFFFFL);
            int d = (int) (seed >>> 48 & 0xFFFFL);
            if (a != 0) {
                intSeeds[count] = a;
                count++;
            }
            if (b != 0) {
                intSeeds[count] = b;
                count++;
            }
            if (c != 0) {
                intSeeds[count] = c;
                count++;
            }
            if (d != 0) {
                intSeeds[count] = d;
                count++;
            }
        }

        int[] shifts = new int[count];
        int sign = 1;
        for (int i = 0; i < count; i++) {
            int seed = intSeeds[i];
            sign = -sign;
            int index = seed % PrimeNumbers.RANDOM_PRIMES.length;
            shifts[i] = sign * PrimeNumbers.RANDOM_PRIMES[index];
        }

        return xorshift(shifts);
    }

    /**
     * Creates a hash scrambler. A hash scrambler left-shifts the input value and applies an x-or operation for each
     * passed seed. The bits that get lost by the shift operation are used as multiplier for a final LGC operation. An
     * additional sign-flip is done for each seed.
     *
     * @param shift  The shift mask: left-shifts are not larger than this value.
     * @param lgcAdd The addend of the final LGC operation.
     * @param seeds  A couple of random seeds used to scramble the input value. At least one seed is required.
     * @return A long scrambler that uses an advanced hash function to scramble longs.
     *
     * @throws IllegalArgumentException Thrown when the passed seeds array is null or empty.
     */
    static LongScrambler hash(int shift, long lgcAdd, long... seeds) {
        if (seeds == null || seeds.length == 0) throw new IllegalArgumentException();
        long initLGCMulMask = (1L << shift) - 1;
        return seed -> {
            long lgcMul = ~seeds[0] & initLGCMulMask;
            for (long s : seeds) {
                // Shift size
                int sh = (int) (seed % shift);

                // Compute the lost bits by shifting left and right.
                long lost = seed << sh >>> sh;
                lgcMul ^= lost;


                seed >>= sh;
                seed ^= s;

                if ((seed & 1) == 0) {
                    seed = ~seed;
                }

                seed ^= lost;
            }
            return seed * lgcMul * 31 + lgcAdd;
        };
    }

    default LongScrambler masked(long mask) {
        return s -> scramble(s) & mask;
    }
}
