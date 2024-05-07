package com.neopragma.withmocks.v2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Weather {
    private ReaderWrapper reader;
    private static final FieldPosition dayNumberField = new FieldPosition(2,4);
    private static final FieldPosition maximumTemperatureField = new FieldPosition(5,8);
    private static final FieldPosition minimumTemperatureField = new FieldPosition(11,14);
    public Weather(ReaderWrapper reader) {
        this.reader = reader;
    }
    public TemperatureDifference smallestTemperatureRange() {
        try {
            return readLines()
                .stream()
                .reduce(new TemperatureDifference("99",1000),
                    (smallestSoFar, currentElement) ->
                        currentElement.difference() < smallestSoFar.difference() ?
                            currentElement : smallestSoFar
                );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private List<TemperatureDifference> readLines() throws IOException {
        ArrayList<TemperatureDifference> result = new ArrayList<>();
        String record = reader.readLine();
        while (record != null) {
            result.add(
                new TemperatureDifference(
                            record.substring(dayNumberField.startPosition(),dayNumberField.endPosition()),
                            Helpers.extractNumericField(record, maximumTemperatureField)
                                    - Helpers.extractNumericField(record, minimumTemperatureField)
                )
            );
            record = reader.readLine();
        }
        return result;
    }
}
