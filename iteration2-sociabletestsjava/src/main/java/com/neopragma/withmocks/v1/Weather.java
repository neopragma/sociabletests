package com.neopragma.withmocks.v1;

import java.io.IOException;

public class Weather {
    ReaderWrapper reader;
    public Weather(ReaderWrapper reader) {
        this.reader = reader;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }
}
