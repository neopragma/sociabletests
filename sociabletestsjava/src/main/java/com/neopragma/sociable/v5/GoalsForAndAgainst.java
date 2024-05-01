package com.neopragma.sociable.v5;

import java.util.Objects;

/**
 * Value object containing the goals scored against opponents and the goals
 * scored by opponents for each team..
 */
public record GoalsForAndAgainst(String teamName, Integer goalsFor, Integer goalsAgainst) {
    public GoalsForAndAgainst {
        Objects.requireNonNull(teamName);
        Objects.requireNonNull(goalsFor);
        Objects.requireNonNull(goalsAgainst);
    }

}
