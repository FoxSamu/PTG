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

package dev.runefox.ptg.noise.cell;

import dev.runefox.ptg.noise.util.Hash;
import dev.runefox.ptg.noise.BaseNoise3D;

/**
 * 3D Cell (voronoi, cell value) noise generator.
 */
public class Cell3D extends BaseNoise3D {

    /**
     * Constructs a Cell noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Cell3D(int seed) {
        super(seed);
    }

    /**
     * Constructs a Cell noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public Cell3D(int seed, double scale) {
        super(seed, scale);
    }

    /**
     * Constructs a Cell noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     * @param scaleZ The coordinate scaling along Z axis
     */
    public Cell3D(int seed, double scaleX, double scaleY, double scaleZ) {
        super(seed, scaleX, scaleY, scaleZ);
    }

    private int hashx(long x, long y, long z) {
        return Hash.hash3I(seed * 12551 + 2315, x, y, z);
    }

    private int hashy(long x, long y, long z) {
        return Hash.hash3I(seed * 5281 + 1121, x, y, z);
    }

    private int hashz(long x, long y, long z) {
        return Hash.hash3I(seed * 8911 + 731, x, y, z);
    }

    private int hashv(long x, long y, long z) {
        return Hash.hash3I(seed * 12475 + 3187, x, y, z);
    }

    @Override
    public double generate(double x, double y, double z) {
        x /= scaleX;
        y /= scaleY;
        z /= scaleZ;

        return Cell.compute(x, y, z, this::hashx, this::hashy, this::hashz, this::hashv);
    }
}
