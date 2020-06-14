/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.Region;
import net.rgsw.ptg.region.RegionContext;
import net.rgsw.ptg.region.RegionFactory;
import net.rgsw.ptg.region.RegionRNG;

@FunctionalInterface
public interface TransformerLayer {
    int generate( RegionRNG rng, Region region, int x, int z );

    default <R extends Region> RegionFactory<R> factory( RegionContext<R> ctx, long seed, RegionFactory<R> regionFactory ) {
        return () -> {
            RegionRNG rng = ctx.getRNG( seed );
            R region = regionFactory.buildRegion();
            return ctx.create( ( x, z ) -> generate( rng.position( x, z ), region, x, z ), region );
        };
    }
}
