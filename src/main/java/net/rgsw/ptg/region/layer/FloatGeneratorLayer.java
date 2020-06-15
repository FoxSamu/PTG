package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatGeneratorLayer extends GeneratorLayer {
    @Override
    default int generate( RegionRNG rng, int x, int z ) {
        return Float.floatToRawIntBits( generateFP( rng, x, z ) );
    }

    float generateFP( RegionRNG rng, int x, int z );
}
