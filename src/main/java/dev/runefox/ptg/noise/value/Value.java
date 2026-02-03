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

package dev.runefox.ptg.noise.value;

import dev.runefox.ptg.noise.util.HashFunction2D;
import dev.runefox.ptg.noise.util.HashFunction3D;
import dev.runefox.ptg.noise.util.NoiseMath;

final class Value {
    private static final double HASH_RANGE = 0x7FFFFFFF;

    private Value() {
    }

    public static double compute(double x, double y, HashFunction2D hf) {

        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);
        long maxx = minx + 1L;
        long maxy = miny + 1L;

        double smoothx = NoiseMath.smooth(x - minx);
        double smoothy = NoiseMath.smooth(y - miny);

        double v1 = hf.hash(minx, miny) / HASH_RANGE * 2 - 1;
        double v2 = hf.hash(maxx, miny) / HASH_RANGE * 2 - 1;
        double v3 = hf.hash(minx, maxy) / HASH_RANGE * 2 - 1;
        double v4 = hf.hash(maxx, maxy) / HASH_RANGE * 2 - 1;

        double lerp12 = NoiseMath.lerp(v1, v2, smoothx);
        double lerp34 = NoiseMath.lerp(v3, v4, smoothx);

        return NoiseMath.lerp(lerp12, lerp34, smoothy);
    }

    public static double compute(double x, double y, double z, HashFunction3D hf) {

        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);
        long minz = NoiseMath.floor(z);
        long maxx = minx + 1L;
        long maxy = miny + 1L;
        long maxz = minz + 1L;

        double smoothx = NoiseMath.smooth(x - minx);
        double smoothy = NoiseMath.smooth(y - miny);
        double smoothz = NoiseMath.smooth(z - minz);


        double v1 = hf.hash(minx, miny, minz) / HASH_RANGE * 2 - 1;
        double v2 = hf.hash(maxx, miny, minz) / HASH_RANGE * 2 - 1;
        double v3 = hf.hash(minx, maxy, minz) / HASH_RANGE * 2 - 1;
        double v4 = hf.hash(maxx, maxy, minz) / HASH_RANGE * 2 - 1;
        double v5 = hf.hash(minx, miny, maxz) / HASH_RANGE * 2 - 1;
        double v6 = hf.hash(maxx, miny, maxz) / HASH_RANGE * 2 - 1;
        double v7 = hf.hash(minx, maxy, maxz) / HASH_RANGE * 2 - 1;
        double v8 = hf.hash(maxx, maxy, maxz) / HASH_RANGE * 2 - 1;

        double lerp12 = NoiseMath.lerp(v1, v2, smoothx);
        double lerp34 = NoiseMath.lerp(v3, v4, smoothx);
        double lerp1234 = NoiseMath.lerp(lerp12, lerp34, smoothy);
        double lerp56 = NoiseMath.lerp(v5, v6, smoothx);
        double lerp78 = NoiseMath.lerp(v7, v8, smoothx);
        double lerp5678 = NoiseMath.lerp(lerp56, lerp78, smoothy);

        return NoiseMath.lerp(lerp1234, lerp5678, smoothz);
    }
}
