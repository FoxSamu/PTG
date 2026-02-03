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

/**
 * A function that maps XZ-coordinates to integers, based on a procedurally generated noise field. These values are
 * either generated directly, or read from a cache map.
 *
 * @see LazyRegion
 */
@FunctionalInterface
public interface Region {
    /**
     * Gets the value for the specified coordinates by either computing it or reading it from cache.
     *
     * @param x The X coordinate
     * @param z The Z coordinate
     * @return The value at the coordinates.
     */
    int getValue(int x, int z);

    /**
     * Gets the value for the specified coordinates by either computing it or reading it from cache, and returns it as a
     * floating point representation using {@link Float#intBitsToFloat intBitsToFloat}.
     *
     * @param x The X coordinate
     * @param z The Z coordinate
     * @return The floating point value at the coordinates.
     */
    default float getFPValue(int x, int z) {
        return Float.intBitsToFloat(getValue(x, z));
    }
}
