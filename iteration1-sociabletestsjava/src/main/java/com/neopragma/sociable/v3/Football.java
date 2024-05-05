package com.neopragma.sociable.v3;

import java.util.List;

public class Football {
    private FootballData footballData;

    public Football(FootballData footballData) {
        this.footballData = footballData;
    }

    public String getTeamWithMinimumScoringSpread() {
        List<GoalsForAndAgainst> goals = footballData.getGoalsForAndAgainst();
        String teamName = null;
        int minimumScoringSpread = Integer.MAX_VALUE;
        for (GoalsForAndAgainst dataForATeam : goals) {
            int teamScoringSpread = dataForATeam.goalsFor() - dataForATeam.goalsAgainst();
            if (teamScoringSpread < minimumScoringSpread) {
                teamName = dataForATeam.teamName();
                minimumScoringSpread = teamScoringSpread;
            }
        }
        return teamName;
    }

}
