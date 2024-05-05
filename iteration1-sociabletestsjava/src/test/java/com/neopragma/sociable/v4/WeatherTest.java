package com.neopragma.sociable.v4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherTest {
    private Weather sut;

    @Test
    public void Given_1_DaysData_itReturns_1_asTheDayWithTheMinimumTemperatureSpread() {
        sut = new Weather(WeatherDataImpl.createNull(
                new String[] { "   1  57    55" }
        ));
        assertEquals(" 1", sut.getDayWithMinimumTemperatureSpread());
    }
    @Test
    public void Given_3_DaysData_itReturns_15_asTheDayWithTheMinimumTemperatureSpread() {
        sut = new Weather(WeatherDataImpl.createNull(
                new String[] {
                        "  14  60    20                        ",
                        "  15  24    22                        ",
                        "  16  25    22                        "
                }
        ));
        assertEquals("15", sut.getDayWithMinimumTemperatureSpread());
    }

}
