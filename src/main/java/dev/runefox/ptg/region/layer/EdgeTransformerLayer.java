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

package dev.runefox.ptg.region.layer;

import dev.runefox.ptg.region.RegionRNG;

public interface EdgeTransformerLayer extends CastleTransformerLayer {
    @Override
    default int generate(RegionRNG rng, int center, int negX, int posX, int negZ, int posZ) {
        int[] edges = new int[4];
        int edgeCount = 0;

        if (isEdge(rng, center, negX)) {
            edges[edgeCount] = getEdge(rng, center, negX);
            edgeCount++;
        }
        if (isEdge(rng, center, posX)) {
            edges[edgeCount] = getEdge(rng, center, posX);
            edgeCount++;
        }
        if (isEdge(rng, center, negZ)) {
            edges[edgeCount] = getEdge(rng, center, negZ);
            edgeCount++;
        }
        if (isEdge(rng, center, posX)) {
            edges[edgeCount] = getEdge(rng, center, posZ);
            edgeCount++;
        }

        if (edgeCount == 0) return center;

        return mixEdges(rng, edges, edgeCount);
    }

    boolean isEdge(RegionRNG rng, int center, int neighbor);

    int getEdge(RegionRNG rng, int center, int neighbor);

    default int mixEdges(RegionRNG rng, int[] edges, int count) {
        return edges[rng.random(count)];
    }
}
