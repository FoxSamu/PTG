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

package dev.runefox.ptg.noise.discrete;

import dev.runefox.ptg.noise.util.HashFunction2D;
import dev.runefox.ptg.noise.util.HashFunction3D;
import dev.runefox.ptg.noise.util.NoiseMath;

final class Discrete {
    private static final double HASH_RANGE = 0x7FFFFFFF;

    private Discrete() {
    }

    public static double compute(double x, double y, HashFunction2D hf) {
        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);

        return hf.hash(minx, miny) / HASH_RANGE * 2 - 1;
    }

    public static double compute(double x, double y, double z, HashFunction3D hf) {
        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);
        long minz = NoiseMath.floor(z);

        return hf.hash(minx, miny, minz) / HASH_RANGE * 2 - 1;
    }
}
