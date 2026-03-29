package dev.runefox.ptg.rng;

/**
 * A <a href="https://en.wikipedia.org/wiki/Xorshift#xorshift*">xorshift*</a> based {@link RNG}.
 */
public class Xorshift extends BitRNG {
    private static final long MUL = 0x2545F4914F6CDD1DL;

    private long seed;

    public Xorshift(long seed) {
        this.seed = scramble(seed);
    }

    private static long scramble(long seed) {
        seed ^= seed >>> 12;
        seed ^= seed << 25;
        seed ^= seed >>> 27;
        seed *= MUL;
        return seed;
    }

    @Override
    protected int next(int bits) {
        long n = scramble(seed);
        seed = n;

        return (int) (n >> 64 - bits);
    }

    @Override
    public void seed(long seed) {
        this.seed = scramble(seed);
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
        return new Xorshift(nextLong());
    }

    @Override
    public RNGPrimer forkPrimer() {
        return new Primer(nextLong());
    }

    /**
     * A primer for {@link Xorshift} instances.
     */
    public static class Primer implements RNGPrimer {
        private final long seed;

        public Primer(long seed) {
            this.seed = seed;
        }

        @Override
        public RNG rng() {
            return new Xorshift(seed);
        }

        @Override
        public RNG with(long seed) {
            return new Xorshift(seed ^ this.seed);
        }
    }
}
