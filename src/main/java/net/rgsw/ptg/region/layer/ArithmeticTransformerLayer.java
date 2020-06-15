package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

@FunctionalInterface
public interface ArithmeticTransformerLayer extends FilterTransformerLayer {
    int compute( int value );

    @Override
    default int generate( RegionRNG rng, int value ) {
        return compute( value );
    }
}
