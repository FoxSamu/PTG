/*
 * Copyright 2020-2026 O. W. Nankman
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
 * AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package dev.runefox.ptg.noise.util;

/**
 * Helper class for basic mathematical operations used during noise generation.
 */
public final class NoiseMath {

    private NoiseMath() {
    }

    /**
     * Interpolates value between 0 and 1 to value between a and b using linear interpolation
     *
     * @param x Value between 0 and 1
     * @see NoiseMath#unlerp(double, double, double)
     */
    public static double lerp(double a, double b, double x) {
        return a + x * (b - a);
    }

    /**
     * Interpolates value between a and b to value between 0 and 1 using linear interpolation
     *
     * @param x Value between a and b
     * @see NoiseMath#lerp(double, double, double)
     */
    public static double unlerp(double a, double b, double x) {
        return (x - a) / (b - a);
    }

    /**
     * Applies smoothing function to a double value between 0 and 1, according to how Ken Perlin defined it.
     *
     * @param t The linear value (0 - 1)
     * @return The smoothed value (0 - 1)
     */
    public static double smooth(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    /**
     * Limits a value to a range, applying a minimum and a maximum limit to a value.
     *
     * @param a The minimum limit
     * @param b The maximum limit
     * @param x The value, which should be limited to
     * @return The value, limited between <i>a</i> and <i>b</i>
     */
    public static double clamp(double a, double b, double x) {
        if (a > b) throw new IllegalArgumentException("Minimum limit is more than maximum limit");
        return x < a ? a : Math.min(x, b);
    }

    public static long floor(double v) {
        long lv = (long) v;
        return v < 0 ? lv - 1 : lv;
    }

    public static int floorI(double v) {
        int lv = (int) v;
        return v < 0 ? lv - 1 : lv;
    }

}
