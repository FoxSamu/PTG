package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.Region;
import net.rgsw.ptg.region.RegionRNG;

@FunctionalInterface
public interface FloatMergerLayer extends MergerLayer {
    @Override
    default int generate( RegionRNG rng, Region regionA, Region regionB, int x, int z ) {
        return Float.floatToRawIntBits( generateFP( rng, regionA, regionB, x, z ) );
    }

    float generateFP( RegionRNG rng, Region regionA, Region regionB, int x, int z );
}
