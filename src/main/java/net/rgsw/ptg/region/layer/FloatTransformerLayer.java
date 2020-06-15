package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.Region;
import net.rgsw.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatTransformerLayer extends TransformerLayer {
    @Override
    default int generate( RegionRNG rng, Region region, int x, int z ) {
        return Float.floatToRawIntBits( generateFP( rng, region, x, z ) );
    }

    float generateFP( RegionRNG rng, Region region, int x, int z );
}
