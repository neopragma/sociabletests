package com.neopragma.withmocks.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherTest {

    @Test
    public void given_high_75_and_low_35_it_returns_difference_of_40() {
        assertEquals(40,temperatureDifference(75, 35));
    }
    private Integer temperatureDifference(int highTemperature, int lowTemperature) {
        return highTemperature - lowTemperature + 1;
    }
}
