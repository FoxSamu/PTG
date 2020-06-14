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
public interface CastleTransformerLayer extends TransformerLayer {
    @Override
    default int generate( RegionRNG rng, Region region, int x, int z ) {
        return generate(
            rng,
            region.getValue( x, z ),
            region.getValue( x - 1, z ),
            region.getValue( x + 1, z ),
            region.getValue( x, z - 1 ),
            region.getValue( x, z + 1 )
        );
    }

    int generate( RegionRNG rng, int center, int negX, int posX, int negZ, int posZ );
}
