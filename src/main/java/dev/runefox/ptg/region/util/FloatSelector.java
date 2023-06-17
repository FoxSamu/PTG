/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.region.util;

@FunctionalInterface
public interface FloatSelector {
    boolean mustReplace(float val);
}
