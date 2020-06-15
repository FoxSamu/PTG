package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.noise.Noise2D;
import net.rgsw.ptg.region.RegionRNG;

public class NoiseLayer implements FloatGeneratorLayer {
    private final Noise2D noise;

    public NoiseLayer( Noise2D noise ) {
        this.noise = noise;
    }

    @Override
    public float generateFP( RegionRNG rng, int x, int z ) {
        return (float) noise.generate( x, z );
    }
}
