/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.util;

@FunctionalInterface
public interface HashFunction2D {
    int hash(long x, long y);
}
