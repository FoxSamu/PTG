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
public interface GeneratorLayer {
    int generate( RegionRNG rng, int x, int z );

    default <R extends Region> RegionFactory<R> factory( RegionContext<R> ctx, long seed ) {
        return () -> {
            RegionRNG rng = ctx.getRNG( seed );
            return ctx.create( ( x, z ) -> generate( rng.position( x, z ), x, z ) );
        };
    }
}
