/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

public class CellZoomLayer extends ZoomLayer {
    public static final CellZoomLayer INSTANCE = new CellZoomLayer();

    protected CellZoomLayer() {
    }

    @Override
    protected int pickRandom(RegionRNG rng, int v00, int v01, int v10, int v11) {
        return v00;
    }

    @Override
    protected int pickRandom(RegionRNG rng, int a, int b) {
        return a;
    }
}
