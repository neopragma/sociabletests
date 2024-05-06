package com.neopragma.withmocks.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherTest {

    @Test
    public void given_high_75_and_low_35_it_returns_difference_of_40() {
        assertEquals(40, (75 - 35));
    }

    @Test
    public void it_extracts_day_number_from_record() {
        assertEquals("10",
                "  10  84    64    74          57.5       0.00 F       210  6.6 050   9  3.4  84 40 1019.0"
                .substring(2,4));
    }
    @Test
    public void it_extracts_maximum_temperature_as_an_integer() {
        assertEquals(-84,
                Integer.valueOf("  10 -84    64    74          57.5       0.00 F       210  6.6 050   9  3.4  84 40 1019.0"
                        .substring(5,8).replaceAll("[^\\d-]", "")));
    }
    @Test
    public void it_extracts_minimum_temperature_as_an_integer() {
        assertEquals(64,
                Integer.valueOf("  10  84    64    74          57.5       0.00 F       210  6.6 050   9  3.4  84 40 1019.0"
                        .substring(11,14).replaceAll("[^\\d-]", "")));
    }

    @Test
    public void it_populates_a_record_with_the_day_number_and_temperature_difference() {
        String inputRecord = "  10  84    64    74          57.5       0.00 F       210  6.6 050   9  3.4  84 40 1019.0";
        TemperatureDifference expected = new TemperatureDifference("10", 20);
        assertEquals(expected,
            new TemperatureDifference(
                inputRecord.substring(2,4),
                Integer.valueOf(inputRecord.substring(5,8).replaceAll("[^\\d-]", ""))
                    -
                Integer.valueOf(inputRecord.substring(11,14).replaceAll("[^\\d-]", ""))
            ));
    }
    private record TemperatureDifference(String dayNumber, Integer difference) {}

}
