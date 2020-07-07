/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.layer;

import net.shadew.ptg.region.Region;
import net.shadew.ptg.region.RegionRNG;

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
