package com.neopragma.sociable.v4;

import java.util.Objects;

/**
 * Value object containing the minimum and maximum termperatures for a given day.
 */
public record MinMaxTemps(Integer dayNumber, Integer minimumTemp, Integer maximumTemp) {
    public MinMaxTemps {
        Objects.requireNonNull(dayNumber);
        Objects.requireNonNull(minimumTemp);
        Objects.requireNonNull(maximumTemp);
    }

}
