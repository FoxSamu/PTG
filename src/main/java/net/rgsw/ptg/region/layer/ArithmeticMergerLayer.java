package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

@FunctionalInterface
public interface ArithmeticMergerLayer extends FilterMergerLayer {
    int compute( int a, int b );

    @Override
    default int generate( RegionRNG rng, int a, int b ) {
        return compute( a, b );
    }
}
