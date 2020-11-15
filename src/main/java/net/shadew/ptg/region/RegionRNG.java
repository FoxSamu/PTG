/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region;

import java.util.Random;

/**
 * An implementation of a pseudorandom number generator. Such implementations are usually made to be faster than {@link
 * Random} because they must generate a lot of pseudorandom numbers and do not need to be safe and accurate.
 */
public interface RegionRNG {
    /**
     * Recomputes the seed of this RNG from the specified coordinates. For each coordinate there only exists one seed.
     *
     * @param x X coordinate
     * @param z Z coordinate
     */
    void setPosition(long x, long z);

    /**
     * Generates a pseudorandom number below the specified bound.
     *
     * @param bound The upper bound (exclusive).
     * @return The generated pseudorandom number.
     */
    int random(int bound);

    /**
     * Generates a pseudorandom boolean.
     *
     * @return The generated boolean.
     */
    default boolean randomBool() {
        return random(2) == 0;
    }

    /**
     * Generates a pseudorandom double between 0 and 1.
     *
     * @return The generated double.
     */
    default double randomDouble() {
        return random(Integer.MAX_VALUE) / (double) Integer.MAX_VALUE;
    }

    /**
     * Generates a pseudorandom float between 0 and 1.
     *
     * @return The generated float.
     */
    default float randomFloat() {
        return random(Integer.MAX_VALUE) / (float) Integer.MAX_VALUE;
    }

    /**
     * Recomputes the seed of this RNG from the specified coordinates using {@link #setPosition} and returns this
     * instance for convenience.
     *
     * @return This instance for convenience
     */
    default RegionRNG position(int x, int z) {
        setPosition(x, z);
        return this;
    }

    /**
     * Chooses pseudorandomly between two integers. This does not compute the next seed if the two values are equal.
     *
     * @param a Value A
     * @param b Value B
     * @return A or B
     */
    default int pickRandom(int a, int b) {
        if (a == b) return a;
        return randomBool() ? a : b;
    }

    /**
     * Chooses pseudorandomly between two floats. This does not compute the next seed if the two values are equal.
     *
     * @param a Value A
     * @param b Value B
     * @return A or B
     */
    default float pickRandom(float a, float b) {
        if (a == b) return a;
        return randomBool() ? a : b;
    }

    /**
     * Picks pseudorandomly from four integers. This does not compute the next seed if all values are equal.
     *
     * @param a Value A
     * @param b Value B
     * @param c Value C
     * @param d Value D
     * @return A, B, C or D
     */
    default int pickRandom(int a, int b, int c, int d) {
        if (a == b && a == c && a == d) return a;
        int rand = random(4);
        if (rand == 0) return a;
        if (rand == 1) return b;
        if (rand == 2) return c;
        return d;
    }

    /**
     * Picks pseudorandomly from four floats. This does not compute the next seed if all values are equal.
     *
     * @param a Value A
     * @param b Value B
     * @param c Value C
     * @param d Value D
     * @return A, B, C or D
     */
    default float pickRandom(float a, float b, float c, float d) {
        if (a == b && a == c && a == d) return a;
        int rand = random(4);
        if (rand == 0) return a;
        if (rand == 1) return b;
        if (rand == 2) return c;
        return d;
    }

    /**
     * Picks pseudorandomly from a set of integers.
     *
     * @param ints The integer array/varargs to pick from.
     * @return The picked integer
     */
    default int pickRandom(int... ints) {
        return ints[random(ints.length)];
    }

    /**
     * Picks pseudorandomly from a set of floats.
     *
     * @param floats The float array/varargs to pick from.
     * @return The picked float
     */
    default float pickRandom(float... floats) {
        return floats[random(floats.length)];
    }
}
