/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

public class FloatRandomLayer implements FloatGeneratorLayer {
    private final RandomFunction function;

    public FloatRandomLayer( RandomFunction function ) {
        this.function = function;
    }

    public FloatRandomLayer( float min, float max ) {
        this( rng -> rng.randomFloat() * ( max - min ) + min );
    }

    public FloatRandomLayer( float[] floats ) {
        this( rng -> rng.pickRandom( floats ) );
    }

    @Override
    public float generateFP( RegionRNG rng, int x, int z ) {
        return function.random( rng );
    }

    @FunctionalInterface
    public interface RandomFunction {
        float random( RegionRNG rng );
    }
}
