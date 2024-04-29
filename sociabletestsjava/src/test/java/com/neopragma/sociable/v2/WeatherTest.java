package com.neopragma.sociable.v2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherTest {
    private Weather sut;

    @Test
    public void Given_1_DaysData_itReturns_1_asTheDayWithTheMinimumTemperatureSpread() {
        sut = new Weather(WeatherDataImpl.createNull(
                new String[] { "            1  55    57" }
        ));
        assertEquals(1, sut.getDayWithMinimumTemperatureSpreadForMonth(6));
    }
    @Test
    public void Given_3_DaysData_itReturns_15_asTheDayWithTheMinimumTemperatureSpread() {
        sut = new Weather(WeatherDataImpl.createNull(
                new String[] {
                        "           14  20    60",
                        "           15  22    24",
                        "           16  22    25"
                }
        ));
        assertEquals(15, sut.getDayWithMinimumTemperatureSpreadForMonth(6));
    }
}
