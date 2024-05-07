package com.neopragma.withmocks.v2;

import java.io.BufferedReader;

/**
 * Workaround to enable mocking of BufferedReader.readLine().
 */
public class ReaderWrapper {
    private BufferedReader bufferedReader;
    public ReaderWrapper(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
    public String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
