package com.neopragma.withmocks.v1;

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
    public void itReturns_1_asTheDayWithTheMinimumTemperatureSpread() {
        when(weatherData
                .getMinMaxTemps())
                .thenReturn(
                        Arrays.asList( new MinMaxTemps(1, 55, 57) ));
        sut = new Weather(weatherData);
        assertEquals(1, sut.getDayWithMinimumTemperatureSpread());
    }
}
