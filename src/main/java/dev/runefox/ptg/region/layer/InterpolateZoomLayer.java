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

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

public class InterpolateZoomLayer extends ZoomLayer {
    public static final InterpolateZoomLayer INTEGER = new InterpolateZoomLayer();
    public static final InterpolateZoomLayer FLOAT = new InterpolateZoomLayer() {
        @Override
        protected int pickRandom(RegionRNG rng, int a, int b, int c, int d) {
            return Float.floatToRawIntBits(
                (Float.intBitsToFloat(a)
                     + Float.intBitsToFloat(b)
                     + Float.intBitsToFloat(c)
                     + Float.intBitsToFloat(d)) / 4
            );
        }

        @Override
        protected int pickRandom(RegionRNG rng, int a, int b) {
            return Float.floatToRawIntBits(
                (Float.intBitsToFloat(a)
                     + Float.intBitsToFloat(b)) / 2
            );
        }
    };

    protected InterpolateZoomLayer() {
    }

    @Override
    protected int pickRandom(RegionRNG rng, int a, int b, int c, int d) {
        // avg(avg(a, b), avg(c, d)) = avg(a, b, c, d)
        return a + b + c + d >> 2; // x >> 2 = x / 4
    }

    @Override
    protected int pickRandom(RegionRNG rng, int a, int b) {
        return a + b >> 1; // x >> 1 = x / 2
    }
}
