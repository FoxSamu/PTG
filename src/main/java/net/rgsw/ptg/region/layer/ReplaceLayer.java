package net.rgsw.ptg.region.layer;

import net.rgsw.ptg.region.RegionRNG;
import net.rgsw.ptg.region.util.FloatSelector;
import net.rgsw.ptg.region.util.IntSelector;

public class ReplaceLayer implements FilterTransformerLayer {
    private final IntSelector selector;
    private final int replacement;

    public ReplaceLayer( IntSelector selector, int replacement ) {
        this.selector = selector;
        this.replacement = replacement;
    }

    public ReplaceLayer( FloatSelector selector, float replacement ) {
        this.selector = val -> selector.mustReplace( Float.intBitsToFloat( val ) );
        this.replacement = Float.floatToRawIntBits( replacement );
    }

    public ReplaceLayer( int selector, int replacement ) {
        this.selector = val -> val == selector;
        this.replacement = replacement;
    }

    public ReplaceLayer( float selector, float replacement ) {
        this.selector = val -> Float.intBitsToFloat( val ) == selector;
        this.replacement = Float.floatToRawIntBits( replacement );
    }

    @Override
    public int generate( RegionRNG rng, int value ) {
        return selector.mustReplace( value ) ? replacement : value;
    }
}
