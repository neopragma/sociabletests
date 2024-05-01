package com.neopragma.sociable.v5;

import java.util.ArrayList;
import java.util.List;


public class FootballFile implements Nullable {
    private FileSystem fileSystem;
    private FootballFile(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }
    public static FootballFile create() {
        return new FootballFile(FileSystem.create().withPath("football.dat"));
    }
    public static FootballFile createNull(String[] inputRecords) {
        String[] allRecords = new String[inputRecords.length + 1];
        allRecords[0] = "       Team            P     W    L   D    F      A     Pts";
        System.arraycopy(inputRecords,
                0,
                allRecords,
                1,
                inputRecords.length);

        return new FootballFile(FileSystem.createNull()
                .withInputRecords(inputRecords));
    }
    public String getTeamWithMinimumScoringSpread() {
        List<ValueRange> valueRanges = new ArrayList<>();
        String line;
        while ((line = fileSystem.nextRecord()) != null) {
            if (!line.isEmpty()) {
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
        return Helpers.findSmallestRangeIn(valueRanges);
    }

}

