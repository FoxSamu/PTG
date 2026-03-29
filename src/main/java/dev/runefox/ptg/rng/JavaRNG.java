package dev.runefox.ptg.rng;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.LongFunction;
import java.util.random.RandomGenerator;

/**
 * A {@link RNG} implementation backed by a {@link RandomGenerator} from {@link java.util.random}.
 */
public class JavaRNG implements ForkableRNG {
    private RandomGenerator random;
    private final LongFunction<RandomGenerator> factory;

    /**
     * Creates a new {@link JavaRNG}.
     *
     * @param seed The seed.
     * @param factory A function that constructs the desired random generator from a seed.
     */
    public JavaRNG(long seed, LongFunction<RandomGenerator> factory) {
        this.random = factory.apply(seed);
        this.factory = factory;
    }

    /**
     * Creates a new {@link JavaRNG} using {@link Random} as a backing random generator.
     *
     * @param seed The seed.
     */
    public JavaRNG(long seed) {
        this(seed, Random::new);
    }

    /**
     * Creates a new {@link JavaRNG} with a random seed determined by {@link ThreadLocalRandom}.
     *
     * @param factory A function that constructs the desired random generator from a seed.
     */
    public JavaRNG(LongFunction<RandomGenerator> factory) {
        this(newSeed(), factory);
    }

    /**
     * Creates a new {@link JavaRNG} with a random seed determined by {@link ThreadLocalRandom} and {@link Random} as
     * backing random generator.
     */
    public JavaRNG() {
        this(newSeed());
    }

    @Override
    public void seed(long seed) {
        random = factory.apply(seed);
    }

    @Override
    public void skip(int n) {
        for (int i = 0; i < n; i ++) {
            random.nextInt();
        }
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    @Override
    public int nextInt(int origin, int bound) {
        return random.nextInt(origin, bound);
    }

    @Override
    public long nextLong() {
        return random.nextLong();
    }

    @Override
    public long nextLong(long bound) {
        return random.nextLong(bound);
    }

    @Override
    public long nextLong(long origin, long bound) {
        return random.nextLong(origin, bound);
    }

    @Override
    public boolean nextBool() {
        return random.nextBoolean();
    }

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public double nextDouble(double bound) {
        return random.nextDouble(bound);
    }

    @Override
    public double nextDouble(double origin, double bound) {
        return random.nextDouble(origin, bound);
    }

    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    @Override
    public float nextFloat(float bound) {
        return random.nextFloat(bound);
    }

    @Override
    public float nextFloat(float origin, float bound) {
        return random.nextFloat(origin, bound);
    }

    @Override
    public RNG fork() {
        return new JavaRNG(nextLong());
    }

    @Override
    public RNGPrimer forkPrimer() {
        return new Primer(nextLong(), factory);
    }

    private static long newSeed() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * A {@link RNGPrimer} for {@link JavaRNG}s.
     */
    public static class Primer implements RNGPrimer {
        private final long seed;
        private final LongFunction<RandomGenerator> factory;

        /**
         * Creates a new {@link JavaRNG.Primer}.
         *
         * @param seed The seed.
         * @param factory A function that constructs the desired random generator from a seed.
         */
        public Primer(long seed, LongFunction<RandomGenerator> factory) {
            this.seed = seed;
            this.factory = factory;
        }

        /**
         * Creates a new {@link JavaRNG.Primer} using {@link Random} as backing random generator.
         *
         * @param seed The seed.
         */
        public Primer(long seed) {
            this(seed, Random::new);
        }

        /**
         * Creates a new {@link JavaRNG.Primer} with a random seed determined by {@link ThreadLocalRandom}.
         *
         * @param factory A function that constructs the desired random generator from a seed.
         */
        public Primer(LongFunction<RandomGenerator> factory) {
            this(newSeed(), factory);
        }

        /**
         * Creates a new {@link JavaRNG.Primer} with a random seed determined by {@link ThreadLocalRandom} and {@link Random}
         * as a backing random generator.
         */
        public Primer() {
            this(newSeed());
        }

        @Override
        public RNG rng() {
            return new JavaRNG(seed, factory);
        }

        @Override
        public RNG with(long seed) {
            return new JavaRNG(seed ^ this.seed, factory);
        }
    }
}
