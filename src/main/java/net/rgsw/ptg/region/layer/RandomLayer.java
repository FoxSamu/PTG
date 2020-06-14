/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

public class RandomLayer implements GeneratorLayer {
    private final RandomFunction function;

    public RandomLayer( RandomFunction function ) {
        this.function = function;
    }

    public RandomLayer( int min, int max ) {
        this( rng -> rng.random( max - min + 1 ) + min );
    }

    public RandomLayer( int[] ints ) {
        this( rng -> rng.pickRandom( ints ) );
    }

    public RandomLayer( double oneChance ) {
        this( rng -> rng.randomDouble() < oneChance ? 1 : 0 );
    }

    @Override
    public int generate( RegionRNG rng, int x, int z ) {
        return function.random( rng );
    }

    @FunctionalInterface
    public interface RandomFunction {
        int random( RegionRNG rng );
    }
}
