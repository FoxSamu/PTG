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
import dev.runefox.ptg.region.util.FloatSelector;
import dev.runefox.ptg.region.util.IntSelector;

public class ReplaceLayer implements FilterTransformerLayer {
    private final IntSelector selector;
    private final int replacement;

    public ReplaceLayer(IntSelector selector, int replacement) {
        this.selector = selector;
        this.replacement = replacement;
    }

    public ReplaceLayer(FloatSelector selector, float replacement) {
        this.selector = val -> selector.mustReplace(Float.intBitsToFloat(val));
        this.replacement = Float.floatToRawIntBits(replacement);
    }

    public ReplaceLayer(int selector, int replacement) {
        this.selector = val -> val == selector;
        this.replacement = replacement;
    }

    public ReplaceLayer(float selector, float replacement) {
        this.selector = val -> Float.intBitsToFloat(val) == selector;
        this.replacement = Float.floatToRawIntBits(replacement);
    }

    @Override
    public int generate(RegionRNG rng, int value) {
        return selector.mustReplace(value) ? replacement : value;
    }
}
