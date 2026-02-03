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

import dev.runefox.ptg.noise.RepetitiveNoise2D;
import dev.runefox.ptg.noise.util.Hash;

/**
 * Repeating 2D Worley noise generator.
 */
public class RepetitiveWorley2D extends RepetitiveNoise2D {

    /**
     * Constructs a Repeating Worley noise generator
     *
     * @param seed The seed, may be any {@link int}
     */
    public RepetitiveWorley2D(int seed, int repeat) {
        super(seed, repeat);
    }

    /**
     * Constructs a Repeating Worley noise generator
     *
     * @param seed  The seed, may be any {@link int}
     * @param scale The coordinate scaling along all axes
     */
    public RepetitiveWorley2D(int seed, double scale, int repeat) {
        super(seed, scale, repeat);
    }

    /**
     * Constructs a Repeating Worley noise generator
     *
     * @param seed   The seed, may be any {@link int}
     * @param scaleX The coordinate scaling along X axis
     * @param scaleY The coordinate scaling along Y axis
     */
    public RepetitiveWorley2D(int seed, double scaleX, double scaleY, int repeatX, int repeatY) {
        super(seed, scaleX, scaleY, repeatX, repeatY);
    }

    private long repeat(long val, int repeat) {
        return val % repeat + (val < 0 ? repeat : 0);
    }

    private int hashx(long x, long y) {
        return Hash.hash2I(seed * 12551 + 2315, repeat(x, repeatX), repeat(y, repeatY));
    }

    private int hashy(long x, long y) {
        return Hash.hash2I(seed * 5281 + 1121, repeat(x, repeatX), repeat(y, repeatY));
    }

    @Override
    public double generate(double x, double y) {
        x /= scaleX;
        y /= scaleY;

        return Worley.compute(x, y, this::hashx, this::hashy);
    }
}
