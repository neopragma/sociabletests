package com.neopragma.sociable.v3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class WeatherTest {
    private final String testRecord1 =
            "   9  86    32*   59       6  61.5       0.00         240  7.6 220  12  6.0  78 46 1018.6";
    private final String testRecord2 =
            "  15  64    55    60       5  54.9       0.00 F       040  4.3 200   7  9.6  96 70 1006.1";
    private static final String EOF = null;

    @Test
    public void it_returns_the_day_number_of_the_day_with_the_smallest_temperature_difference() {
        Weather cut = Weather.createNull(new String[]{
                testRecord1, testRecord2, EOF
        });
        TemperatureDifference expected = new TemperatureDifference("15", 9);
        assertEquals(expected, cut.smallestTemperatureRange());
    }
}
