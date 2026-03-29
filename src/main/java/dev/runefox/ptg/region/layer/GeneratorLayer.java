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

import dev.runefox.ptg.region.Region;
import dev.runefox.ptg.region.RegionContext;
import dev.runefox.ptg.region.RegionFactory;
import dev.runefox.ptg.region.RegionRNG;

@FunctionalInterface
public interface GeneratorLayer {
    int generate(RegionRNG rng, int x, int z);

    default <R extends Region> RegionFactory<R> factory(RegionContext<R> ctx, long seed) {
        return () -> {
            ThreadLocal<RegionRNG> rng = ctx.getThreadLocalRNG(seed);
            return ctx.create((x, z) -> generate(rng.get().seedFromPosition(x, z), x, z));
        };
    }
}
