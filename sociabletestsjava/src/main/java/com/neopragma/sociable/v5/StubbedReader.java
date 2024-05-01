package com.neopragma.sociable.v5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

class StubbedReader extends BufferedReader {
    private String[] inputRecords = null;
    private int recordIndex = 0;
    private final int EOF = -1;
    public StubbedReader(Reader in) {
        super(in);
    }

    public String readLine() {
        if (inputRecords != null) {
            if (recordIndex >= inputRecords.length) {
                return null;
            } else {
                return inputRecords[recordIndex++];
            }
        } else {
            try {
                return super.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public StubbedReader withInputRecords(String[] inputRecords) {
        this.inputRecords = inputRecords;
        return this;
    }

}

