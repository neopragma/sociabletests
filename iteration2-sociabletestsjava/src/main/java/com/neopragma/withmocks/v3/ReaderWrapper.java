package com.neopragma.withmocks.v3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Workaround to enable mocking of BufferedReader.readLine().
 */
public class ReaderWrapper {
    private Stream<String> lines;
    private Path path;
    private String[] records;
    private ReaderWrapper() {}
    public static ReaderWrapper create(String pathName) {
        ReaderWrapper wrapper = new ReaderWrapper();
        wrapper.path = Path.of(pathName);
        return wrapper;
    }
    public static ReaderWrapper createStub(String[] records) {
        ReaderWrapper wrapper = new ReaderWrapper();
        wrapper.records = records;
        return wrapper;
    }
    public Stream<String> readLine() {
        if (records == null) {
            try {
                return Files.lines(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return Arrays.stream(records);
        }
    }
}
