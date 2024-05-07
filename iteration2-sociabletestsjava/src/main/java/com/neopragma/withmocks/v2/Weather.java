package com.neopragma.withmocks.v2;

import java.io.IOException;

public class Weather {
    private ReaderWrapper reader;
    private static final FieldPosition dayNumberField = new FieldPosition(2,4);
    private static final FieldPosition maximumTemperatureField = new FieldPosition(5,8);
    private static final FieldPosition minimumTemperatureField = new FieldPosition(11,14);
    public Weather(ReaderWrapper reader) {
        this.reader = reader;
    }
    public TemperatureDifference readLine() throws IOException {
        String record = reader.readLine();
        if (record == null) {
            return null;
        }
        return new TemperatureDifference(
                record.substring(dayNumberField.startPosition(),dayNumberField.endPosition()),
                Helpers.extractNumericField(record, maximumTemperatureField)
                        - Helpers.extractNumericField(record, minimumTemperatureField));
    }
}
