package com.neopragma.sociable.v2;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataImpl implements Nullable, WeatherData {
    private BufferedReader reader;
    public static WeatherData create() {
        try {
            return new WeatherDataImpl(new BufferedReader(new FileReader("weather.dat")));
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
    public List<MinMaxTemps> getMinMaxTemps() {
        return loadMinMaxTemps();
    }

    private List<MinMaxTemps> loadMinMaxTemps() {
        List<MinMaxTemps> minMaxTemps = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    // create a MinMaxTemp object for each line
                    if (Character.isDigit(line.charAt(3))) {
                        // this is a hack
                        minMaxTemps.add(new MinMaxTemps(
                                stringToInteger(line.substring(2, 4)),
                                stringToInteger(line.substring(11, 14)),
                                stringToInteger(line.substring(5, 8))
                        ));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return minMaxTemps;
    }

    private Integer stringToInteger(String str) {
        return Integer.valueOf(str.replaceAll("[^\\d]", ""));
    }
}

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
        int sourceIndex;
        int destinationIndex;
        this.inputRecords = new String[inputRecords.length + 2];
        this.inputRecords[0] = "  Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP";
        this.inputRecords[1] = "";
        for (sourceIndex = 0, destinationIndex = 2 ;
             sourceIndex < inputRecords.length ;
             sourceIndex++, destinationIndex++ ) {
            this.inputRecords[destinationIndex] = inputRecords[sourceIndex];
        }
        return this;
    }

}
