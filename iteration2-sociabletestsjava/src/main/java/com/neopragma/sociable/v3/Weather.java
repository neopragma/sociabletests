package com.neopragma.sociable.v3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Weather {
    private final ReaderWrapper reader;
    private static final FieldPosition dayNumberField = new FieldPosition(2,4);
    private static final FieldPosition maximumTemperatureField = new FieldPosition(5,8);
    private static final FieldPosition minimumTemperatureField = new FieldPosition(11,14);
    public static Weather create(String pathName) {
        return new Weather(new RealReaderWrapper(pathName));
    }
    public static Weather createNull(String[] records) {
        return new Weather(new StubbedReaderWrapper(records));
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
    interface ReaderWrapper {
        String readLine();
    }
    static class RealReaderWrapper implements ReaderWrapper {
        private BufferedReader bufferedReader = null;
        public RealReaderWrapper(String pathName) {
            try {
                bufferedReader = new BufferedReader(new FileReader(pathName));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public String readLine() {
            try {
                return bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class StubbedReaderWrapper implements ReaderWrapper {
        private String[] records = null;
        private int recordIndex = 0;
        public StubbedReaderWrapper(String[] records) {
            this.records = records;
        }
        @Override
        public String readLine() {
            String line = null;
            if (recordIndex < records.length) {
                line = records[recordIndex];
                recordIndex++;
            }
            return line;
        }
    }
}
