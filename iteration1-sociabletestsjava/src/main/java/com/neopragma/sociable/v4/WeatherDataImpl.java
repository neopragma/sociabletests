package com.neopragma.sociable.v4;

import java.io.*;
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
        String[] allRecords = new String[inputRecords.length + 2];
        allRecords[0] = "  Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP";
        allRecords[1] = "";
        System.arraycopy(inputRecords,
                0,
                allRecords,
                2,
                inputRecords.length);
        return new WeatherDataImpl(new StubbedReader(new StringReader(""))
                .withInputRecords(allRecords));
    }

    private WeatherDataImpl(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public List<ValueRange> getMinMaxTemps() {

        List<ValueRange> valueRanges = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    // create a MinMaxTemp object for each line
                    if (Character.isDigit(line.charAt(3))) {
                        // this is a hack
                        valueRanges.add(new ValueRange(
                                line.substring(2, 4),
                                Helpers.stringToInteger(line.substring(11, 14)),
                                Helpers.stringToInteger(line.substring(5, 8))
                        ));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return valueRanges;
    }
}

