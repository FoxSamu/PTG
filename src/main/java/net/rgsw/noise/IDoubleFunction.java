/*
 * Copyright (c) 2019 RGSW
 * All rights reserved. Do not distribute.
 * This file is part of the Modernity, and is licensed under the terms and conditions of RedGalaxy.
 *
 * Date:   11 - 14 - 2019
 * Author: rgsw
 */

package net.rgsw.noise;

/**
 * Functional interface that represents a function of zero or more double values. This function can be used in {@link
 * INoise2D#combine} and {@link INoise3D#combine}
 */
@FunctionalInterface
public interface IDoubleFunction {
    /**
     * Combines zero or more double values using a specific function. This may be every function, as long as it results
     * in a double value.
     *
     * @param doubles The double values to combine using the specific function
     * @return The result value
     */
    double combine( double... doubles );
}
