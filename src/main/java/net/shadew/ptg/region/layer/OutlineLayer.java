/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.RegionRNG;

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
