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

package dev.runefox.ptg.region;

import java.util.Random;

import dev.runefox.ptg.rng.RNG;

/**
 * An implementation of a pseudorandom number generator. Such implementations are usually made to be faster than {@link
 * Random} because they must generate a lot of pseudorandom numbers and do not need to be safe and accurate.
 */
public interface RegionRNG extends RNG {
    /**
     * Recomputes the seed of this RNG from the specified coordinates and returns this instance for convenience.
     *
     * @param x X coordinate.
     * @param z Z coordinate.
     * @return This instance for convenience.
     */
    RegionRNG seedFromPosition(int x, int z);
}
