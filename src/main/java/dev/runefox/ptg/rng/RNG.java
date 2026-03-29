package dev.runefox.ptg.rng;

/**
 * A pseudorandom number generator.
 *
 * @see RNGPrimer
 */
public interface RNG {
    /**
     * Sets the seed of this {@link RNG}.
     */
    void seed(long seed);

    /**
     * Skips {@code n} random numbers in the random number sequence.
     */
    void skip(int n);

    /**
     * Skips 1 random number in the random number sequence.
     */
    default void skip() {
        skip(1);
    }

    /**
     * Generates a pseudorandom integer.
     *
     * @return The generated pseudorandom number.
     */
    int nextInt();

    /**
     * Generates a pseudorandom number below the specified bound.
     *
     * @param bound The upper bound (exclusive).
     * @return The generated pseudorandom number.
     */
    int nextInt(int bound);

    /**
     * Generates a pseudorandom number in the specified range.
     *
     * @param origin The smallest value that should be generated.
     * @param bound The upper bound (exclusive).
     * @return The generated pseudorandom number.
     */
    int nextInt(int origin, int bound);

    /**
     * Generates a pseudorandom long.
     *
     * @return The generated pseudorandom number.
     */
    long nextLong();

    /**
     * Generates a pseudorandom number below the specified bound.
     *
     * @param bound The upper bound (exclusive).
     * @return The generated pseudorandom number.
     */
    long nextLong(long bound);

    /**
     * Generates a pseudorandom number in the specified range.
     *
     * @param origin The smallest value that should be generated.
     * @param bound The upper bound (exclusive).
     * @return The generated pseudorandom number.
     */
    long nextLong(long origin, long bound);

    /**
     * Generates a pseudorandom boolean.
     *
     * @return The generated boolean.
     */
    boolean nextBool();

    /**
     * Generates a pseudorandom double between 0 (inclusive) and 1 (exclusive).
     *
     * @return The generated double.
     */
    double nextDouble();

    /**
     * Generates a pseudorandom double between 0 (inclusive) and {@code bound} (exclusive).
     *
     * @return The generated double.
     */
    default double nextDouble(double bound) {
        if (!(0.0f < bound && bound < Double.POSITIVE_INFINITY)) {
            throw new IllegalArgumentException("Bound must be positive and finite");
        }

        double random = nextDouble() * bound;

        // Correct rounding error
        if (random >= bound) {
            random = Math.nextDown(bound);
        }

        return random;
    }

    /**
     * Generates a pseudorandom double between {@code origin} (inclusive) and {@code bound} (exclusive).
     *
     * @return The generated double.
     */
    default double nextDouble(double origin, double bound) {
        if (origin <= Float.NEGATIVE_INFINITY || origin >= bound || bound >= Float.POSITIVE_INFINITY) {
            throw new IllegalArgumentException("Bound must be greater than origin");
        }

        double random = nextDouble();

        if (bound - origin < Double.POSITIVE_INFINITY) {
            random = random * (bound - origin) + origin;
        } else {
            // Avoid overflow
            double halfOrigin = 0.5 * origin;
            random = (random * (0.5 * bound - halfOrigin) + halfOrigin) * 2.0;
        }

        // Correct rounding error
        if (random >= bound) {
            random = Math.nextDown(bound);
        }

        return random;
    }

    /**
     * Generates a pseudorandom float between 0 and 1.
     *
     * @return The generated float.
     */
    float nextFloat();

    /**
     * Generates a pseudorandom float between 0 (inclusive) and {@code bound} (exclusive).
     *
     * @return The generated float.
     */
    default float nextFloat(float bound) {
        if (!(0.0f < bound && bound < Double.POSITIVE_INFINITY)) {
            throw new IllegalArgumentException("Bound must be positive and finite");
        }

        float random = nextFloat() * bound;

        // Correct rounding error
        if (random >= bound) {
            random = Math.nextDown(bound);
        }

        return random;
    }

    /**
     * Generates a pseudorandom float between {@code origin} (inclusive) and {@code bound} (exclusive).
     *
     * @return The generated float.
     */
    default float nextFloat(float origin, float bound) {
        if (origin <= Double.NEGATIVE_INFINITY || origin >= bound || bound >= Double.POSITIVE_INFINITY) {
            throw new IllegalArgumentException("Bound must be greater than origin");
        }

        float random = nextFloat();

        if (bound - origin < Float.POSITIVE_INFINITY) {
            random = random * (bound - origin) + origin;
        } else {
            // Avoid overflow
            float halfOrigin = 0.5f * origin;
            random = (random * (0.5f * bound - halfOrigin) + halfOrigin) * 2.0f;
        }

        // Correct rounding error
        if (random >= bound) {
            random = Math.nextDown(bound);
        }

        return random;
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
        return nextBool() ? a : b;
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
        return nextBool() ? a : b;
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
        int rand = nextInt(4);
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
        int rand = nextInt(4);
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
        return ints[nextInt(ints.length)];
    }

    /**
     * Picks pseudorandomly from a set of floats.
     *
     * @param floats The float array/varargs to pick from.
     * @return The picked float
     */
    default float pickRandom(float... floats) {
        return floats[nextInt(floats.length)];
    }
}
