package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatArithmeticMergerLayer extends FloatFilterMergerLayer {
    float compute( float a, float b );

    @Override
    default float generate( RegionRNG rng, float a, float b ) {
        return compute( a, b );
    }
}
