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

package dev.runefox.ptg.noise.util;

import dev.runefox.ptg.noise.Noise2D;
import dev.runefox.ptg.noise.Noise3D;

/**
 * Functional interface that represents a function of zero or more double values. This function can be used in {@link
 * Noise2D#combine} and {@link Noise3D#combine}
 */
@FunctionalInterface
public interface DoubleFunction {
    /**
     * Combines zero or more double values using a specific function. This may be every function, as long as it results
     * in a double value.
     *
     * @param doubles The double values to combine using the specific function
     * @return The result value
     */
    double combine(double... doubles);
}
