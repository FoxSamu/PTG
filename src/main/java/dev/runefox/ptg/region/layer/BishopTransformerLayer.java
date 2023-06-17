/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.Region;
import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface BishopTransformerLayer extends TransformerLayer {
    @Override
    default int generate(RegionRNG rng, Region region, int x, int z) {
        return generate(
            rng,
            region.getValue(x, z),
            region.getValue(x - 1, z - 1),
            region.getValue(x + 1, z - 1),
            region.getValue(x + 1, z + 1),
            region.getValue(x - 1, z + 1)
        );
    }


    int generate(RegionRNG rng, int center, int negXnegZ, int posXnegZ, int posXposZ, int negXposZ);
}
