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
                                new ValueRange("Team1", 40, 50),
                                new ValueRange("Team2", 48, 50),
                                new ValueRange("Team3", 47, 50)
                        ));
        sut = new Football(footballData);
        assertEquals("Team2", sut.getTeamWithMinimumScoringSpread());
    }
}
