package com.neopragma.withmocks.v3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        try (Stream<String> lines =
                reader.readLine()) {
                    lines.forEach((line) -> {
                    result.add(
                        new TemperatureDifference(
                            line.substring(dayNumberField.startPosition(), dayNumberField.endPosition()),
                            Helpers.extractNumericField(line, maximumTemperatureField)
                            - Helpers.extractNumericField(line, minimumTemperatureField)
                        )
                    );
                }
            );
        }
        return result;
    }
}
