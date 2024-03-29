/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise;

import dev.runefox.ptg.noise.util.DoubleFunction;
import dev.runefox.ptg.noise.util.Hash;
import dev.runefox.ptg.noise.util.NoiseMath;
import dev.runefox.ptg.region.Region;

/**
 * Generic interface of a noise generator for 2D space.
 */
@FunctionalInterface
public interface Noise2D {

    default double generateMultiplied(double x, double y, double multiplier) {
        return generate(x, y) * multiplier;
    }

    /**
     * Generates noise at the specified coordinates.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The generated noise value
     */
    double generate(double x, double y);


    /**
     * Creates a noise generator that adds a constant value to the generated noise of this noise generator.
     *
     * @param amount The constant value to add
     * @return The created noise generator
     */
    default Noise2D add(double amount) {
        return (x, y) -> generate(x, y) + amount;
    }

    /**
     * Creates a noise generator that subtracts a constant value from the generated noise of this noise generator.
     *
     * @param amount The constant value to subtract
     * @return The created noise generator
     */
    default Noise2D subtract(double amount) {
        return (x, y) -> generate(x, y) - amount;
    }

    /**
     * Creates a noise generator that multiplies the noise generated by this generator with a constant value.
     *
     * @param amount The constant value to multiply with
     * @return The created noise generator
     */
    default Noise2D multiply(double amount) {
        return (x, y) -> generate(x, y) * amount;
    }

    /**
     * Creates a noise generator that divides the noise generated by this generator by a constant value.
     *
     * @param amount The constant value to divide by
     * @return The created noise generator
     */
    default Noise2D divide(double amount) {
        return (x, y) -> generate(x, y) / amount;
    }


    /**
     * Creates a noise generator that adds the noise generated by the specified generator to the noise generated by this
     * generator.
     *
     * @param amount The noise generator that generates noise to add
     * @return The created noise generator
     */
    default Noise2D add(Noise2D amount) {
        return (x, y) -> generate(x, y) + amount.generate(x, y);
    }

    /**
     * Creates a noise generator that subtracts the noise generated by the specified generator from the noise generated
     * by this generator.
     *
     * @param amount The noise generator that generates noise to subtract
     * @return The created noise generator
     */
    default Noise2D subtract(Noise2D amount) {
        return (x, y) -> generate(x, y) - amount.generate(x, y);
    }

    /**
     * Creates a noise generator that multiplies the noise generated by this generator with the noise generated by the
     * specified generator.
     *
     * @param amount The noise generator that generates noise to multiply with
     * @return The created noise generator
     */
    default Noise2D multiply(Noise2D amount) {
        return (x, y) -> generate(x, y) * amount.generate(x, y);
    }

    /**
     * Creates a noise generator that divides the noise generated by this generator by the noise generated by the
     * specified generator.
     *
     * @param amount The noise generator that generates noise to divide by
     * @return The created noise generator
     */
    default Noise2D divide(Noise2D amount) {
        return (x, y) -> generate(x, y) / amount.generate(x, y);
    }

    /**
     * Creates a noise generator that generates the inverted noise field of this generator.
     *
     * @return The created noise generator
     */
    default Noise2D inverse() {
        return (x, y) -> -generate(x, y);
    }

    /**
     * Creates a noise generator that interpolates the noise of this generator between the specified constant limits,
     * using linear interpolation.
     *
     * @param min The constant minimum limit
     * @param max The constant maximum limit
     * @return The created noise generator
     */
    default Noise2D lerp(double min, double max) {
        return (x, y) -> NoiseMath.lerp(min, max, (generate(x, y) + 1) / 2);
    }

    /**
     * Creates a noise generator that interpolates the noise of this generator between the the limits generated by the
     * specified noise generators, using linear interpolation.
     *
     * @param min The minimum limit generator
     * @param max The maximum limit generator
     * @return The created noise generator
     */
    default Noise2D lerp(Noise2D min, Noise2D max) {
        return (x, y) -> NoiseMath.lerp(min.generate(x, y), max.generate(x, y), (generate(x, y) + 1) / 2);
    }

    /**
     * Creates a noise generator that generates a constant value.
     *
     * @param value The value to generate
     * @return The created noise generator
     */
    static Noise2D constant(double value) {
        return (x, y) -> value;
    }

    /**
     * Creates a 2D noise generator from a 3D noise generator, using the specified, constant Z coordinate.
     *
     * @param noise The 3D noise generator
     * @param z     The constant Z coordinate
     * @return The created noise generator
     */
    static Noise2D from3D(Noise3D noise, double z) {
        return (x, y) -> noise.generate(x, y, z);
    }

    /**
     * Creates a noise generator that combines the noises generated by multiple noise generators using the specified
     * function. The values of the noise generators will be passed as varargs to the function.
     *
     * @param combiner The combiner function
     * @param noises   The noise generators to combine
     * @return The created noise generator
     */
    static Noise2D combine(DoubleFunction combiner, Noise2D... noises) {
        return (x, y) -> {
            double[] arr = new double[noises.length];
            int i = 0;
            for (Noise2D noise : noises) {
                arr[i] = noise.generate(x, y);
            }
            return combiner.combine(arr);
        };
    }

    /**
     * Creates a noise generator that scales the noise field generated by this generator.
     *
     * @param scale The scaling of the noise field for every coordinate.
     * @return The created noise generator
     */
    default Noise2D scale(double scale) {
        return (x, y) -> generate(x * scale, y * scale);
    }

    /**
     * Creates a noise generator that scales the noise field generated by this generator.
     *
     * @param x The scaling of the noise field for the X coordinate.
     * @param y The scaling of the noise field for the Y coordinate.
     * @return The created noise generator
     */
    default Noise2D scale(double x, double y) {
        return (x1, y1) -> generate(x1 * x, y1 * y);
    }

    /**
     * Creates a noise generator that translates the noise field generated by this generator.
     *
     * @param x The translation of the noise field along the X coordinate.
     * @param y The translation of the noise field along the Y coordinate.
     * @return The created noise generator
     */
    default Noise2D translate(double x, double y) {
        return (x1, y1) -> generate(x1 + x, y1 + y);
    }

    /**
     * Generates a fractal noise generator from this noise generator using a specified amount of octaves.
     *
     * @param octaves The amount of octaves
     * @return The created noise generator
     */
    default Noise2D fractal(int octaves) {
        return (x, y) -> {
            double n = 0;
            double m = 1;
            for (int i = 0; i < octaves; i++) {
                n += generate(x / m, y / m) * m;
                m /= 2;
            }
            return n;
        };
    }

    /**
     * Generates a ridge noise generator from this noise generator. This filters the noise values generated by this
     * noise generator using the function {@code -(abs(n) * 2 - 1)} where {@code n} is the generated noise value.
     *
     * @return The created noise generator
     */
    default Noise2D ridge() {
        return (x, y) -> {
            double n = generate(x, y);
            return -(Math.abs(n) * 2 - 1);
        };
    }

    /**
     * Creates a cell noise generator with a specific seed.
     *
     * @param seed The seed
     * @return The created noise generator
     */
    static Noise2D random(int seed) {
        return (x, y) -> Hash.hash2D(seed, NoiseMath.floor(x), NoiseMath.floor(y)) * 2 - 1;
    }


    /**
     * Creates a {@link Region}-based cell noise generator. Uses {@link Region#getFPValue}.
     *
     * @param region The region to generate with
     * @return The created noise generator
     */
    static Noise2D region(Region region) {
        return (x, y) -> region.getFPValue(NoiseMath.floorI(x), NoiseMath.floorI(y));
    }
}
