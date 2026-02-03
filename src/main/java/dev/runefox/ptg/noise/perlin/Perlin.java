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

package dev.runefox.ptg.noise.perlin;

import dev.runefox.ptg.noise.util.HashFunction2D;
import dev.runefox.ptg.noise.util.HashFunction3D;
import dev.runefox.ptg.noise.util.NoiseMath;

final class Perlin {

    private static final int[][] GRAD_2D = {
        {-1, -1},
        {-1, 1},
        {1, -1},
        {1, 1},
        {-1, -1},
        {-1, 1},
        {1, -1},
        {1, 1},
        {-1, 0},
        {-1, 0},
        {1, 0},
        {1, 0},
        {0, -1},
        {0, 1},
        {0, -1},
        {0, 1}
    };

    private static final int[][] GRAD_3D = {
        {-1, -1, 0},
        {-1, 1, 0},
        {1, -1, 0},
        {1, 1, 0},
        {-1, 0, -1},
        {-1, 0, 1},
        {1, 0, -1},
        {1, 0, 1},
        {0, -1, -1},
        {0, 1, -1},
        {0, -1, 1},
        {0, 1, 1},
        {-1, -1, 0},
        {-1, 1, 0},
        {1, -1, 0},
        {1, 1, 0},
        {-1, 0, -1},
        {-1, 0, 1},
        {1, 0, -1},
        {1, 0, 1},

        // Extra values to prevent slow % operator...
        {0, -1, -1},
        {0, 1, -1},
        {0, -1, 1},
        {0, 1, 1},
        {-1, -1, 0},
        {-1, 1, 0},
        {1, -1, 0},
        {1, 1, 0},
        {-1, 0, -1},
        {-1, 0, 1},
        {1, 0, -1},
        {1, 0, 1}
    };

    private Perlin() {
    }

    public static double compute(double x, double y, HashFunction2D hf) {

        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);
        long maxx = minx + 1L;
        long maxy = miny + 1L;

        double smoothx = NoiseMath.smooth(x - minx);
        double smoothy = NoiseMath.smooth(y - miny);

        int idx1 = hf.hash(minx, miny) & 15;
        int idx2 = hf.hash(maxx, miny) & 15;
        int idx3 = hf.hash(minx, maxy) & 15;
        int idx4 = hf.hash(maxx, maxy) & 15;

        int[] grad1 = GRAD_2D[idx1];
        int[] grad2 = GRAD_2D[idx2];
        int[] grad3 = GRAD_2D[idx3];
        int[] grad4 = GRAD_2D[idx4];

        double dist10 = x - minx;
        double dist11 = y - miny;
        double dist20 = x - maxx;
        double dist21 = y - miny;
        double dist30 = x - minx;
        double dist31 = y - maxy;
        double dist40 = x - maxx;
        double dist41 = y - maxy;

        double dot1 = dist10 * grad1[0] + dist11 * grad1[1];
        double dot2 = dist20 * grad2[0] + dist21 * grad2[1];
        double dot3 = dist30 * grad3[0] + dist31 * grad3[1];
        double dot4 = dist40 * grad4[0] + dist41 * grad4[1];

        double lerp12 = NoiseMath.lerp(dot1, dot2, smoothx);
        double lerp34 = NoiseMath.lerp(dot3, dot4, smoothx);

        return NoiseMath.lerp(lerp12, lerp34, smoothy);
    }

    public static double compute(double x, double y, double z, HashFunction3D hf) {

        long minx = NoiseMath.floor(x);
        long miny = NoiseMath.floor(y);
        long minz = NoiseMath.floor(z);
        long maxx = minx + 1L;
        long maxy = miny + 1L;
        long maxz = minz + 1L;
        double smoothx = NoiseMath.smooth(x - minx);
        double smoothy = NoiseMath.smooth(y - miny);
        double smoothz = NoiseMath.smooth(z - minz);


        int idx1 = hf.hash(minx, miny, minz) & 31;
        int idx2 = hf.hash(maxx, miny, minz) & 31;
        int idx3 = hf.hash(minx, maxy, minz) & 31;
        int idx4 = hf.hash(maxx, maxy, minz) & 31;
        int idx5 = hf.hash(minx, miny, maxz) & 31;
        int idx6 = hf.hash(maxx, miny, maxz) & 31;
        int idx7 = hf.hash(minx, maxy, maxz) & 31;
        int idx8 = hf.hash(maxx, maxy, maxz) & 31;

        int[] grad1 = GRAD_3D[idx1];
        int[] grad2 = GRAD_3D[idx2];
        int[] grad3 = GRAD_3D[idx3];
        int[] grad4 = GRAD_3D[idx4];
        int[] grad5 = GRAD_3D[idx5];
        int[] grad6 = GRAD_3D[idx6];
        int[] grad7 = GRAD_3D[idx7];
        int[] grad8 = GRAD_3D[idx8];

        double dist11 = x - minx;
        double dist12 = y - miny;
        double dist13 = z - minz;
        double dist21 = x - maxx;
        double dist22 = y - miny;
        double dist23 = z - minz;
        double dist31 = x - minx;
        double dist32 = y - maxy;
        double dist33 = z - minz;
        double dist41 = x - maxx;
        double dist42 = y - maxy;
        double dist43 = z - minz;
        double dist51 = x - minx;
        double dist52 = y - miny;
        double dist53 = z - maxz;
        double dist61 = x - maxx;
        double dist62 = y - miny;
        double dist63 = z - maxz;
        double dist71 = x - minx;
        double dist72 = y - maxy;
        double dist73 = z - maxz;
        double dist81 = x - maxx;
        double dist82 = y - maxy;
        double dist83 = z - maxz;

        double dot1 = dist11 * grad1[0] + dist12 * grad1[1] + dist13 * grad1[2];
        double dot2 = dist21 * grad2[0] + dist22 * grad2[1] + dist23 * grad2[2];
        double dot3 = dist31 * grad3[0] + dist32 * grad3[1] + dist33 * grad3[2];
        double dot4 = dist41 * grad4[0] + dist42 * grad4[1] + dist43 * grad4[2];
        double dot5 = dist51 * grad5[0] + dist52 * grad5[1] + dist53 * grad5[2];
        double dot6 = dist61 * grad6[0] + dist62 * grad6[1] + dist63 * grad6[2];
        double dot7 = dist71 * grad7[0] + dist72 * grad7[1] + dist73 * grad7[2];
        double dot8 = dist81 * grad8[0] + dist82 * grad8[1] + dist83 * grad8[2];

        double lerp12 = NoiseMath.lerp(dot1, dot2, smoothx);
        double lerp34 = NoiseMath.lerp(dot3, dot4, smoothx);
        double lerp1234 = NoiseMath.lerp(lerp12, lerp34, smoothy);
        double lerp56 = NoiseMath.lerp(dot5, dot6, smoothx);
        double lerp78 = NoiseMath.lerp(dot7, dot8, smoothx);
        double lerp5678 = NoiseMath.lerp(lerp56, lerp78, smoothy);

        return NoiseMath.lerp(lerp1234, lerp5678, smoothz);
    }
}
