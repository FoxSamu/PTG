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

import dev.runefox.ptg.region.Region;
import dev.runefox.ptg.region.RegionRNG;
import dev.runefox.ptg.region.util.FloatSelector;
import dev.runefox.ptg.region.util.IntSelector;

public class ReplaceMergeLayer implements MergerLayer {
    private final IntSelector selector;

    public ReplaceMergeLayer(IntSelector selector) {
        this.selector = selector;
    }

    public ReplaceMergeLayer(FloatSelector selector) {
        this.selector = val -> selector.mustReplace(Float.intBitsToFloat(val));
    }

    public ReplaceMergeLayer(int selector) {
        this.selector = val -> val == selector;
    }

    public ReplaceMergeLayer(float selector) {
        this.selector = val -> Float.intBitsToFloat(val) == selector;
    }

    // For performance we use a raw MergerLayer
    // - We don't want region B to generate anything unless regionA generated a selected value
    @Override
    public int generate(RegionRNG rng, Region regionA, Region regionB, int x, int z) {
        int a = regionA.getValue(x, z);
        if (selector.mustReplace(a)) return regionB.getValue(x, z);
        return a;
    }
}
