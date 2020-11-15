/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.RegionRNG;

public class SmoothingLayer implements CastleTransformerLayer {
    public static final SmoothingLayer INSTANCE = new SmoothingLayer();

    protected SmoothingLayer() {
    }

    @Override
    public int generate(RegionRNG rng, int center, int negX, int posX, int negZ, int posZ) {
        boolean x = negX == posX;
        boolean z = negZ == posZ;
        if (x == z) {
            if (x) { // Both axes have equal values
                return rng.pickRandom(negX, negZ);
            } else { // All sides are different
                return center;
            }
        } else { // One axis has equal values
            return x ? negX : negZ;
        }
    }
}
