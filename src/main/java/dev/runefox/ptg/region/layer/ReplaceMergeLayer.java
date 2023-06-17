/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
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
