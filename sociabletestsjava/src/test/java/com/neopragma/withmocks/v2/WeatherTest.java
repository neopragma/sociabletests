package com.neopragma.withmocks.v2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherTest {
    private Weather sut;

    @Mock
    WeatherData weatherData;

    @Test
    public void Given_1_DaysData_itReturns_1_asTheDayWithTheMinimumTemperatureSpread() {
        when(weatherData
                .getMinMaxTemps())
                .thenReturn(
                        Arrays.asList( new MinMaxTemps(1, 55, 57) ));
        sut = new Weather(weatherData);
        assertEquals(1, sut.getDayWithMinimumTemperatureSpread());
    }
    @Test
    public void Given_3_DaysData_itReturns_15_asTheDayWithTheMinimumTemperatureSpread() {
        when(weatherData
                .getMinMaxTemps())
                .thenReturn(
                        Arrays.asList(
                                new MinMaxTemps(14, 20, 60),
                                new MinMaxTemps(15, 22, 24),
                                new MinMaxTemps(16, 22, 25)
                        ));
        sut = new Weather(weatherData);
        assertEquals(15, sut.getDayWithMinimumTemperatureSpread());
    }
}
