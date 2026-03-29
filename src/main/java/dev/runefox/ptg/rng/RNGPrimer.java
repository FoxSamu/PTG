package dev.runefox.ptg.rng;

/**
 * A source of {@link RNG}s. A {@link RNGPrimer} has a root seed, which it scrambles together with a given hash or seed
 * to create {@link RNG} instances. Each {@link RNG} implementation has an associated {@link RNGPrimer} implementation
 * that supplies {@link RNG}s of its associated type.
 *
 * @see RNG
 */
public interface RNGPrimer {
    /**
     * Creates a {@link RNG} with the root seed of this primer.
     *
     * @return The created {@link RNG}.
     */
    RNG rng();

    /**
     * Creates a {@link RNG} with the root seed of this primer and a given seed.
     *
     * @param seed A seed.
     * @return The created {@link RNG}.
     */
    RNG with(long seed);

    /**
     * Creates a {@link RNG} with the root seed of this primer and a given seed.
     *
     * @param seed A seed.
     * @return The created {@link RNG}.
     */
    default RNG with(int seed) {
        return with(RNGUtil.seed(seed));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a given seed.
     *
     * @param seed A seed.
     * @return The created {@link RNG}.
     */
    default RNG with(float seed) {
        return with(Float.floatToIntBits(seed));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a given seed.
     *
     * @param seed A seed.
     * @return The created {@link RNG}.
     */
    default RNG with(double seed) {
        return with(Double.doubleToLongBits(seed));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(long x, long y) {
        return with(RNGUtil.seed(x, y));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(long x, long y, long z) {
        return with(RNGUtil.seed(x, y, z));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @param w The w coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(long x, long y, long z, long w) {
        return with(RNGUtil.seed(x, y, z, w));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(int x, int y) {
        return with(RNGUtil.seed(x, y));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(int x, int y, int z) {
        return with(RNGUtil.seed(x, y, z));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @param w The w coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(int x, int y, int z, int w) {
        return with(RNGUtil.seed(x, y, z, w));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(float x, float y) {
        return at(Float.floatToIntBits(x), Float.floatToIntBits(y));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(float x, float y, float z) {
        return at(Float.floatToIntBits(x), Float.floatToIntBits(y), Float.floatToIntBits(z));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @param w The w coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(float x, float y, float z, float w) {
        return at(Float.floatToIntBits(x), Float.floatToIntBits(y), Float.floatToIntBits(z), Float.floatToIntBits(w));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x An x coordinate.
     * @param y An y coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(double x, double y) {
        return at(Double.doubleToLongBits(x), Double.doubleToLongBits(y));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(double x, double y, double z) {
        return at(Double.doubleToLongBits(x), Double.doubleToLongBits(y), Double.doubleToLongBits(z));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a hash of the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @param w The w coordinate.
     * @return The created {@link RNG}.
     */
    default RNG at(double x, double y, double z, double w) {
        return at(Double.doubleToLongBits(x), Double.doubleToLongBits(y), Double.doubleToLongBits(z), Double.doubleToLongBits(w));
    }

    /**
     * Creates a {@link RNG} with the root seed of this primer and a 64-bit hash of the given object (if possible).
     *
     * @param hashable An object to hash.
     * @return The created {@link RNG}.
     *
     * @see LongHashable
     */
    default RNG hash(Object hashable) {
        return with(RNGUtil.tryHashAsLong(hashable));
    }
}
