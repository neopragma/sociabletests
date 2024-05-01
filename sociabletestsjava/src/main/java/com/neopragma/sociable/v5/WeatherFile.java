package com.neopragma.sociable.v5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherFile implements Nullable {
    private FileSystem fileSystem;
    private WeatherFile(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }
    public static WeatherFile create() {
        return new WeatherFile(FileSystem.create().withPath("weather.dat"));
    }
    public static WeatherFile createNull(String[] inputRecords) {
        String[] allRecords = new String[inputRecords.length + 2];
        allRecords[0] = "  Dy MxT   MnT   AvT   HDDay  AvDP 1HrP TPcpn WxType PDir AvSp Dir MxS SkyC MxR MnR AvSLP";
        allRecords[1] = "";
        System.arraycopy(inputRecords,
                0,
                allRecords,
                2,
                inputRecords.length);
        return new WeatherFile(FileSystem.createNull()
                .withInputRecords(allRecords));
    }
    public String getDayWithMinimumTemperatureSpread() {
        List<ValueRange> valueRanges = new ArrayList<>();
        String line;
        while ((line = fileSystem.nextRecord()) != null) {
            if (!line.isEmpty()) {
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
        return Helpers.findSmallestRangeIn(valueRanges);
    }
}

