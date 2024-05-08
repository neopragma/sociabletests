package com.neopragma.withmocks.v3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Workaround to enable mocking of BufferedReader.readLine().
 */
public class ReaderWrapper {
    private Stream<String> records;
    public ReaderWrapper() {}
    public ReaderWrapper(Stream<String> records) {
        this.records = records;
    }
    public Stream<String> readLine() {
        try {
            return records == null ? Files.lines(Path.of("weather.dat")) : records;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
