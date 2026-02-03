/*
 * Copyright 2020-2026 O. W. Nankman
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
 * AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package dev.runefox.ptg.region;

public abstract class FractalGenerator<T> {
    private final Region region;

    /**
     * Creates a fractal generator from the given fractal generator.
     *
     * @param region The fractal generator ({@link Region}).
     * @throws NullPointerException When the argument is null.
     */
    public FractalGenerator(Region region) {
        if (region == null) throw new NullPointerException();
        this.region = region;
    }

    /**
     * Creates a fractal generator from the given fractal generator factory.
     *
     * @param factory The fractal generator factory ({@link RegionFactory}).
     * @throws NullPointerException When the argument is null.
     */
    public FractalGenerator(RegionFactory<?> factory) {
        if (factory == null) throw new NullPointerException();
        this.region = factory.buildRegion();
    }

    /**
     * Looks up the specified value for the given ID.
     *
     * @param id The ID, which is the value returned by a {@linkplain Region region}
     * @return The looked up value.
     */
    protected abstract T toValue(int id);

    /**
     * Create an array of the generic fractal type.
     *
     * @param size The array size.
     * @return An array of the specified size.
     */
    protected abstract T[] createArray(int size);


    /**
     * Returns the value at the specified coordinates.
     *
     * @param x X coordinate
     * @param z Z coordinate
     * @return The value at this coords
     */
    public T generate(int x, int z) {
        int biome = region.getValue(x, z);
        return toValue(biome);
    }

    /**
     * Returns the data for the specified region.
     *
     * @param x     The lower X coord
     * @param z     The lower Z coord
     * @param xSize The region size along X-axis
     * @param zSize The region size along Z-axis
     * @return The generated data in the region.
     */
    public T[] generate(int x, int z, int xSize, int zSize) {
        return generate(null, x, z, xSize, zSize);
    }

    /**
     * Generates the data for the specified region, storing them in the specified buffer if possible. A new buffer is
     * created when the specified buffer is null or too small.
     *
     * @param data  The biome buffer to use. Can be null.
     * @param x     The lower X coord
     * @param z     The lower Z coord
     * @param xSize The region size along X-axis
     * @param zSize The region size along Z-axis
     * @return The used array filled with data for the region. This may or may not the same instance as the specified
     *     array.
     */
    public T[] generate(T[] data, int x, int z, int xSize, int zSize) {
        if (data == null || data.length < xSize * zSize) {
            data = createArray(xSize * zSize);
        }

        for (int ix = 0; ix < xSize; ix++) {
            for (int iz = 0; iz < zSize; iz++) {
                data[iz * xSize + ix] = generate(ix + x, iz + z);
            }
        }

        return data;
    }
}
