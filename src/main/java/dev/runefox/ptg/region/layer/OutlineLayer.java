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

public class OutlineLayer implements CastleTransformerLayer {
    public static final OutlineLayer INSTANCE = new OutlineLayer();

    @Override
    public int generate(RegionRNG rng, int center, int negX, int posX, int negZ, int posZ) {
        return areAllSame(center, negX, posX, negZ, posZ) ? 0 : 1;
    }

    private static boolean areAllSame(int a, int b, int c, int d, int e) {
        return e == a && e == b && e == c && e == d;
    }
}
