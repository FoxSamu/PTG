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

public class PackFloatLayer implements FilterTransformerLayer {
    private final int precision;
    private final float min;
    private final float max;

    public PackFloatLayer(int precision, float min, float max) {
        this.precision = precision;
        this.min = min;
        this.max = max;
    }

    @Override
    public int generate(RegionRNG rng, int value) {
        float t = (clamp(Float.intBitsToFloat(value), min, max) - min) / (max - min);
        return floor(t * (precision - 1));
    }

    private static float clamp(float val, float min, float max) {
        if (val > max) return max;
        return Math.max(val, min);
    }

    private static int floor(float val) {
        int ival = (int) val;
        return val < 0 ? ival - 1 : ival;
    }
}
