/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

public class CellZoomLayer extends ZoomLayer {
    public static final CellZoomLayer INSTANCE = new CellZoomLayer();

    protected CellZoomLayer() {
    }

    @Override
    protected int pickRandom( RegionRNG rng, int v00, int v01, int v10, int v11 ) {
        return v00;
    }

    @Override
    protected int pickRandom( RegionRNG rng, int a, int b ) {
        return a;
    }
}
