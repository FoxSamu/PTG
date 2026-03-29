package dev.runefox.ptg.rng;

/**
 * A very fast linear-congruential {@link RNG}. While {@link LCG} is more accurate, {@link FastLCG} provides speed over
 * accuracy.
 */
public class FastLCG extends BitRNG {
    private static final long MUL = 6364136223846793005L;
    private static final long INC = 1442695040888963407L;

    private long seed;

    public FastLCG(long seed) {
        this.seed = seed ^ MUL;
    }

    protected static long scramble(long n) {
        return n * MUL + INC;
    }

    @Override
    protected int next(int bits) {
        long o = seed;
        long n = scramble(o);
        seed = n;

        return (int) (n >> 64 - bits);
    }

    @Override
    protected long nextL(int bits) {
        long o = seed;
        long n = scramble(o);
        seed = n;

        return n >> 64 - bits;
    }

    @Override
    public void seed(long seed) {
        this.seed = seed ^ MUL;
    }

    @Override
    public void skip(int n) {
        long s = seed;
        for (int i = 0; i < n; i ++) {
            s = scramble(s);
        }
        seed = s;
    }

    @Override
    public RNG fork() {
        return new FastLCG(nextLong());
    }

    @Override
    public RNGPrimer forkPrimer() {
        return new Primer(nextLong());
    }

    /**
     * A primer for {@link FastLCG}s.
     */
    public static class Primer implements RNGPrimer {
        private final long seed;

        public Primer(long seed) {
            this.seed = seed;
        }

        @Override
        public RNG rng() {
            return new FastLCG(seed);
        }

        @Override
        public RNG with(long seed) {
            return new FastLCG(seed ^ this.seed);
        }
    }
}
