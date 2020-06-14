/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

public class FuzzyZoomLayer extends ZoomLayer {
    public static final FuzzyZoomLayer INSTANCE = new FuzzyZoomLayer();

    protected FuzzyZoomLayer() {
    }

    @Override
    protected int pickRandom( RegionRNG rng, int v00, int v01, int v10, int v11 ) {
        return rng.pickRandom( v00, v01, v10, v11 );
    }
}
