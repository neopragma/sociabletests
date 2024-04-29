package com.neopragma.withmocks.v4;

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
}
