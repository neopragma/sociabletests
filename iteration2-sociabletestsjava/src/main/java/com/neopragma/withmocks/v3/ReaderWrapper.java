package com.neopragma.withmocks.v3;

import java.util.stream.Stream;

/**
 * Workaround to enable mocking of BufferedReader.readLine().
 */
public class ReaderWrapper {
    private Stream<String> records;
    public ReaderWrapper(Stream<String> records) {
        this.records = records;
    }
    public Stream<String> readLine() {
        return records;
    }
}
