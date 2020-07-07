/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.Region;
import net.shadew.ptg.region.RegionRNG;

@FunctionalInterface
public interface FilterTransformerLayer extends TransformerLayer {
    @Override
    default int generate( RegionRNG rng, Region region, int x, int z ) {
        return generate( rng, region.getValue( x, z ) );
    }

    int generate( RegionRNG rng, int value );
}
