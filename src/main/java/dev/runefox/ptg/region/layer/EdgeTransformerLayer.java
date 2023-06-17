/*
 * Copyright (c) 2023 Runefox Dev
 * Licensed under Apache 2.0 license
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
