package com.neopragma.sociable.v3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Weather {
    private final ReaderWrapper reader;
    private static final FieldPosition dayNumberField = new FieldPosition(2,4);
    private static final FieldPosition maximumTemperatureField = new FieldPosition(5,8);
    private static final FieldPosition minimumTemperatureField = new FieldPosition(11,14);
    public Weather(String pathName) {
        this(new RealReaderWrapper(pathName));
    }
    public Weather(String[] records) {
        this(new StubbedReaderWrapper(records));
    }
    private Weather(ReaderWrapper reader) {
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
        try (Stream<String> lines = reader.readLine()) {
            lines.forEach((line) -> {
                result.add(new TemperatureDifference(
                        line.substring(dayNumberField.startPosition(), dayNumberField.endPosition()),
                        Helpers.extractNumericField(line, maximumTemperatureField)
                                - Helpers.extractNumericField(line, minimumTemperatureField)
                ));
            });
        }
        return result;
    }
    interface ReaderWrapper {
        Stream<String> readLine();
    }
    static class RealReaderWrapper implements ReaderWrapper {
        private final String pathName;
        public RealReaderWrapper(String pathName) {
            this.pathName = pathName;
        }
        @Override
        public Stream<String> readLine() {
            try {
                return Files.lines(Path.of(pathName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class StubbedReaderWrapper implements ReaderWrapper {
        private String[] records = null;
        public StubbedReaderWrapper(String[] records) {
            this.records = records;
        }
        @Override
        public Stream<String> readLine() {
            return Arrays.stream(records);
        }
    }
}
