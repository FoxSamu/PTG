/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.util;

@FunctionalInterface
public interface HashFunction3D {
    int hash(long x, long y, long z);
}
