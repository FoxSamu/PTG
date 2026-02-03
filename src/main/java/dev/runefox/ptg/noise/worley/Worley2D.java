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

package dev.runefox.ptg.noise.worley;

import dev.runefox.ptg.noise.BaseNoise2D;
import dev.runefox.ptg.noise.util.Hash;

/**
 * 2D Worley (voronoi, distance to point) noise generator.
 */
public class Worley2D extends BaseNoise2D {

    /**
     * Constructs a Worley noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public Worley2D(int seed) {
        super(seed);
    }

    /**
     * Constructs a Worley noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public Worley2D(int seed, double scale) {
        super(seed, scale);
    }

    /**
     * Constructs a Worley noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public Worley2D(int seed, double scaleX, double scaleY) {
        super(seed, scaleX, scaleY);
    }

    private int hashx(long x, long y) {
        return Hash.hash2I(seed * 12551 + 2315, x, y);
    }

    private int hashy(long x, long y) {
        return Hash.hash2I(seed * 5281 + 1121, x, y);
    }

    @Override
    public double generate(double x, double y) {
        x /= scaleX;
        y /= scaleY;

        return Worley.compute(x, y, this::hashx, this::hashy);
    }
}
