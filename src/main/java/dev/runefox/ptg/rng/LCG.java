package dev.runefox.ptg.rng;

/**
 * A linear-congruential {@link RNG}. The LCG uses the same constants as {@link java.util.Random}.
 */
public class LCG extends BitRNG {
    private static final int BITS = 48;
    private static final long MASK = (1L << BITS) - 1;
    private static final long MUL = 0x5DEECE66DL;
    private static final long INC = 11L;

    private long seed;

    public LCG(long seed) {
        this.seed = (seed ^ MUL) & MASK;
    }

    @Override
    protected int next(int bits) {
        long o = seed;
        long n = o * MUL + INC & MASK;
        seed = n;

        return (int) (n >> BITS - bits);
    }

    @Override
    public void seed(long seed) {
        this.seed = (seed ^ MUL) & MASK;
    }

    @Override
    public void skip(int n) {
        long s = seed;
        for (int i = 0; i < n; i ++) {
            s = s * MUL + INC & MASK;
        }
        seed = s;
    }

    @Override
    public RNG fork() {
        return new LCG(nextLong());
    }

    @Override
    public RNGPrimer forkPrimer() {
        return new Primer(nextLong());
    }

    /**
     * A primer for {@link LCG}s.
     */
    public static class Primer implements RNGPrimer {
        private final long seed;

        public Primer(long seed) {
            this.seed = seed;
        }

        @Override
        public RNG rng() {
            return new LCG(seed);
        }

        @Override
        public RNG with(long seed) {
            return new LCG(seed ^ this.seed);
        }
    }
}
