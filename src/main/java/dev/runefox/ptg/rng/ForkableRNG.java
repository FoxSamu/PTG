package dev.runefox.ptg.rng;

/**
 * An {@link RNG} with forking capabilities.
 */
public interface ForkableRNG extends RNG {
    /**
     * Forks a new {@link RNG} from this instance. The forked RNG has its own seed, which is determined by generating a
     * random seed using this RNG. The returned {@link RNG} should not continue the same sequence as this {@link RNG}.
     *
     * @return The forked {@link RNG}.
     */
    RNG fork();

    /**
     * Forks a new {@link RNGPrimer} from this RNG. The forked primer has its own seed, which is determined by
     * generating a random seed using this RNG.
     *
     * @return The forked {@link RNGPrimer}.
     */
    RNGPrimer forkPrimer();
}
