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

package dev.runefox.ptg.noise.cell;

import dev.runefox.ptg.noise.util.HashFunction2D;
import dev.runefox.ptg.noise.util.HashFunction3D;
import dev.runefox.ptg.noise.util.NoiseMath;

final class Cell {
    private static final double HASH_RANGE = 0x7FFFFFFF;

    private Cell() {
    }

    private static double distsq(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return dx * dx + dy * dy;
    }

    private static double distsq(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        return dx * dx + dy * dy + dz * dz;
    }

    public static double compute(double x, double y, HashFunction2D hfx, HashFunction2D hfy, HashFunction2D hfv) {
        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);

        double nearest = Double.POSITIVE_INFINITY;
        double value = 0;

        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                double px = hfx.hash(minx + ix, miny + iy) / HASH_RANGE + ix + minx;
                double py = hfy.hash(minx + ix, miny + iy) / HASH_RANGE + iy + miny;

                double d = distsq(x, y, px, py);
                if (d < nearest) {
                    nearest = d;
                    value = hfv.hash(minx + ix, miny + iy) / HASH_RANGE * 2 - 1;
                }
            }
        }

        return value;
    }

    public static double compute(double x, double y, double z, HashFunction3D hfx, HashFunction3D hfy, HashFunction3D hfz, HashFunction3D hfv) {
        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);
        long minz = NoiseMath.floor(z);

        double nearest = Double.POSITIVE_INFINITY;
        double value = 0;

        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                for (int iz = -1; iz <= 1; iz++) {
                    double px = hfx.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE + ix + minx;
                    double py = hfy.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE + iy + miny;
                    double pz = hfz.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE + iz + minz;

                    double d = distsq(x, y, z, px, py, pz);
                    if (d < nearest) {
                        nearest = d;
                        value = hfv.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE * 2 - 1;
                    }
                }
            }
        }

        return value;
    }
}
