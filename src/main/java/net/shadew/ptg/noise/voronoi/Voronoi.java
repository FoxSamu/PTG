/*
 * Copyright (c) 2020 RGSW
 * Licensed under Apache 2.0 license
 */

package net.shadew.ptg.noise.voronoi;

import net.shadew.ptg.noise.util.HashFunction2D;
import net.shadew.ptg.noise.util.HashFunction3D;
import net.shadew.ptg.noise.util.NoiseMath;

final class Voronoi {
    private static final double HASH_RANGE = 0x7FFFFFFF;

    private Voronoi() {
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

    public static double compute(double x, double y, HashFunction2D hfx, HashFunction2D hfy) {
        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);

        double nearest = Double.POSITIVE_INFINITY;
        double nearestx = 0;
        double nearesty = 0;
        int nearestix = 0;
        int nearestiy = 0;

        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                double px = hfx.hash(minx + ix, miny + iy) / HASH_RANGE + ix + minx;
                double py = hfy.hash(minx + ix, miny + iy) / HASH_RANGE + iy + miny;

                double d = distsq(x, y, px, py);
                if (d < nearest) {
                    nearest = d;
                    nearestx = px;
                    nearesty = py;
                    nearestix = ix;
                    nearestiy = iy;
                }
            }
        }

        double nearestdx = nearestx - x;
        double nearestdy = nearesty - y;

        double dist = Double.POSITIVE_INFINITY;
        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                if (ix == nearestix && iy == nearestiy)
                    continue;

                double px = hfx.hash(minx + ix, miny + iy) / HASH_RANGE + ix + minx;
                double py = hfy.hash(minx + ix, miny + iy) / HASH_RANGE + iy + miny;

                double dx = px - x;
                double dy = py - y;

                double dcx = (dx + nearestdx) / 2d;
                double dcy = (dy + nearestdy) / 2d;

                double diffx = dx - nearestdx;
                double diffy = dy - nearestdy;
                double difflen = Math.sqrt(diffx * diffx + diffy * diffy);
                diffx /= difflen;
                diffy /= difflen;

                double edist = dcx * diffx + dcy * diffy;
                if (edist < dist)
                    dist = edist;
            }
        }

        return dist * 4 - 1;
    }

    public static double compute(double x, double y, double z, HashFunction3D hfx, HashFunction3D hfy, HashFunction3D hfz) {
        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);
        long minz = NoiseMath.floor(z);

        double nearest = Double.POSITIVE_INFINITY;
        double nearestx = 0;
        double nearesty = 0;
        double nearestz = 0;
        int nearestix = 0;
        int nearestiy = 0;
        int nearestiz = 0;

        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                for (int iz = -1; iz <= 1; iz++) {
                    double px = hfx.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE + ix + minx;
                    double py = hfy.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE + iy + miny;
                    double pz = hfz.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE + iz + minz;

                    double d = distsq(x, y, z, px, py, pz);
                    if (d < nearest) {
                        nearest = d;
                        nearestx = px;
                        nearesty = py;
                        nearestz = pz;
                        nearestix = ix;
                        nearestiy = iy;
                        nearestiz = iz;
                    }
                }
            }
        }

        double nearestdx = nearestx - x;
        double nearestdy = nearesty - y;
        double nearestdz = nearestz - z;

        double dist = Double.POSITIVE_INFINITY;
        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                for (int iz = -1; iz <= 1; iz++) {
                    if (ix == nearestix && iy == nearestiy)
                        continue;

                    double px = hfx.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE + ix + minx;
                    double py = hfy.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE + iy + miny;
                    double pz = hfz.hash(minx + ix, miny + iy, minz + iz) / HASH_RANGE + iz + minz;

                    double dx = px - x;
                    double dy = py - y;
                    double dz = pz - z;

                    double dcx = (dx + nearestdx) / 2d;
                    double dcy = (dy + nearestdy) / 2d;
                    double dcz = (dz + nearestdz) / 2d;

                    double diffx = dx - nearestdx;
                    double diffy = dy - nearestdy;
                    double diffz = dz - nearestdz;
                    double difflen = Math.sqrt(diffx * diffx + diffy * diffy + diffz * diffz);
                    diffx /= difflen;
                    diffy /= difflen;
                    diffz /= difflen;

                    double edist = dcx * diffx + dcy * diffy + dcz * diffz;
                    if (edist < dist)
                        dist = edist;
                }
            }
        }

        return dist * 4 - 1;
    }
}
