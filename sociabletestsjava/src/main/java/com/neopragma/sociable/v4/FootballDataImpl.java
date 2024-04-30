package com.neopragma.sociable.v4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FootballDataImpl implements Nullable, FootballData {
    private BufferedReader reader;
    public static FootballData create() {
        try {
            return new FootballDataImpl(new BufferedReader(new FileReader("football.dat")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static FootballData createNull(String[] inputRecords) {
        String[] allRecords = new String[inputRecords.length + 1];
        allRecords[0] = "       Team            P     W    L   D    F      A     Pts";
        System.arraycopy(inputRecords,
                0,
                allRecords,
                1,
                inputRecords.length);

        return new FootballDataImpl(new StubbedReader(new StringReader(""))
                .withInputRecords(inputRecords));
    }

    private FootballDataImpl(BufferedReader reader) {
        this.reader = reader;
    }
    @Override
    public List<ValueRange> getGoalsForAndAgainst() {
        List<ValueRange> valueRanges = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    // create a GoalsForAndAgainst object for each line
                    if (Character.isDigit(line.charAt(44))) {
                        // this is a hack
                        valueRanges.add(new ValueRange(
                                line.substring(7, 22),
                                Helpers.stringToInteger(line.substring(50, 53)),
                                Helpers.stringToInteger(line.substring(43, 46))
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

