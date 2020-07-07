/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.region.util;

@FunctionalInterface
public interface IntSelector {
    boolean mustReplace(int val);
}
