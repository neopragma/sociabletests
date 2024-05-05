package com.neopragma.sociable.v1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataImpl implements Nullable, WeatherData {
    private BufferedReader reader;
    public static WeatherData create() {
        try {
            return new WeatherDataImpl(new BufferedReader(new FileReader("path")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static WeatherData createNull(String[] inputRecords) {
        return new WeatherDataImpl(new StubbedReader(new StringReader(""))
                .withInputRecords(inputRecords));
    }

    private WeatherDataImpl(BufferedReader reader) {
        this.reader = reader;
    }
    @Override
    public List<MinMaxTemps> getMinMaxTempsForMonth(Integer monthNumber) {
        return loadMinMaxTemps(monthNumber);
    }

    private List<MinMaxTemps> loadMinMaxTemps(Integer monthNumber) {
        List<MinMaxTemps> minMaxTemps = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {

                System.out.println("Got record <" + line + ">");

                // create a MinMaxTemp object for each line
                if (Character.isDigit(line.charAt(16))) {
                    // this is a hack
                    minMaxTemps.add(new MinMaxTemps(
                            Integer.valueOf(line.substring(12, 13)),
                            Integer.valueOf(line.substring(15, 17)),
                            Integer.valueOf(line.substring(21, 23))
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return minMaxTemps;
    }
}

class StubbedReader extends BufferedReader {
    private String[] inputRecords = null;
    private int recordIndex = 0;
    private final int EOF = -1;
    public StubbedReader(Reader in) {
        super(in);
    }

    @Override
    public int read(char[] cbuf, int off, int len) {
        return EOF;
    }
    @Override
    public void close() {}

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
        int sourceIndex;
        int destinationIndex;
        this.inputRecords = new String[inputRecords.length + 2];
        this.inputRecords[0] = "            Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP";
        this.inputRecords[1] = "                                               ";
        for (sourceIndex = 0, destinationIndex = 2 ;
             sourceIndex < inputRecords.length ;
             sourceIndex++, destinationIndex++ ) {
            this.inputRecords[destinationIndex] = inputRecords[sourceIndex];
        }
        return this;
    }

}
