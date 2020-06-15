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
public interface FloatFilterMergerLayer extends MergerLayer {
    @Override
    default int generate( RegionRNG rng, Region regionA, Region regionB, int x, int z ) {
        return Float.floatToRawIntBits( generate( rng, regionA.getFPValue( x, z ), regionB.getFPValue( x, z ) ) );
    }

    float generate( RegionRNG rng, float a, float b );
}
