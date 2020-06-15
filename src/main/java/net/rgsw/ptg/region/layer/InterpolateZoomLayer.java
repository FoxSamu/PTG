package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

public class InterpolateZoomLayer extends ZoomLayer {
    public static final InterpolateZoomLayer INSTANCE = new InterpolateZoomLayer();

    protected InterpolateZoomLayer() {
    }

    @Override
    protected int pickRandom( RegionRNG rng, int a, int b, int c, int d ) {
        // avg(avg(a, b), avg(c, d)) = avg(a, b, c, d)
        return a + b + c + d >> 2; // x >> 2 = x / 4
    }

    @Override
    protected int pickRandom( RegionRNG rng, int a, int b ) {
        return a + b >> 1; // x >> 1 = x / 2
    }
}
