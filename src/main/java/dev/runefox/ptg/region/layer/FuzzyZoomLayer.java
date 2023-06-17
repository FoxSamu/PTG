/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

public class FuzzyZoomLayer extends ZoomLayer {
    public static final FuzzyZoomLayer INSTANCE = new FuzzyZoomLayer();

    protected FuzzyZoomLayer() {
    }

    @Override
    protected int pickRandom(RegionRNG rng, int v00, int v01, int v10, int v11) {
        return rng.pickRandom(v00, v01, v10, v11);
    }
}
