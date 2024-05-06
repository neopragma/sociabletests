package com.neopragma.withmocks.v1;

import java.io.BufferedReader;
import java.io.IOException;

public class Weather {
    ReaderWrapper reader;
    public Weather(ReaderWrapper reader) {
        this.reader = reader;
    }

    public String readNextRecord() throws IOException {
        return reader.readLine();
    }
}
