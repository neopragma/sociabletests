package com.neopragma.sociable.v4;

public class Football {
    private FootballData footballData;

    public Football(FootballData footballData) {
        this.footballData = footballData;
    }

    public String getTeamWithMinimumScoringSpread() {
        return Helpers.findSmallestRangeIn(footballData.getGoalsForAndAgainst());
    }

}
