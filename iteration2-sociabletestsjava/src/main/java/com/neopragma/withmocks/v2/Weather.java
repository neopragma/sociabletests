package com.neopragma.withmocks.v2;

import java.io.IOException;

public class Weather {
    ReaderWrapper reader;
    public Weather(ReaderWrapper reader) {
        this.reader = reader;
    }

    public TemperatureDifference readLine() throws IOException {
        String record = reader.readLine();
        if (record == null) {
            return null;
        }
        return new TemperatureDifference(
                record.substring(2,4),
                Integer.valueOf(record.substring(5,8).replaceAll("[^\\d-]", ""))
                        -
                        Integer.valueOf(record.substring(11,14).replaceAll("[^\\d-]", ""))
        );
    }
}
