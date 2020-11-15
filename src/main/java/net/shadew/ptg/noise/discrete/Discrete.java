/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.discrete;

import net.shadew.ptg.noise.util.HashFunction2D;
import net.shadew.ptg.noise.util.HashFunction3D;
import net.shadew.ptg.noise.util.NoiseMath;

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
