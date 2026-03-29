package dev.runefox.ptg.rng;

/**
 * An object implementing {@link LongHashable} provides 64-bit hash codes alongside Java's built-in 32-bit hash codes.
 * This is used by {@link RNGPrimer}s to hash any object into a 64-bit seed.
 */
public interface LongHashable {
    /**
     * Computes a 64-bit hash code for this object.
     *
     * @return The hash code.
     */
    long longHashCode();
}
