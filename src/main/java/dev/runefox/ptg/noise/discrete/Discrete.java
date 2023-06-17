/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
 */

package dev.runefox.ptg.noise.discrete;

import dev.runefox.ptg.noise.util.HashFunction2D;
import dev.runefox.ptg.noise.util.HashFunction3D;
import dev.runefox.ptg.noise.util.NoiseMath;

final class Discrete {
    private static final double HASH_RANGE = 0x7FFFFFFF;

    private Discrete() {
    }

    public static double compute(double x, double y, HashFunction2D hf) {
        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);

        return hf.hash(minx, miny) / HASH_RANGE * 2 - 1;
    }

    public static double compute(double x, double y, double z, HashFunction3D hf) {
        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);
        long minz = NoiseMath.floor(z);

        return hf.hash(minx, miny, minz) / HASH_RANGE * 2 - 1;
    }
}
