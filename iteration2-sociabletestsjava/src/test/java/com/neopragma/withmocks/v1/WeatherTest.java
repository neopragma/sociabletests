package com.neopragma.withmocks.v1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherTest {
    @Mock
    ReaderWrapper mockReader;
    @Test
    public void it_reads_two_records() throws IOException {
        when(mockReader.readLine()).thenReturn(
                "abc", "def", null
        );
        Weather cut = new Weather(mockReader);
        assertEquals("abc", cut.readNextRecord());
        assertEquals("def", cut.readNextRecord());
        assertEquals(null, cut.readNextRecord());
    }
}
