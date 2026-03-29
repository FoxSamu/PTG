package dev.runefox.ptg.rng;

/**
 * A base {@link RNG} implementation that produces random numbers based off short, pseudorandomly produced bitstrings.
 */
public abstract class BitRNG implements ForkableRNG {
    private static final float FLOAT_MULTIPLIER = 5.9604645E-8F;
    private static final double DOUBLE_MULTIPLIER = 1.110223E-16F;

    /**
     * Produces a random sequence of up to 32 bits.
     *
     * @param bits The amount of random bits to generate.
     * @return The bits.
     */
    protected abstract int next(int bits);

    /**
     * Produces a random sequence of up to 64 bits. Defaults to combining multiple shorter sequences returned from
     * {@link #next}.
     *
     * @param bits The amount of random bits to generate.
     * @return The bits.
     */
    protected long nextL(int bits) {
        if (bits <= 32) {
            return next(bits);
        } else {
            var lb = bits / 2;
            return ((long) next(lb)) | ((long) next(bits - lb)) << lb;
        }
    }

    @Override
    public int nextInt() {
        return next(32);
    }

    private int nextIntInternal(int bound) {
        int mask = bound - 1;
        if ((bound & mask) == 0) {
            // Power of 2
            return next(32) & mask;
        }

        int random = next(31);
        int sample = random;
        int modulo = sample % bound;
        while (sample - modulo + mask < 0) {
            sample = next(31);
            modulo = sample % bound;
        }

        return modulo;
    }

    @Override
    public int nextInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        }

        return nextIntInternal(bound);
    }

    @Override
    public int nextInt(int origin, int bound) {
        if (origin >= bound) {
            throw new IllegalArgumentException("Bound must be greater than origin");
        }


        int range = bound - origin;
        int mask = range - 1;

        if ((range & mask) == 0) {
            // Power of 2 range
            return (next(32) & mask) + origin;
        }

        if (range > 0) {
            // Int-representable range
            return nextIntInternal(range) + origin;
        }

        // Not an int-representable range
        int r = next(32);
        while (r < origin || r >= bound) {
            r = next(32);
        }

        return r;
    }

    private long nextLongInternal(long bound) {
        long mask = bound - 1;
        if ((bound & mask) == 0) {
            // Power of 2
            return (nextL(64)) & mask;
        }

        long random = nextL(63);
        long sample = random;
        long modulo = sample % bound;
        while (sample - modulo + mask < 0) {
            sample = nextL(63);
            modulo = sample % bound;
        }

        return modulo;
    }

    @Override
    public long nextLong() {
        return nextL(64);
    }

    @Override
    public long nextLong(long bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        }

        return nextLongInternal(bound);
    }

    @Override
    public long nextLong(long origin, long bound) {
        if (origin >= bound) {
            throw new IllegalArgumentException("Bound must be greater than origin");
        }

        long range = bound - origin;
        long mask = range - 1;

        if ((range & mask) == 0) {
            // Power of 2 range
            return (nextL(64) & mask) + origin;
        }

        if (range > 0) {
            // Long-representable range
            return nextLongInternal(range) + origin;
        }

        // Not a long-representable range
        long r = nextL(64);
        while (r < origin || r >= bound) {
            r = nextL(64);
        }

        return r;
    }

    @Override
    public boolean nextBool() {
        return next(1) != 0;
    }

    @Override
    public double nextDouble() {
        long upper = next(26);
        long lower = next(27);
        return ((upper << 27) | lower) * DOUBLE_MULTIPLIER;
    }

    @Override
    public float nextFloat() {
        return next(24) * FLOAT_MULTIPLIER;
    }
}
