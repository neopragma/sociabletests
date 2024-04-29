package com.neopragma.withmocks.v4;

import java.util.List;

public class Football {
    private FootballData footballData;

    public Football(FootballData footballData) {
        this.footballData = footballData;
    }

    public String getTeamWithMinimumScoringSpread() {
        return Helpers.findSmallestRangeIn(footballData.getGoalsForAndAgainst());
    }

}
