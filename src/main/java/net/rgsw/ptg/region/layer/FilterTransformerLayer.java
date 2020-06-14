/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.Region;
import net.rgsw.ptg.region.RegionRNG;

@FunctionalInterface
public interface FilterTransformerLayer extends TransformerLayer {
    @Override
    default int generate( RegionRNG rng, Region region, int x, int z ) {
        return generate( rng, region.getValue( x, z ) );
    }

    int generate( RegionRNG rng, int value );
}
