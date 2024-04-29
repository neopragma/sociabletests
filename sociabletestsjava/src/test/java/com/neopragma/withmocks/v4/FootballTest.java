package com.neopragma.withmocks.v4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FootballTest {
    private Football sut;

    @Mock
    FootballData footballData;
    @Test
    public void Given_3_TeamsData_itReturns_Team2_asTheTeamWithTheMinimumScoringSpread() {
        when(footballData
                .getGoalsForAndAgainst())
                .thenReturn(
                        Arrays.asList(
                                new GoalsForAndAgainst("Team1", 50, 40),
                                new GoalsForAndAgainst("Team2", 50, 48),
                                new GoalsForAndAgainst("Team3", 50, 47)
                        ));
        sut = new Football(footballData);
        assertEquals("Team2", sut.getTeamWithMinimumScoringSpread());
    }
}
