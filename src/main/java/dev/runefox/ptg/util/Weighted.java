package dev.runefox.ptg.util;

/**
 * A value with a weight.
 *
 * @param value  The value.
 * @param weight The weight.
 */
public record Weighted<T>(T value, int weight) {
}
