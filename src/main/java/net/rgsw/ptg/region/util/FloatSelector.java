package net.rgsw.ptg.region.util;

@FunctionalInterface
public interface FloatSelector {
    boolean mustReplace( float val );
}
