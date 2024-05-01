package com.neopragma.sociable.v5;

import java.io.*;

public class FileSystem {
    private ReaderWrapper reader;
    private FileSystem() {}
    public static FileSystem create() {
        return new FileSystem();
    }
    public static FileSystem createNull() {
        return new FileSystem();
    }
    public FileSystem withPath(String path) {
        reader = new RealReader().withPath(path);
        return this;
    }
    public FileSystem withInputRecords(String[] inputRecords) {
        reader = new StubbedReader().withInputRecords(inputRecords);
        return this;
    }
    public String nextRecord() {
        return reader.nextRecord();
    }
    private interface ReaderWrapper {
        ReaderWrapper withPath(String path);
        ReaderWrapper withInputRecords(String[] inputRecords);
        String nextRecord();
    }
    private static class RealReader implements ReaderWrapper {
        private BufferedReader reader;
        public RealReader() {}
        @Override
        public ReaderWrapper withPath(String path) {
            try {
                this.reader = new BufferedReader(new FileReader(path));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return this;
        }
        @Override
        public ReaderWrapper withInputRecords(String[] inputRecords) {
            return this;
        }
        @Override
        public String nextRecord() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class StubbedReader
            extends BufferedReader
            implements ReaderWrapper {
        private String[] inputRecords = null;
        private int recordIndex = 0;
        private final int EOF = -1;

        public StubbedReader() {
            super(null);
        }

        @Override
        public ReaderWrapper withPath(String path) {
            return this;
        }
        @Override
        public ReaderWrapper withInputRecords(String[] inputRecords) {
            this.inputRecords = inputRecords;
            return this;
        }
        @Override
        public String nextRecord() {
            if (inputRecords != null) {
                if (recordIndex >= inputRecords.length) {
                    return null;
                } else {
                    return inputRecords[recordIndex++];
                }
            }
            return null;
        }
    }
}
