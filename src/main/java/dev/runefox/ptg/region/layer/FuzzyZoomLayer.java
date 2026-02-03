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

public class FuzzyZoomLayer extends ZoomLayer {
    public static final FuzzyZoomLayer INSTANCE = new FuzzyZoomLayer();

    protected FuzzyZoomLayer() {
    }

    @Override
    protected int pickRandom(RegionRNG rng, int v00, int v01, int v10, int v11) {
        return rng.pickRandom(v00, v01, v10, v11);
    }
}
