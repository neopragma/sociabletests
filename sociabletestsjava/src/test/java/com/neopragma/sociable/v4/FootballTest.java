package com.neopragma.sociable.v4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FootballTest {
    private Football sut;

    @Test
    public void Given_3_TeamsData_itReturns_Team2_asTheTeamWithTheMinimumScoringSpread() {
        sut = new Football(FootballDataImpl.createNull(
                new String[] {
                        "    9. Team1                               50  -  40    ",
                        "   10. Team2                               50  -  49    ",
                        "   11. Team3                               50  -  48    "
                }
        ));
        assertEquals("Team2", sut.getTeamWithMinimumScoringSpread().trim());
    }

}
