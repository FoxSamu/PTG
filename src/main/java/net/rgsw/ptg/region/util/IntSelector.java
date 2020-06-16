package net.rgsw.ptg.region.util;

@FunctionalInterface
public interface IntSelector {
    boolean mustReplace( int val );
}
