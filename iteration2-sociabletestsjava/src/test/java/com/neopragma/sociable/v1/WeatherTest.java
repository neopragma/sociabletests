package com.neopragma.sociable.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherTest {
    @Test
    public void it_reads_two_records() {
        Weather cut = Weather.createNull(new String[]
                { "abc", "def", null }
        );
        assertEquals("abc", cut.readLine());
        assertEquals("def", cut.readLine());
        assertEquals(null, cut.readLine());
    }
}
