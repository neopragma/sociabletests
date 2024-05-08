package com.neopragma.withmocks.v3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherTest {
    private final String testRecord1 =
        "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6";
    private final String testRecord2 =
        "  15  64    55    60       5  54.9       0.00 F       040  4.3 200   7  9.6  96 70 1006.1";
    private static final String EOF = null;
    @Mock
    ReaderWrapper mockReader;

    @Test
    public void it_returns_the_day_number_of_the_day_with_the_smallest_temperature_difference() {
        when(mockReader.readLine()).thenReturn(
                testRecord1, testRecord2, EOF
        );
        TemperatureDifference expected = new TemperatureDifference("15", 9);
        Weather cut = new Weather(mockReader);
        assertEquals(expected, cut.smallestTemperatureRange());
    }
}
