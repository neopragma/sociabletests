package com.neopragma.withmocks.v4;

import java.util.Objects;

/**
 * Value object containing the minimum and maximum values of a range of integers
 * along with a key to distinguish each instance from the others.
 */
public record ValueRange(String key, Integer minimumValue, Integer maximumValue) {
    public ValueRange {
        Objects.requireNonNull(key);
        Objects.requireNonNull(minimumValue);
        Objects.requireNonNull(maximumValue);
    }

}
