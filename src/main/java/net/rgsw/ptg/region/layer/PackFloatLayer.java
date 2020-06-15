package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;

public class PackFloatLayer implements FilterTransformerLayer {
    private final int precision;
    private final float min;
    private final float max;

    public PackFloatLayer( int precision, float min, float max ) {
        this.precision = precision;
        this.min = min;
        this.max = max;
    }

    @Override
    public int generate( RegionRNG rng, int value ) {
        float t = ( clamp( Float.intBitsToFloat( value ), min, max ) - min ) / ( max - min );
        return floor( t * ( precision - 1 ) );
    }

    private static float clamp( float val, float min, float max ) {
        if( val > max ) return max;
        return Math.max( val, min );
    }

    private static int floor( float val ) {
        int ival = (int) val;
        return val < 0 ? ival - 1 : ival;
    }
}
