package com.neopragma.sociable.v1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherTest {
    private Weather sut;

    @Test
    public void itReturns_1_asTheDayWithTheMinimumTemperatureSpread() {
        sut = new Weather(WeatherDataImpl.createNull(
                new String[] { "            1  55    57" }
        ));
        assertEquals(1, sut.getDayWithMinimumTemperatureSpreadForMonth(6));
    }
}
