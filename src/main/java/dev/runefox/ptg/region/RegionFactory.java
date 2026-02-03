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


import java.util.function.Function;

/**
 * A region factory builds {@link Region} instances. These factories are usually wrapped by other factories that apply
 * transformations to the {@link Region}s they build, forming a factory chain. A {@link RegionBuilder} is used to build
 * the factory chain and also implements this interface.
 *
 * @param <R> The region type this factory builds.
 */
@FunctionalInterface
public interface RegionFactory<R extends Region> {
    /**
     * Builds a {@link Region}.
     *
     * @return The built region.
     */
    R buildRegion();

    /**
     * Makes a {@link FractalGenerator} from a built {@link Region}.
     *
     * @return The created {@link FractalGenerator} instance.
     */
    default <D extends FractalGenerator<?>> D makeGenerator(Function<Region, ? extends D> factory) {
        return factory.apply(buildRegion());
    }
}
