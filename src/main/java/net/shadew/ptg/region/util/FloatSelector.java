/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.util;

@FunctionalInterface
public interface FloatSelector {
    boolean mustReplace(float val);
}
