package com.neopragma.sociable.v4;

import java.util.List;

public class Helpers {
    public static String findSmallestRangeIn(List<ValueRange> valueRanges) {
        String key = null;
        int minimumSpread = Integer.MAX_VALUE;
        for (ValueRange valueRange : valueRanges) {
            int valueSpread = valueRange.maximumValue() - valueRange.minimumValue();
            if (valueSpread < minimumSpread) {
                key = valueRange.key();
                minimumSpread = valueSpread;
            }
        }
        return key;
    }

    public static Integer stringToInteger(String str) {
        return Integer.valueOf(str.replaceAll("[^\\d]", ""));
    }

}
