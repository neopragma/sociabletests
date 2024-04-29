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
        return new FootballDataImpl(new StubbedReader(new StringReader(""))
                .withInputRecords(inputRecords));
    }

    private FootballDataImpl(BufferedReader reader) {
        this.reader = reader;
    }
    @Override
    public List<GoalsForAndAgainst> getGoalsForAndAgainst() {
        return loadGoalsForAndAgainst();
    }

    private List<GoalsForAndAgainst> loadGoalsForAndAgainst() {
        List<GoalsForAndAgainst> goalsForAndAgainst = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    // create a GoalsForAndAgainst object for each line
                    if (Character.isDigit(line.charAt(44))) {
                        // this is a hack
                        goalsForAndAgainst.add(new GoalsForAndAgainst(
                                line.substring(7, 22),
                                stringToInteger(line.substring(43, 46)),
                                stringToInteger(line.substring(50, 53))
                        ));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return goalsForAndAgainst;
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
        this.inputRecords = new String[inputRecords.length + 1];
        this.inputRecords[0] = "       Team            P     W    L   D    F      A     Pts";
        for (sourceIndex = 0, destinationIndex = 1 ;
             sourceIndex < inputRecords.length ;
             sourceIndex++, destinationIndex++ ) {
            this.inputRecords[destinationIndex] = inputRecords[sourceIndex];
        }
        return this;
    }

}
