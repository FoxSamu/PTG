package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatArithmeticTransformerLayer extends FloatFilterTransformerLayer {
    float compute( float value );

    @Override
    default float generate( RegionRNG rng, float value ) {
        return compute( value );
    }
}
