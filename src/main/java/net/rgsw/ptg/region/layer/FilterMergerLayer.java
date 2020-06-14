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
public interface FilterMergerLayer extends MergerLayer {
    @Override
    default int generate( RegionRNG rng, Region regionA, Region regionB, int x, int z ) {
        return generate( rng, regionA.getValue( x, z ), regionB.getValue( x, z ) );
    }

    int generate( RegionRNG rng, int a, int b );
}
